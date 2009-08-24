package api;

import api.struct.Board;
import internal.Player;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author ablaine
 */
public abstract class PlayerLogic {
	private Player player;
	private State state;
	
	/* Python convention to discourage use. */
	public final void _init(Player player, State state) {
		if (this.player == null) {
			this.player = player;
			this.state = state;
		}
	}

	public final String getName() {
		return player.getName();
	}

	public final String getFullName() {
		return player.getFullName();
	}

	public final State getState() {
		return state;
	}

	public void init() { }

	public void gameOver() { }

	public abstract Point makeMove(Board board, List<Point> validMoves);
}
