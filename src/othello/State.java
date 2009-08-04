package othello;

/**
 *
 * @author ablaine
 */
public enum State {
	EMPTY, LIGHT, DARK;

	public State getOpposite() {
		switch(this) {
			case LIGHT: return DARK;
			case DARK: return LIGHT;
		}
		return EMPTY;
	}
}
