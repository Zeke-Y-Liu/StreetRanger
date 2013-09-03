package com.ranger.common;

//CREATE TABLE `WB_VISIBLE` (
//		  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
//		  `TYPE` int(11) DEFAULT -1,
//		  `LIST_ID` int(11) DEFAULT -1,
//		  PRIMARY KEY (`ID`)
//)

/*
 * Constatants class
 */

public class Visible {
	private long id;
	private int type;
	private int listId;
	
	public Visible(long id, int type, int listId) {
		this.id = id;
		this.type = type;
		this.listId = listId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
}
