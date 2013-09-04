package com.ranger.dao;

import java.util.ArrayList;
import java.util.List;

import com.ranger.common.Visible;
import com.ranger.dao.Dao;
import com.ranger.dao.SpringUtil;

public class VisibleConstant {

	private static List<Visible> visibleList = new ArrayList<Visible>();
	static {
		Dao dao = (Dao) SpringUtil.getBean("dao");
		visibleList = dao.loadAllVisible();
	}
	// get persisted visible id by visible object 
	public static  long getVisibleId(Visible visible) {
		for(Visible v : visibleList) {
			if(v.equals(visible)) {
				return v.getId();
			}
		}
		// not find in db, insert a new one
		Dao dao = (Dao) SpringUtil.getBean("dao");
		dao.insertVisible(visible);
		visibleList.add(visible);
		return visible.getId();
	}
}
