package com.ranger.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
