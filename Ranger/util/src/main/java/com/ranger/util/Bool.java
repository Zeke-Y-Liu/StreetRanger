package com.ranger.util;

public enum Bool {
	TRUE("Y"), FALSE("N");
	
	private String value;
	private Bool(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	public static Bool getBool(boolean value) {
		if(value) {
			return TRUE;
		} else {
			return FALSE;
		}
	}
	
	public static boolean getBoolean(String value) {
		if(TRUE.getValue().equalsIgnoreCase(value)) {
			return true;
		} else {
			return false;
		}
	}
}
