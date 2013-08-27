package com.ranger.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Tag {

	public Tag(Long peopleId, String name) {
		this._peopleId = peopleId;
		this._name = name;
	}
	
	private String _name;
	private Long _peopleId;
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public Long getPeopleId() {
		return _peopleId;
	}

	public void setPeopleId(Long peopleId) {
		this._peopleId = peopleId;
	}
	
	@Override
	public int hashCode() {
	     return new HashCodeBuilder(13, 23)
	       .append(_name)
	       .append(_peopleId)
	       .toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		Tag t = (Tag) obj;
		return new EqualsBuilder()
			.append(_name, t.getName())
			.append(_peopleId, t.getPeopleId())
			.isEquals();
	}
	
}
