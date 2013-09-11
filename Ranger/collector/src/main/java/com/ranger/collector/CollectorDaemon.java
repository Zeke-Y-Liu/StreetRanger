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

import sun.security.krb5.Config;

import com.ranger.dao.Dao;
import com.ranger.dao.SpringUtil;
import com.ranger.util.AccessTokenUtil;

/*
 * run this class with parameter 
 * 1. access token -- required
 * 2. name of the initial user pool file optional, default to initialUserPoo.properties
 * the file contains a set of screenname, uid or name
 * 
 * the format of int initial user pool file can be
 * uid = id1, id2, id3...
 * screenname = screenname1, screenname2, screenname3 ...
 * name = name1, name2, name3 ...
 * 
 */
public class CollectorDaemon {

	private static String UID = "uid";
	private static String CMD_STOP = "stop";
	
	static Logger log = Logger.getLogger(Collector.class.getName());
	
	public static void main(String args[]) {
		String accessToken = args[0];
		AccessTokenUtil.setAccessToken(accessToken);
		String intialUserPoolFileName = "initialUserPoo.properties";
		if(args.length > 1 && !StringUtils.trimToEmpty(args[1]).equals("")) {
			intialUserPoolFileName = args[1];
		}
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(intialUserPoolFileName);
		} catch (ConfigurationException e) {
			log.error("unable to load initial user pool file" + intialUserPoolFileName, e);
		}
		
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
			while(CMD_STOP.equalsIgnoreCase(commandLine)) {
				commandLine = reader.readLine();
			}
		} catch (IOException e) {
			log.error(e);
		}

	}	
}
