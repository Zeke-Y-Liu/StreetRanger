package com.ranger.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Source implements DataObject {

	private Long id;
	private String url;
	private String relationship;
	private String name;
	
	public Source(Long id, String url, String relationship, String name) {
		this.id = id;
		this.url = url;
		this.relationship = relationship;
		this.name = name;
	}
	
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
	
	@Override
	public int hashCode() {
	     return new HashCodeBuilder(31, 47)
	       .append(url)
	       .append(relationship)
	       .append(name)
	       .toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		Source s = (Source) obj;
		return new EqualsBuilder()
			.append(url, s.getUrl())
			.append(relationship, s.getRelationship())
			.append(name, s.getName())
			.isEquals();
	}

	@Override
	public List<DataField> getDataFields() {
		List<DataField> dataFieldList = new ArrayList<DataField>(); 		
		DataField dataField = new DataField(DataFieldType.LONG, 1, id);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 2, url);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 3, relationship);
		dataFieldList.add(dataField);		
		dataField = new DataField(DataFieldType.STRING, 4, name);
		dataFieldList.add(dataField);
		
		return dataFieldList;
	}
	
}
