
package othello;

import java.awt.Point;

/**
 * Represents a single othello piece by it's location and state.
 * @author ablaine
 */
public class Piece {
	private Point location;
	private State state;

	public Piece(Point location, State state) {
		this.location = location;
		this.state = state;
	}

	public Point getLocation() {
		return location;
	}

	public State getState() {
		return state;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setState(State state) {
		this.state = state;
	}

}
