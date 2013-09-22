package com.ranger.collector;

import com.ranger.common.RequestRateLimit;


/*
 * capability of collect vast majority of user's info with very few seed data.
 * also provide some APIs for client to test and manage the internal status
 * possible solution:
 * 1. crawl all the weibo users by friendship
 * 2. search user with different criteria
 * 3. get latest status and then get the user's infor from status 
 * 
 */


public interface Collector {

	public void collect();
	public int flush2DB();
	/*
	 * when all the users are populated, flush to database
	 */
	public boolean isReadyToFlush();
	
	public RequestRateLimit getRateLimit();
}
