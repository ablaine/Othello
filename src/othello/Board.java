package othello;

import java.awt.Point;
import java.util.Observable;

/**
 *
 * @author ablaine
 */
public class Board extends Observable implements Cloneable {
	public final static int WIDTH = 8;
	public final static int HEIGHT = 8;
	private State[][] board;

	public Board() {
		board = new State[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				board[x][y] = State.EMPTY;
			}
		}
		board[3][3] = State.LIGHT;
		board[4][4] = State.LIGHT;
		board[3][4] = State.DARK;
		board[4][3] = State.DARK;
	}

	public Board(State[][] board) {
		if (board.length == WIDTH) {
			for (State[] row : board) {
				if (row.length == HEIGHT) {
					this.board = board;
					return;
				}
			}
		}
		System.err.println("This board is not Othello regulation size.");
		System.exit(0);
	}
	
	public void updateBoard(Move move) {
		updateBoard(move.getFlipped());
	}

	public void updateBoard(FlipList list) {
		for (Point p : list) {
			board[p.x][p.y] = list.getState();
		}
		setChanged();
		notifyObservers(list);
	}

	/*
	 * Avoids getting a move that is liable to have the board changed, making
	 * that move invalid.
	 */
	public Move getMove(Point p, State player) {
		return new Move(p, player, clone());
	}
	
	public Move getMove(int x, int y, State player) {
		return getMove(new Point(x, y), player);
	}
	
	public MoveList getValidMoves(State player) {
		MoveList result = new MoveList();
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Move m = getMove(x, y, player);
				if (m.isValid()) {
					result.add(m);
				}
			}
		}
		return result;
	}

	public boolean isWithinBounds(Point location) {
		return isWithinBounds(location.x, location.y);
	}

	public boolean isWithinBounds(int x, int y) {
		if (0 <= x && x < Board.WIDTH) {
			if (0 <= y && y < Board.HEIGHT) {
				return true;
			}
		}
		return false;
	}

	public State getState(Point p) {
		return getState(p.x, p.y);
	}

	public State getState(int x, int y) {
		return board[x][y];
	}

	public State[][] getBoard() {
		return board;
	}

	@Override
	public Board clone() {
		State[][] result = new State[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < WIDTH; y++) {
				result[x][y] = board[x][y];
			}
		}
		return new Board(result);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  0 1 2 3 4 5 6 7 \n");

		for (int x = 0; x < WIDTH; x++) {
			sb.append((x) + " ");
			for (State s : board[x]) {
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
