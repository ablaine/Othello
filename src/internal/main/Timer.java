package internal.main;

import jig.engine.GameClock.Alarm;

/**
 *
 * @author ablaine
 */
public class Timer {
	private final Alarm alarm;

	public Timer(Alarm alarm) {
		this.alarm = alarm;
	}

	public synchronized long remainingTime() {
		return alarm.remainingTime();

	}

	public synchronized long elapsedTime() {
		return alarm.elapsedTime();
	}

	public synchronized boolean expired() {
		return alarm.expired();
	}

	public synchronized void reset() {
		alarm.reset();
	}

}
