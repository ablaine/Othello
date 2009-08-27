package api;

import api.util.UnitConversion;
import api.util.UnitConversion.Unit;
import jig.engine.GameClock.Alarm;

/**
 * Represents the game clock to keep track of the total time each player takes
 * to make their move.
 * 
 * @author Andrew Blaine
 */
public class GameClock {
	private Alarm alarm;

	/**
	 * Constructs a game clock using the given alarm.
	 * 
	 * @param alarm The alarm to use.
	 */
	public GameClock(Alarm alarm) {
		this.alarm = alarm;
	}

	/**
	 * Returns the currently remaining time for the current player to make a
	 * move.<br/>
	 * <br/>
	 * By default, this number is in nanoseconds.
	 *
	 * @return a long reperesenting the remaining time to decide your move
	 */
	public long getRemainingTime() {
		return alarm.remainingTime();
	}

	/**
	 * Returns the currently remaining time for the current player to make a
	 * move.
	 * 
	 * @param unit The unit you want the result in.
	 * @return a long reperesenting the remaining time to decide your move
	 */
	public long getRemainingTime(Unit unit) {
		switch (unit) {
			case SECOND:      return UnitConversion.convert(alarm.remainingTime(), Unit.NANOSECOND, Unit.SECOND);
			case MILLISECOND: return UnitConversion.convert(alarm.remainingTime(), Unit.NANOSECOND, Unit.MILLISECOND);
			case MICROSECOND: return UnitConversion.convert(alarm.remainingTime(), Unit.NANOSECOND, Unit.MICROSECOND);
			case NANOSECOND:  return alarm.remainingTime();
		}
		System.err.println("Switch statement does not handle all cases:");
		System.err.println("@ getRemainingTime(Unit unit) in " + GameClock.class.getCanonicalName());
		System.exit(0);
		return 0;
	}

	/**
	 * Returns the elapsed time for the current player since they were given
	 * control to make a move.<br/>
	 * <br/>
	 * By default, this number is in nanoseconds.
	 *
	 * @return a long reperesenting the elapsed time since you were requested
	 * to move
	 */
	public long getElapsedTime() {
		return alarm.elapsedTime();
	}

	/**
	 * Returns the elapsed time for the current player since they were given
	 * control to make a move.
	 *
	 * @param unit The unit you want the result in.
	 * @return a long reperesenting the elapsed time since you were requested
	 */
	public long getElapsedTime(Unit unit) {
		switch (unit) {
			case SECOND:      return UnitConversion.convert(alarm.elapsedTime(), Unit.NANOSECOND, Unit.SECOND);
			case MILLISECOND: return UnitConversion.convert(alarm.elapsedTime(), Unit.NANOSECOND, Unit.MILLISECOND);
			case MICROSECOND: return UnitConversion.convert(alarm.elapsedTime(), Unit.NANOSECOND, Unit.MICROSECOND);
			case NANOSECOND:  return alarm.elapsedTime();
		}
		System.err.println("Switch statement does not handle all cases:");
		System.err.println("@ getElapsedTime(Unit unit) in " + GameClock.class.getCanonicalName());
		System.exit(0);
		return 0;
	}
}
