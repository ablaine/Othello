package internal;

/**
 *
 * @author ablaine
 */
public class MatchStatus {
	public static enum State { INVALID, NOT_PLAYED, TIED, WINNER }
	private final Player winner;
	private final State state;

	public final static MatchStatus INVALID = new MatchStatus(null, State.INVALID);

	private MatchStatus(Player p, State s) {
		winner = p;
		state = s;
	}

	public MatchStatus() {
		this(null, State.NOT_PLAYED);
	}

	public MatchStatus(Player p) {
		winner = p;
		if (winner == null) {
			state = State.TIED;
		} else {
			state = State.WINNER;
		}
	}

	public State getState() {
		return state;
	}

	/**
	 * Calling this method when <code>getState</code> returns anything other
	 * than <code>State.WINNER</code> will result in <code>null</code>.
	 * 
	 * @return
	 */
	public Player getWinner() {
		if (state == State.WINNER) {
			return winner;
		}
		return null;
	}
}
