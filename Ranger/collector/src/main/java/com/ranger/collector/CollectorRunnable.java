package com.ranger.collector;

import org.apache.log4j.Logger;

public class CollectorRunnable implements Runnable {
	static Logger log = Logger.getLogger(CollectorRunnable.class.getName());
	
	public static short COMMAND_STOP = 99;
	// an external command to interfere this thread, for example, resume the
	// thread by notifying
	// suspend this thread by stop.
	private short cmd;
	private DynamicCollectorScheduler scheduler;

	public CollectorRunnable(DynamicCollectorScheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public synchronized void setCommand(short cmd) {
		this.cmd = cmd;
	}
	
	@Override
	public void run() {
		// cmd is not stop
		while (cmd != COMMAND_STOP) {
			long result = scheduler.schedule();
			if (result == 0) {
				synchronized (scheduler) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
						log.warn(e);
					}
				}
			} else if (result > 0) {
				synchronized (scheduler) {
					try {
						scheduler.wait(result);
					} catch (InterruptedException e) {
						log.warn(e);
					}
				}
			}  // else go on
		}
	}
}
