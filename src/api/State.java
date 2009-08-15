package api;

import java.util.Random;

/**
 *
 * @author ablaine
 */
public enum State {
	EMPTY, LIGHT, DARK;

	private final static Random random = new Random();

	public State getOpposite() {
		switch(this) {
			case LIGHT: return DARK;
			case DARK: return LIGHT;
			default: return EMPTY;
		}
	}

	public static State getRandom() {
		if (random.nextInt(2) == 0) {
			return DARK;
		} else {
			return LIGHT;
		}
	}
}
