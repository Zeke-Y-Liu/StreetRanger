package com.ranger.collector;

public class CollectorRunnable implements Runnable {
	public static short COMMAND_STOP = 0;
	// an external command to interfere this thread, for example, resume the
	// thread by notifying
	// suspend this thread by stop.
	private short cmd;
	private CollectorScheduler scheduler;

	public synchronized void setCommand(short cmd) {
		this.cmd = cmd;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// cmd is not stop
		while (cmd != 0) {
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
