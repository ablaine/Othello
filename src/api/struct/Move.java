package api.struct;

import api.State;
import java.awt.Point;

/**
 * Represents a container for a single move on the game board. This contains
 * the <code>State</code> of the player making the move, and the
 * <code>Point</code> or "location" of the piece on the board.
 *
 * @author Andrew Blaine
 */
public final class Move {
	private final State playerState;
	private final Point location;

	/**
	 * Constructs a move object.
	 * 
	 * @param playerState
	 * @param location
	 */
	public Move(State playerState, Point location) {
		this.playerState = playerState;
		this.location = location;
	}

	/**
	 * Returns the player state that is making the move.
	 * 
	 * @return player state that owns this move
	 */
	public State getPlayerState() {
		return playerState;
	}

	/**
	 * Returns the location of the move on the board.
	 *
	 * @return location (x, y) coordinate of the move
	 */
	public Point getLocation() {
		return location;
	}
}
