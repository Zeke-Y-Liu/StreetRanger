package com.ranger.common;

import java.util.ArrayList;
import java.util.List;

import com.ranger.dao.Dao;
import com.ranger.dao.SpringUtil;

public class SourceConstant {

	private static List<Source> sourceList = new ArrayList<Source>();
	static {
		Dao dao = (Dao) SpringUtil.getBean("dao");
		sourceList = dao.loadAllSource();
	}
	// get persisted visible id by visible object 
	public static  long getSourceId(Source source) {
		for(Source s : sourceList) {
			if(s.equals(source)) {
				return s.getId();
			}
		}
		// not find in db, insert a new one
		Dao dao = (Dao) SpringUtil.getBean("dao");
		dao.insertSource(source);
		sourceList.add(source);
		return source.getId();
	}
	
}
