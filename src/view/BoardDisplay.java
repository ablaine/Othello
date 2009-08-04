package view;

import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;
import othello.State;

/**
 *
 * @author ablaine
 */
public class BoardDisplay {
	private Tile[][] board;

	public BoardDisplay(AbstractBodyLayer<VanillaAARectangle> tileLayer) {
		board = new Tile[View.GRID_UNITS_HORI][View.GRID_UNITS_VERT];
		for (int x = 0; x < View.GRID_UNITS_HORI; x++) {
			for (int y = 0; y < View.GRID_UNITS_VERT; y++) {
				Tile t = new Tile(x, y, State.EMPTY);
				board[x][y] = t;
				tileLayer.add(t);
			}
		}
	}
}
