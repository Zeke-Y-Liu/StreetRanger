package com.ranger.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ranger.util.Util;

public class SpringUtil {
		
	 	private static ApplicationContext  ctx = new FileSystemXmlApplicationContext(Util.getBaseDir() + "config/applicationContext.xml");
	 	private static String BEAN_NAME_DAO = "dao";
	 		 	
	    public static Object getBean(String beanName){
	         return ctx.getBean(beanName);
	    }   
	    
	    public static Dao getDao() {
	    	return (Dao)ctx.getBean(BEAN_NAME_DAO);
	    }
}
