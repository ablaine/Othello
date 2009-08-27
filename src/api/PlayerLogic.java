package api;

import api.struct.Board;
import internal.Player;
import java.awt.Point;
import java.util.List;

/**
 * This abstract class handles choosing the next piece to play in the form
 * of the abstract method <code>makeMove</code>.
 * The methods <code>getName</code>, <code>getFullName</code> and
 * <code>getState</code> are mostly just for conviencence. The methods
 * <code>init</code> and <code>gameOver</code> are optional hooks that allow
 * implementing AIs to perform some pre-match initilizations and post-match
 * cleanup/analyzations.<br/>
 * <br/>
 * Developers who implement this class are encouraged to use only the classes
 * provided by the api package.
 * 
 * @author Andrew Blaine
 */
public abstract class PlayerLogic {
	private Player player;
	private State state;
	
	/**
	 * This method is reserved for internal use. Note, attempts to call this
	 * method (aside from the initial PlayerFactory call) will fail to have
	 * any effect. The preceding underscore is a python convention which
	 * intends to discourage use.
	 * 
	 * @param player This logic's <code>Player</code> object.
	 * @param state This logic's <code>State</code>.
	 */
	public final void _init(Player player, State state) {
		if (this.player == null) {
			this.player = player;
			this.state = state;
		}
	}

	/**
	 * The canonical (or fully qualified) name of the class.<br/>
	 * e.g. <code>impl.ai.RandomPlayer</code>
	 * 
	 * @return this player logic's name
	 */
	public final String getName() {
		return player.getName();
	}

	/**
	 * The canonical (or fully qualified) name of the class, appended with this
	 * player logic's state.<br/>
	 * e.g. <code>impl.ai.RandomPlayer(DARK)</code>
	 * 
	 * @return this player logic's full name
	 */
	public final String getFullName() {
		return player.getFullName();
	}

	/**
	 * The state of this player logic. One of either <code>State.LIGHT</code>
	 * or <code>State.DARK</code>.
	 * 
	 * @return this player logic's state
	 */
	public final State getState() {
		return state;
	}

	public final GameClock getGameClock() {
		return player.getGameClock();
	}
	
	/**
	 * An optional hook that is called prior to the beginning the match. You
	 * can perform any initialization routines or pre-calculation that you
	 * need here.
	 */
	public void init() { }

	/**
	 * An optional hook that is called after the match has ended. The board will
	 * not be cleared yet, so the final board can be saved and/or analyzed.
	 */
	public void gameOver() { }

	/**
	 * Provided the current state of the board and a list of valid moves, this
	 * method is reqested to return its choosen move. You are guarenteed that
	 * a move is possible for you if this method is called. Returning a
	 * <code>null</code> or otherwise invalid move results in a forfeiture of
	 * this match.<br/>
	 * <br/>
	 * While not required, you are encouraged to utilize the API provided
	 * classes found in the <code>api</code> package.
	 * 
	 * @param board The current state of the game board.
	 * @param validMoves The list of all valid moves for this player.
	 * @return a <code>java.awt.Point</code> object with x=column and y=row
	 */
	public abstract Point makeMove(Board board, List<Point> validMoves);
}
