package com.ranger.util;


import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.URL;

public class PropertiesUtil {
	
	public static String ATTR_ACCESS_TOKEN = "accessToken";	
	public static String ATTR_TIME_GAP_THRESHOLD = "timeGapThreshold";
	public static String ATTR_BATCH_SIZE = "batchSize";
	public static String ATTR_COLLECTOR_KICK_OFF_INTERVAL = "collectorKickOffInterval";
	
    private PropertiesUtil() {  }
    /**
     * Load a properties file from the classpath
     * @param propsName
     * @return Properties
     * @throws Exception
     */
    public static Properties load(String propsName) throws Exception {
        Properties props = new Properties();
        URL url = ClassLoader.getSystemResource(propsName);
        props.load(url.openStream());
        return props;
    }

    /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public static Properties load(File propsFile) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);    
        fis.close();
        return props;
    }
}