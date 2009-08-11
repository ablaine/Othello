package othello;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author ablaine
 */
public class FlipList extends LinkedList<Point> implements IState {
	private final State state;

	public FlipList(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
}
