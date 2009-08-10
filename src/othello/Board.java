package othello;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import util.Pair;

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
	
	public void makeMove(Piece move) {
		updateBoard(getFlippedPieces(move));
	}

	public void updateBoard(Pair<List<Point>, State> listToFlip) {
		List<Point> locations = listToFlip.getFirst();
		State state = listToFlip.getSecond();
		for (Point p : locations) {
			board[p.x][p.y] = state;
		}
		setChanged();
		notifyObservers(listToFlip);
	}

	public Pair<List<Point>, State> getFlippedPieces(Piece move) {
		return new MoveLogic(move).getResult();
	}

	private class MoveLogic {
		private Piece move;
		private State playerState;
		private List<Point> toFlip = new LinkedList<Point>();

		public MoveLogic(Piece move) {
			this.move = move;
			playerState = move.getState();
		}

		public Pair<List<Point>, State> getResult() {
			followPathInit(new Point(-1, +1));//Left  Up
			followPathInit(new Point(0, +1));//      Up
			followPathInit(new Point(+1, +1));//Right Up
			followPathInit(new Point(+1, 0));//Right
			followPathInit(new Point(+1, -1));//Right Down
			followPathInit(new Point(0, -1));//      Down
			followPathInit(new Point(-1, -1));//Left  Down
			followPathInit(new Point(-1, 0));//Left
			
			if (!toFlip.isEmpty()) {
				Point loc = move.getLocation();
				if (withinBounds(loc) && getState(loc) == State.EMPTY) {
					toFlip.add(move.getLocation());
				}
			}
			return new Pair<List<Point>, State>(toFlip, playerState);
		}

		private void followPathInit(Point dir) {
			Point loc = move.getLocation();
			followPath(loc.x + dir.x, loc.y + dir.y, dir, new LinkedList<Point>());
		}

		private void followPath(int locX, int locY, Point dir, List<Point> potentialFlips) {
			if (withinBounds(locX, locY)) {
				if (board[locX][locY] == playerState.getOpposite()) {
					potentialFlips.add(new Point(locX, locY));
					followPath(locX + dir.x, locY + dir.y, dir, potentialFlips);
				} else if (board[locX][locY] == playerState) {
					toFlip.addAll(potentialFlips);
				}
			}
		}
	}

	public State getState(Point p) {
		return getState(p.x, p.y);
	}

	public State getState(int x, int y) {
		return board[x][y];
	}
	
	public boolean withinBounds(Point location) {
		return withinBounds(location.x, location.y);
	}

	public boolean withinBounds(int x, int y) {
		if (0 <= x && x < Board.WIDTH) {
			if (0 <= y && y < Board.HEIGHT) {
				return true;
			}
		}
		return false;
	}

	@Override
	public State[][] clone() {
		State[][] result = new State[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < WIDTH; y++) {
				result[x][y] = board[x][y];
			}
		}
		return result;
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
