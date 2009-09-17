package internal.timer;

import jig.engine.GameClock.Alarm;

/**
 * This implementation will keep track of how long a player has left to make
 * their move.
 * 
 * @author Andrew Blaine
 */
public class TimedTimer implements Timer {
	private final Alarm alarm;

	public TimedTimer(Alarm alarm) {
		this.alarm = alarm;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public boolean expired() {
		return alarm.expired();
	}

	@Override
	public long elapsedTime() {
		return alarm.elapsedTime();
	}

	@Override
	public long remainingTime() {
		return alarm.remainingTime();
	}

	@Override
	public void reset() {
		alarm.reset();
	}

}
