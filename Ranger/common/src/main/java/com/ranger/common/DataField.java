package com.ranger.common;

public class DataField {
	private DataFieldType type;
	private int index;
	private Object value;
	public DataFieldType getType() {
		return type;
	}
	public void setType(DataFieldType type) {
		this.type = type;
	}	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}	
}
