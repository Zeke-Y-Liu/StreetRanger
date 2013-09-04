package com.ranger.common;

import java.util.ArrayList;
import java.util.List;

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

public class Visible implements DataObject {
	private Long id;
	private Integer type;
	private Integer listId;
	
	public Visible(Long id, Integer type, Integer listId) {
		this.id = id;
		this.type = type;
		this.listId = listId;
	}
		
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public Integer getListId() {
		return listId;
	}



	public void setListId(Integer listId) {
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

	@Override
	public List<DataField> getDataFields() {
List<DataField> dataFieldList = new ArrayList<DataField>(); 
		
		DataField dataField = new DataField(DataFieldType.LONG, 1, id);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.INTEGER, 2, type);
		dataFieldList.add(dataField);
		dataField = new DataField(DataFieldType.INTEGER, 3, listId);
		dataFieldList.add(dataField);
		
		return dataFieldList;
	}
}
