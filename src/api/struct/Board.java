package api.struct;

import api.State;
import java.awt.Point;
import java.util.Observable;

/**
 *
 * @author ablaine
 */
public final class Board extends Observable implements Cloneable {
	public static final int SIZE = 8;
	private State[][] grid;

	public Board(State[][] board) {
		this.grid = board;
	}

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

	public boolean isWithinBounds(Point location) {
		return isWithinBounds(location.x, location.y);
	}
	
	public boolean isWithinBounds(int x, int y) {
		if (0 <= x && x < SIZE) {
			if (0 <= y && y < SIZE) {
				return true;
			}
		}
		return false;
	}

	public State getState(Point p) {
		return getState(p.x, p.y);
	}

	public State getState(int x, int y) {
		return grid[x][y];
	}

	public State[][] getGrid() {
		return grid;
	}

	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}

	/**
	 * Performs a deep copy of the board.
	 * @return
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  0 1 2 3 4 5 6 7 \n");

		for (int x = 0; x < SIZE; x++) {
			sb.append((x) + " ");
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
