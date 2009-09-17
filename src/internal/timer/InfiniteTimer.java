package internal.timer;

import jig.engine.GameClock.Alarm;

/**
 * This implementation is for matches that do not limit the player's time to
 * make a move.
 *
 * @author Andrew Blaine
 */
public class InfiniteTimer implements Timer {
	private final Alarm alarm;

	/** The alarm is only for keeping track of elapsed time. */
	public InfiniteTimer(Alarm alarm) {
		this.alarm = alarm;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean expired() {
		return false;
	}

	@Override
	public long elapsedTime() {
		return alarm.elapsedTime();
	}

	@Override
	public long remainingTime() {
		return -1;
	}

	@Override
	public void reset() {
		alarm.reset();
	}

}
