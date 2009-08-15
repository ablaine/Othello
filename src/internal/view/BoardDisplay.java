package internal.view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;
import api.struct.Board;
import api.struct.FlipList;

/**
 *
 * @author ablaine
 */
public class BoardDisplay implements Observer {
	private Tile[][] board;

	public BoardDisplay(Board actualBoard, AbstractBodyLayer<VanillaAARectangle> tileLayer) {
		board = new Tile[Board.SIZE][Board.SIZE];
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Tile t = new Tile(x, y, actualBoard.getState(x, y));
				board[x][y] = t;
				tileLayer.add(t);
			}
		}
		actualBoard.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		FlipList listToFlip = (FlipList) arg;

		for (Point p : listToFlip) {
			Point loc = p.getLocation();
			board[loc.x][loc.y].setState(listToFlip.getState());
		}
	}
}
