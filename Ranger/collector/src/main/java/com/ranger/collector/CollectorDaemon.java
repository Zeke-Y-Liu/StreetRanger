package com.ranger.collector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.ranger.dao.SpringUtil;
import com.ranger.util.PropertiesUtil;

/*
 * run this class with parameter 
 * 1. accessToken -- optional if not provided by command args, will try to get from config file
 * 2. totalRequestPerHour -- default to 150, max reqeusts allowed by weobi API limitation 
 * 3. requestPerHourLowWarter -- default to 120, for any reason, for e.g poor network, poor pc, if actual request per hour less than actualRequestPerHourLowWarter, we see it warning sign of poor performance
 * 4. timePerMaxRequestTimesLowWarter --  default to 40 mins, if request times reach 150 within 40 mins, that means the bottle neck is weibo API limited request, we can improve total through put by using multiple ip, or access token
 * 
 */
public class CollectorDaemon {
	private static String CMD_STOP = "stop";
	
	static Logger log = Logger.getLogger(Collector.class.getName());
	
	public static void main(String args[]) {
		String intialUserPoolFileName = "configuration.properties";
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(intialUserPoolFileName);
		} catch (ConfigurationException e) {
			log.error("unable to load initial user pool file" + intialUserPoolFileName, e);
			return;
		}
		
		String[] accessTokens = config.getStringArray(PropertiesUtil.ATTR_ACCESS_TOKEN);
		int collectorKickOffInterval = config.getInt(PropertiesUtil.ATTR_COLLECTOR_KICK_OFF_INTERVAL);
		long timeGapThreshold = config.getLong(PropertiesUtil.ATTR_TIME_GAP_THRESHOLD);
		int batchSize = config.getInt(PropertiesUtil.ATTR_BATCH_SIZE);
		List<DynamicCollectorScheduler> schedulers = new ArrayList<DynamicCollectorScheduler>();
		ScheduledThreadPoolExecutor threadScheduler = new ScheduledThreadPoolExecutor(accessTokens.length);

		for(int i=0; i<accessTokens.length; i++) {
			String accessToken = StringUtils.trimToEmpty(accessTokens[i]);
			Collector collector = new TimelineCollector(SpringUtil.getDao(), accessToken, batchSize);
			DynamicCollectorScheduler scheduler = new DynamicCollectorScheduler(collector, timeGapThreshold);
			schedulers.add(scheduler);
			CollectorRunnable runnable = new CollectorRunnable(scheduler);
			// to make full use of limited requests and server resource, minimum the parallel threads, 
			// kick off the collecting thread one by with predefined intervals
			threadScheduler.schedule(runnable, collectorKickOffInterval*i, TimeUnit.SECONDS);
//			Thread collectorThread = new Thread(runnable);
//			collectorThread.start();
		}
		// get out of working thread's way.
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String commandLine = "";
		try {
			Thread.sleep(3000);
			while(!CMD_STOP.equalsIgnoreCase(commandLine)) {				
				commandLine = reader.readLine();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
}
