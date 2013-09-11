package com.ranger.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	 	private static ApplicationContext  ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	 	private static String BEAN_NAME_DAO = "dao";
	 		 	
	    public static Object getBean(String beanName){
	         return ctx.getBean(beanName);
	    }   
	    
	    public static Dao getDao() {
	    	return (Dao)ctx.getBean(BEAN_NAME_DAO);
	    }
}
