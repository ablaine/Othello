package api.struct;

import api.State;
import internal.util.Observable;
import java.awt.Point;

/**
 * Represents a simple Othello game board.
 * 
 * @author Andrew Blaine
 */
public final class Board extends Observable implements Cloneable {
	public static final int SIZE = 8;
	private State[][] grid;

	/**
	 * Constructs a Board.
	 * 
	 * @param board The initial underlying grid for this board.
	 */
	public Board(State[][] board) {
		this.grid = board;
	}

	/**
	 * Returns the number of pieces on this board owned by the player state.
	 * 
	 * @param playerState The state you want to count.
	 * @return the number elements with the passed in state
	 */
	public int getScore(State playerState) {
		int result = 0;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (grid[x][y] == playerState) {
					result ++;
				}
			}
		}
		return result;
	}

	/**
	 * Determines whether the point is within the bounds of the game board.
	 * 
	 * @param location The point in question.
	 * @return <code>true</code> if this point is within the game board.
	 */
	public boolean isWithinBounds(Point location) {
		return isWithinBounds(location.x, location.y);
	}

	/**
	 * Determines whether the point is within the bounds of the game board.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if this point is within the game board.
	 */
	public boolean isWithinBounds(int x, int y) {
		return 0 <= x && x < SIZE &&
			   0 <= y && y < SIZE;
	}

	/**
	 * Returns the state at the specified point in the board.
	 * 
	 * @param p The point you are interested in.
	 * @return the state at that point
	 */
	public State getState(Point p) {
		return getState(p.x, p.y);
	}

	/**
	 * Returns the state at the specified point in the board.
	 * 
	 * @param x
	 * @param y
	 * @return the state at that point
	 */
	public State getState(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Returns the underlying grid of <code>State</code>s representing this
	 * board.<br/>
	 * <br/>
	 * Note is generally not recommended, or necessary to manipulate this.
	 * <code>GameLogic</code> contains numerous methods for manipulating the
	 * board.
	 * 
	 * @return the reference to the underlying grid
	 */
	public State[][] getGrid() {
		return grid;
	}

	/**
	 * Provides a quick way to change multiple points to a specific state,
	 * regardless of the game rules.
	 * 
	 * @param flipList the list of points to change
	 */
	public void modify(FlipList flipList) {
		for (Point p : flipList) {
			grid[p.x][p.y] = flipList.getState();
		}
		notifyBoardObservers(flipList);
	}

	/**
	 * Performs a deep (field for field) copy of the board.
	 * 
	 * @return a clone of this board instance
	 */
	@Override
	public Board clone() {
		State[][] result = new State[SIZE][SIZE];
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				result[x][y] = grid[x][y];
			}
		}
		return new Board(result);
	}

	/**
	 * Returns an ascii representation of the board.<br/>
	 * <br/>
	 * The numbers 0 through 7 appear on the top and left edges. Dark is
	 * represented by <code>X</code> and light is represented by <code>O</code>.
	 * Empty tiles are <code>.</code>
	 *
	 * @return an ASCII representation of the board
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  " + "  0 1 2 3 4 5 6 7 \n");

		for (int x = 0; x < SIZE; x++) {
			sb.append("  " + (x) + " ");
			for (State s : grid[x]) {
				char c;
				switch (s) {
					case LIGHT:
						c = 'O';
						break;
					case DARK:
						c = 'X';
						break;
					default:
						c = '.';
				}
				sb.append(c + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
