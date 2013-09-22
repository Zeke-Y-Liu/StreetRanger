package com.ranger.common;

public class RequestRateLimit {
	private long userLimit;
	private long remainingUserHits;
	private long resetTimeInSecond;
	
	public RequestRateLimit(long userLimit, long remainingUserHits, long resetTimeInSecond) {
		this.userLimit = userLimit;
		this.remainingUserHits = remainingUserHits;
		this.resetTimeInSecond = resetTimeInSecond;
	}

	public long getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(long userLimit) {
		this.userLimit = userLimit;
	}

	public long getRemainingUserHits() {
		return remainingUserHits;
	}

	public void setRemainingUserHits(long remainingUserHits) {
		this.remainingUserHits = remainingUserHits;
	}

	public long getResetTimeInSecond() {
		return resetTimeInSecond;
	}

	public void setResetTimeInSecond(long resetTimeInSecond) {
		this.resetTimeInSecond = resetTimeInSecond;
	}	
}
