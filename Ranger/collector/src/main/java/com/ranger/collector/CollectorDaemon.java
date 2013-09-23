package com.ranger.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
		String accessToken = null;
		if(args.length > 0) {
			accessToken = args[0];
		}
		
		String intialUserPoolFileName = "initialUserPool.properties";
		if(args.length > 1 && !StringUtils.trimToEmpty(args[1]).equals("")) {
			intialUserPoolFileName = args[1];
		}
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(intialUserPoolFileName);
		} catch (ConfigurationException e) {
			log.error("unable to load initial user pool file" + intialUserPoolFileName, e);
			return;
		}
		
		if(accessToken == null && !StringUtils.trimToEmpty(config.getString(PropertiesUtil.ATTR_ACCESS_TOKEN)).equals("") ) {
			accessToken = StringUtils.trimToEmpty(config.getString(PropertiesUtil.ATTR_ACCESS_TOKEN));
		} else {
			log.error("no access token provided");
			return;
		}
		
		long timeGapThreshold = config.getLong(PropertiesUtil.ATTR_TIME_GAP_THRESHOLD);
		int batchSize = config.getInt(PropertiesUtil.ATTR_BATCH_SIZE);
		Collector collector = new TimelineCollector(SpringUtil.getDao(), accessToken, batchSize);
		DynamicCollectorScheduler scheduler = new DynamicCollectorScheduler(collector, timeGapThreshold);
		CollectorRunnable runnable = new CollectorRunnable(scheduler);
		Thread collectorThread = new Thread(runnable);
		collectorThread.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String commandLine = null;
		try {
			commandLine = reader.readLine();
			while(!CMD_STOP.equalsIgnoreCase(commandLine)) {
				commandLine = reader.readLine();
			}
		} catch (IOException e) {
			log.error(e);
		}
	}
}
