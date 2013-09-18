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
import com.ranger.util.AccessTokenUtil;

/*
 * run this class with parameter 
 * 1. access token -- optional if not provided by command args, will try to get from config file
 * 2. name of the initial user pool file optional, default to initialUserPoo.properties
 * the file contains a set of screenname, uid or name
 * 
 * the format of int initial user pool file can be
 * accessToken = xxxxx
 * uid = id1, id2, id3...
 * screenname = screenname1, screenname2, screenname3 ...
 * name = name1, name2, name3 ...
 * 
 */
public class CollectorDaemon {

	private static String UID = "uid";
	private static String CMD_STOP = "stop";
	private static String ACCESS_TOKEN = "accessToken";
	
	static Logger log = Logger.getLogger(Collector.class.getName());
	
	public static void main(String args[]) {
		String accessToken = null;
		if(args.length > 0) {
			accessToken = args[0];
		}
		
		String intialUserPoolFileName = "initialUserPoo.properties";
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
		
		if(accessToken == null && !StringUtils.trimToEmpty(config.getString(ACCESS_TOKEN)).equals("") ) {
			accessToken = StringUtils.trimToEmpty(config.getString(ACCESS_TOKEN));
		} else {
			log.error("no access token provided");
			return;
		}
		
		AccessTokenUtil.setAccessToken(accessToken);
		Collector collector = new Collector(SpringUtil.getDao());
		List<String> uids = new ArrayList<String>();
		for(String uid : config.getStringArray(UID)) {
			uids.add(uid);
		}
		collector.initUidPool(uids);
		CollectorScheduler scheduler = new CollectorScheduler(collector);
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
