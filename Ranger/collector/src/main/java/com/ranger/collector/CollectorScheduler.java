package com.ranger.collector;

import org.apache.log4j.Logger;
/*
 *   having scheduler to decouple the thread related code and business logic.
 * As we know, thread related code is hard to test and often cause trouble, 
 * so try to minimum the code and logic in thread related class.
 */

public class CollectorScheduler {
	
	static Logger log = Logger.getLogger(CollectorScheduler.class.getName());
	
	// request times within hour, will be reset when next recording period(next hour) begins
	private int reqeustTimes;
	
	// the time when the first request occurs, used to determine if the requests number reaches 1000 within an hour 
	// will be reset when next recording period(next hour) begins
	long periodStartTime = System.currentTimeMillis();
	
	// an hour in second
	private long AN_HOUR_IN_MILLISECOND = 60*60*1000;
	
	private Collector collector;
	
	public CollectorScheduler(Collector collector) {
		this.collector = collector;
	}
	/*
	 * schedule the collector according to the policy and restriction of weibo API.
	 * return 
	 * 1. -1 if collector can proceed, 
	 * 2. 0 if collection need to suspend, for example there is no available new user, 
	 * 3. positive number if the collector need to sleep some time because of the 
	 * number of request per hour
	 */
	long schedule() {
		
		long result = -1;
		reqeustTimes ++;
		long timeElapsed = System.currentTimeMillis() - periodStartTime;
		if(reqeustTimes < 1000) {
			if(timeElapsed >= AN_HOUR_IN_MILLISECOND) {
				// an hour elapsed, reset and count
				periodStartTime = System.currentTimeMillis();
				reqeustTimes = 1;
				// if request times is less than 800 per hour, considering multiple thread. 
				if(reqeustTimes < 800) {
					log.info("Less than 1000 request per hour, suffering performance issue, considering multiple thread" + "time=" + timeElapsed + " request=" + reqeustTimes);
				}
			}
			result = -1;
		} else {
			result = AN_HOUR_IN_MILLISECOND - timeElapsed;
			
			// if request times reach 1000 within 40 mins, considering multiple token. 
			if(timeElapsed < 40*60*1000) {
				log.info("1000 requests within 40 mins, suffering performance issue, considering multiple token" + "time=" + timeElapsed + " request=" + reqeustTimes);
			}
		}
		collector.collect();
		if(collector.isReadyToFlush()) {
			collector.flush2DB();
		}
		return result;
	}
}
