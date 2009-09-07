package api;

import api.struct.Board;
import api.struct.Move;
import api.struct.FlipList;
import api.struct.MoveList;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import jig.engine.GameClock.Alarm;

/**
 * Handles all Othello game logic such as applying a move to a board,
 * determining valid moves given a state and board, etc.
 *
 * @author Andrew Blaine
 */
public class GameLogic {

	/**
	 * Applies the move to a clone of the input board and returns the clone.
	 * This will leave the passed in board unchanged.
	 *
	 * @param board The board to update.
	 * @param move The move to apply.
	 * @return the new board after having applied the move
	 */
	public static Board makeHypotheticalMove(Board board, Move move) {
		Board clone = board.clone();
		makeDestructiveMove(clone, move);
		return clone;
	}

	/**
	 * Destructively modifies the input board to reflect the changes of the
	 * move being applied to the passed in board. If an invalid move is passed
	 * in, the input board will be unmodified.
	 *
	 * @param board The board to update.
	 * @param move The move to apply.
	 */
	public static void makeDestructiveMove(Board board, Move move) {
		board.modify(new Helper(board, move).getFlipped());
	}

	/**
	 * Returns all valid moves for a given player's state and board.
	 * 
	 * @param board The board to examine.
	 * @param playerState The player who can perform these moves.
	 * @return a list of valid <code>Move</code>s for this board.
	 */
	public static MoveList getValidMoves(Board board, State playerState) {
		MoveList result = new MoveList();
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Move move = new Move(playerState, new Point(x, y));
				if (isValidMove(board, move)) {
					result.add(move);
				}
			}
		}
		return result;
	}

	/**
	 * Determines whether the input move being applied to the input board is
	 * valid.
	 * 
	 * @param board The board to examine.
	 * @param move The move to check.
	 * @return a boolean specifying whether this move would be valid.
	 */
	public static boolean isValidMove(Board board, Move move) {
		return new Helper(board, move).getFlipped().size() > 0;
	}
}

/**
 * A helper class to test moves against boards. Using a few unchanging global
 * variables alleviates much of the overhead of passing these values around.
 * 
 * @author Andrew Blaine
 */
class Helper {
	private final Point loc;
	private final State state;
	private final Board board;
	private FlipList flipped = null;

	public Helper(Board board, Move move) {
		this(board, move.getPlayerState(), move.getLocation());
	}

	public Helper(Board board, State state, Point location) {
		this.loc = location;
		this.state = state;
		this.board = board;
	}

	/**
	 * Returns a list of points that can be flipped by this object. By Othello
	 * logic, if this list is empty, then the move is considered invalid.
	 * 
	 * @return a list of points and the state that performed the action
	 */
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

	private boolean isWithinBounds() {
		return board.isWithinBounds(loc);
	}
}