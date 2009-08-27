package api.struct;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple <code>Move</code> oriented list.
 * 
 * @author Andrew Blaine
 */
public final class MoveList extends LinkedList<Move> {

	/**
	 * Extracts just the locations from the <code>Move</code> elements and
	 * returns the list.
	 *
	 * @return a list of just the locations
	 */
	public List<Point> toPoints() {
		List<Point> result = new LinkedList<Point>();
		for (Move m : this) {
			result.add(m.getLocation());
		}
		return result;
	}
}
