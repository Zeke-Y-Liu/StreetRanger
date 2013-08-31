package com.ranger.collector;
/*
 *   having scheduler to decouple the thread related code and business logic.
 * As we know, thread related code is hard to test and often cause trouble, 
 * so try to minimum the code and logic in thread related class.
 */
public class CollectorScheduler {
	private Collector collector;
	/*
	 * schedule the collector according to the policy and restriction of weibo API.
	 * return 
	 * 1. -1 if collector can proceed, 
	 * 2. 0 if collection need to suspend, for example there is no available user id, 
	 * 3. positive number if the collector need to sleep some time because of the 
	 * number of request per hour
	 */
	int schedule() {
		return 0;
	}
}
