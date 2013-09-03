package com.ranger.common;

//CREATE TABLE `WB_SOURCE` (
//		  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
//		  `URL` varchar(200) DEFAULT '',
//		  `RELATIONSHIP` varchar(200) DEFAULT '',
//		  `NAME` varchar(200) DEFAULT '',
//		  PRIMARY KEY (`ID`)
//)

public class Source {

	private long id;
	private String url;
	private String relationship;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
