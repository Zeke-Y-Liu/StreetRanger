package com.ranger.collector;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

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

	static Logger log = Logger.getLogger(Collector.class.getName());
	
	public static void main(String args[]) {
		
		String intialUserPoolFileName = args[0];
		
		
		try {
			Configuration config = new PropertiesConfiguration("usergui.properties");
		} catch (ConfigurationException e) {
			log.error("unable to load initial user pool file" + intialUserPoolFileName, e);
		}
	}
	
	
}
