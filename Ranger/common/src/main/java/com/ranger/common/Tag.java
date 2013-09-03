package com.ranger.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

//CREATE TABLE `WB_TAG` (
//		  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
//		  `TID` char(20) NOT NULL,
//		  `VALUE` varchar(200) NOT NULL,
//		  `WEIGHT` varchar(200) NOT NULL,
//		  `USER_ID` bigint(20) DEFAULT NULL, -- user db primary key id, not the unique user uid
//		  PRIMARY KEY (`ID`),
//		  UNIQUE KEY `TID_UNIQUE` (`TID`),
//		  CONSTRAINT `USER_ID_TAG` FOREIGN KEY (`USER_ID`) REFERENCES `wb_user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
//)

public class Tag {

	public Tag(Long id, String tid, String value, String weight, Long userId) {
		this.id = id;
		this.tid = tid;
		this.value = value;
		this.weight = weight;
		this.userId = userId;
	}
	
	private Long id;
	private String tid;
	private String value;
	private String weight;
	private Long userId;	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
		
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
	     return new HashCodeBuilder(13, 23)
	       .append(tid)
	       .toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		Tag t = (Tag) obj;
		return new EqualsBuilder()
			.append(tid, t.getTid())
			.isEquals();
	}
	
}
