package com.ranger.collector;

public class CollectorRunnable implements Runnable {
	// an external command to intefer this thread, for example, resume the
	// thread by notifying
	// suspend this thread by stop.
	private short command;
	private CollectorScheduler scheduler;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// cmd is not stop
		while (command != 0) {
			int result = scheduler.schedule();
			if (result == 0) {
				synchronized (scheduler) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (result > 0) {
				synchronized (scheduler) {
					try {
						scheduler.wait(result);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}  // else go on
		}
	}
}
