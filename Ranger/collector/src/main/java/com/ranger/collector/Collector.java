package com.ranger.collector;

import com.ranger.common.User;

/*
 * 1. hold a set of weibo user id in a pool, for each id, get user's information
 * 2. put the user into user pool, if the pool is full, flush the data pool to database
 * 3. add the user's friend uid to user id pool
 * 4. for each id, get user's information recursively
 */

public class Collector {

	public User getUserInfoByUid(String uid) {
		User u = new User();
		// private DataSetPool avaiableUserIdSetPool = new  DataSetPool();
		// private DataSetPool userSetPool = new DataSetPool();
		return u;
	}
	/*
	 * return the number of available user ids
	 */
	public int getAvaiableUserIdCount() {
		// return avaiableUserIdSetPool.size();
		return 0;
	}
	/*
	 * return the number of the users in pool
	 * used to determine if we exceed the restriction of 1000 request per hour
	 */
	public int getUserPoolSize() {
		// return userSetPool.size();
		
		return 0;
	}
	/*
	 * this method is self-evidence
	 */
	public boolean isUserPoolFull() {
		// return userPool.isFull();
		return false;
	}
}
