package api.struct;

import api.State;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a list of points to be flipped and the state that represents
 * these points.
 * 
 * @author Andrew Blaine
 */
public final class FlipList extends LinkedList<Point> {
	private final State state;

	/**
	 * Constructs an empty <code>FlipList</code>.
	 * 
	 * @param state The state representing this object.
	 */
	public FlipList(State state) {
		this.state = state;
	}

	/**
	 * Constructs a <code>FlipList</code> with an initial list of points.
	 * 
	 * @param list The initial list to create this list with.
	 * @param state The state representing this object.
	 */
	public FlipList(List<Point> list, State state) {
		super(list);
		this.state = state;
	}

	/**
	 * The state represents this object's elements.
	 * 
	 * @return the state representing this object
	 */
	public State getState() {
		return state;
	}
}
