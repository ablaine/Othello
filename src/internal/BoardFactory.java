package internal;

import api.State;
import api.struct.Board;

/**
 *
 * @author ablaine
 */
public class BoardFactory {
	
	public static Board createDefaultOthelloBoard() {
		State[][] grid = new State[Board.SIZE][Board.SIZE];
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				grid[x][y] = State.EMPTY;
			}
		}
		grid[3][3] = State.LIGHT;
		grid[4][4] = State.LIGHT;
		grid[3][4] = State.DARK;
		grid[4][3] = State.DARK;
		
		return new Board(grid);
	}
}
