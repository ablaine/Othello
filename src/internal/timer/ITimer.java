package internal.timer;

/**
 * This interface describes a timer for keeping track of how long a player has
 * taken in deciding their move.
 *
 * @author Andrew Blaine
 */
public interface ITimer {

	public boolean isActive();

	public boolean expired();

	public long elapsedTime();

	public long remainingTime();

	public void reset();
}
