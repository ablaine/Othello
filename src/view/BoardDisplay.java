package view;

import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;
import othello.Board;
import othello.State;
import util.Pair;

/**
 *
 * @author ablaine
 */
public class BoardDisplay implements Observer {
	private Tile[][] board;

	public BoardDisplay(Board actualBoard, AbstractBodyLayer<VanillaAARectangle> tileLayer) {
		board = new Tile[Board.WIDTH][Board.HEIGHT];
		for (int x = 0; x < Board.WIDTH; x++) {
			for (int y = 0; y < Board.HEIGHT; y++) {
				Tile t = new Tile(x, y, actualBoard.getState(x, y));
				board[x][y] = t;
				tileLayer.add(t);
			}
		}
		actualBoard.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Pair<List<Point>, State> listToFlip = (Pair<List<Point>, State>) arg;
		List<Point> lst = listToFlip.getFirst();

		for (Point p : lst) {
			Point loc = p.getLocation();
			board[loc.x][loc.y].setState(listToFlip.getSecond());
		}
	}
}
