package com.ranger.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	@Override
	public int hashCode() {
	     return new HashCodeBuilder(23, 31)
	       .append(type)
	       .append(listId)
	       .toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		Visible v = (Visible) obj;
		return new EqualsBuilder()
			.append(type, v.getType())
			.append(listId, v.getListId())
			.isEquals();
	}
}
