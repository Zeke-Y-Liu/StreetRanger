package com.ranger.util;

// this class is intended to be singleton, initialzed during app start up.
// it's rare to change the access token.
// if necessary, we can re-factor this class to hold a set of accessToken to be used periodically 
public class AccessTokenUtil {
	private static  String accessToken = "";	
	public static synchronized String getAccessToken() {
		return accessToken;
	}
	
	public static synchronized void setAccessToken(String newAccessToken) {
		accessToken = newAccessToken;
	}
}
