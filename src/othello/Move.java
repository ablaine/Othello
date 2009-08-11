package othello;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * Hmm, if the board were to change in the future.. this will be all messed up.
 * I could solve if I were to clone the main board for each possible move...
 * Maybe make a getMove off the board.
 *
 * @author ablaine
 */
public class Move implements IState {
	private final Point loc;
	private final State state;
	private final Board board;
	private FlipList flipped = null;

	public Move(Point location, State state, Board board) {
		this.loc = location;
		this.state = state;
		this.board = board;
	}

	public FlipList getFlipped() {
		if (flipped == null) {
			flipped = new FlipList(state);
			if (isWithinBounds() && board.getState(loc) == State.EMPTY) {
				followPathInit(new Point(-1, +1));//Left	Up
				followPathInit(new Point( 0, +1));//		Up
				followPathInit(new Point(+1, +1));//Right	Up
				followPathInit(new Point(+1,  0));//Right
				followPathInit(new Point(+1, -1));//Right	Down
				followPathInit(new Point( 0, -1));//		Down
				followPathInit(new Point(-1, -1));//Left	Down
				followPathInit(new Point(-1,  0));//Left

				if (flipped.size() > 0) {
					flipped.add(loc);
				}
			}
		}
		return flipped;
	}

	private void followPathInit(Point dir) {
		followPath(loc.x + dir.x, loc.y + dir.y, dir, new LinkedList<Point>());
	}

	private void followPath(int locX, int locY, Point dir, List<Point> potentialFlips) {
		if (board.isWithinBounds(locX, locY)) {
			State boardState = board.getState(locX, locY);
			if (boardState == state.getOpposite()) {
				potentialFlips.add(new Point(locX, locY));
				followPath(locX + dir.x, locY + dir.y, dir, potentialFlips);
			} else if (boardState == state) {
				flipped.addAll(potentialFlips);
			}
		}
	}

	public boolean isValid() {
		return getFlipped().size() > 0;
	}

	public boolean isWithinBounds() {
		return board.isWithinBounds(loc);
	}

	public Point getLocation() {
		return loc;
	}

	public State getState() {
		return state;
	}

	public Board getBoard() {
		return board;
	}
}
