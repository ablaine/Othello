package api;

import java.util.Random;

/**
 * This enum represents one of three states an Othello board tile can be, a
 * dark piece, a light piece or an empty spot. This class is additionally used
 * to associate players with which pieces they own (e.g. light and dark).
 *
 * @author Andrew Blaine
 */
public enum State {
	EMPTY, LIGHT, DARK;

	private final static Random random = new Random();

	/**
	 * If this instance is <code>LIGHT</code>, this returns <code>DARK</code>,
	 * and vice versa. However, if this instance is <code>EMPTY</code>, this
	 * returns itself.
	 * 
	 * @return the opposite state, or <code>EMPTY</code> if that is the current
	 * instance
	 */
	public State getOpposite() {
		switch(this) {
			case LIGHT: return DARK;
			case DARK: return LIGHT;
			default: return EMPTY;
		}
	}

	/**
	 * Returns a random instance of either <code>LIGHT</code> or
	 * <code>DARK</code>.
	 * 
	 * @return either <code>LIGHT</code> or <code>DARK</code>, picked at random
	 */
	public static State getRandom() {
		if (random.nextInt(2) == 0) {
			return DARK;
		} else {
			return LIGHT;
		}
	}
}
