package othello;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class MoveList extends LinkedList<Move> {

	public List<Point> toPoints() {
		List<Point> result = new LinkedList<Point>();
		for (Move m : this) {
			result.add(m.getLocation());
		}
		return result;
	}
}
