package api.struct;

import api.*;
import java.awt.Point;

/**
 *
 * @author ablaine
 */
public final class Move {
	private final State playerState;
	private final Point location;

	public Move(State playerState, Point location) {
		this.playerState = playerState;
		this.location = location;
	}

	public State getPlayerState() {
		return playerState;
	}

	public Point getLocation() {
		return location;
	}
}
