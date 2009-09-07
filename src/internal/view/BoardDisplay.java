package internal.view;

import java.awt.Point;
import java.util.Observable;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;
import api.struct.Board;
import api.struct.FlipList;
import internal.util.BoardObserver;

/**
 *
 * @author ablaine
 */
public class BoardDisplay implements BoardObserver {
	private final AbstractBodyLayer<VanillaAARectangle> tileLayer;
	private Tile[][] board;

	public BoardDisplay(AbstractBodyLayer<VanillaAARectangle> tileLayer) {
		this.tileLayer = tileLayer;
	}

	public void provideBoard(Board actualBoard) {
		tileLayer.clear();
		board = new Tile[Board.SIZE][Board.SIZE];
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Tile t = new Tile(x, y, actualBoard.getState(x, y));
				board[x][y] = t;
				tileLayer.add(t);
			}
		}
		actualBoard.registerObserver(this);
	}

	public void update(Observable o, Object arg) {
		FlipList listToFlip = (FlipList) arg;

		for (Point p : listToFlip) {
			Point loc = p.getLocation();
			board[loc.x][loc.y].setState(listToFlip.getState());
		}
	}

	@Override
	public void updateBoard(FlipList flipList) {
		for (Point p : flipList) {
			Point loc = p.getLocation();
			board[loc.x][loc.y].setState(flipList.getState());
		}
	}
}
