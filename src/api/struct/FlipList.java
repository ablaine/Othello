package api.struct;

import api.State;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author ablaine
 */
public final class FlipList extends LinkedList<Point> {
	private final State state;

	public FlipList(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
}
