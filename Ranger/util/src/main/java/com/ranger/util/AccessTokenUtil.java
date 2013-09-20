package com.ranger.util;

// this class is intended to be singleton, initialzed during app start up.
// it's rare to change the access token.
// if necessary, we can re-factor this class to hold a set of accessToken to be used periodically

/*
 * to improve the cohesion of the code remove this class
 * reason is that:
 * this class is initially designed with a singleton pattern to facilitate the access to access token
 *   
 * it's used in collector internally.
 * NOT used, feel free to remove this class
 */
@Deprecated
public class AccessTokenUtil {
	private static  String accessToken = "";	
	public static synchronized String getAccessToken() {
		return accessToken;
	}
	
	public static synchronized void setAccessToken(String newAccessToken) {
		accessToken = newAccessToken;
	}
}
