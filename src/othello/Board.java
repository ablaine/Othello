package othello;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class Board {
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

	public void makeMove(PlayerMove playerMove) {
		Point location = playerMove.getPoint();
		State player = playerMove.getPlayer();

		setPiece(location, player);

		followPathInit(new Point(location), new Point(-1, +1), player);//Left  Up
		followPathInit(new Point(location), new Point( 0, +1), player);//      Up
		followPathInit(new Point(location), new Point(+1, +1), player);//Right Up
		followPathInit(new Point(location), new Point(+1,  0), player);//Right
		followPathInit(new Point(location), new Point(+1, -1), player);//Right Down
		followPathInit(new Point(location), new Point( 0, -1), player);//      Down
		followPathInit(new Point(location), new Point(-1, -1), player);//Left  Down
		followPathInit(new Point(location), new Point(-1,  0), player);//Left
	}

	private void followPathInit(Point location, Point direction, State player) {
		location.setLocation(location.x + direction.x, location.y + direction.y);
		followPath(location, direction, player, new LinkedList<Point>());
	}
	
	private void followPath(Point location, Point direction, State player, List<Point> toFlip) {
		if (withinBounds(location)) {
			int x = location.x;
			int y = location.y;
			if (board[x][y] == player.getOpposite()) {
				toFlip.add(new Point(location));
				location.setLocation(location.x + direction.x, location.y + direction.y);
				followPath(location, direction, player, toFlip);
			} else if (board[x][y] == player) {
				setPiece(toFlip, player);
			}
		}
	}
	
	private void setPiece(List<Point> list, State state) {
		for (Point p : list) {
			setPiece(p, state);
		}
	}

	private void setPiece(Point location, State state) {
		board[location.x][location.y] = state;
	}
	
	public boolean withinBounds(Point location) {
		if (0 <= location.x && location.x < Board.WIDTH) {
			if (0 <= location.y && location.y < Board.HEIGHT) {
				return true;
			}
		}
		return false;
	}

	public State[][] getBoard() {
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
