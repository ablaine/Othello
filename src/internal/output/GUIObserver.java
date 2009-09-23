package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import api.struct.Move;
import internal.Match;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Settings;
import internal.Tournament.GameState;
import java.awt.Point;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;

/**
 * 
 * @author Andrew Blaine
 */
public class GUIObserver implements IOutput {
	public static final int GRID_SPACING = 2;
	public static final int TILE_SIZE = 80;
	public static final int BOARD_WIDTH  = (TILE_SIZE * Board.SIZE) + (GRID_SPACING * (Board.SIZE + 1));
	public static final int BOARD_HEIGHT = BOARD_WIDTH;

	public static final int WORLD_WIDTH = BOARD_WIDTH;
	public static final int WORLD_HEIGHT = BOARD_HEIGHT;
	
	private final AbstractBodyLayer<VanillaAARectangle> tileLayer;
	private Tile[][] grid;

	public GUIObserver(final AbstractBodyLayer<VanillaAARectangle> tileLayer) {
		this.tileLayer = tileLayer;
	}

	@Override
	public void settings(final Settings settings) {
		//Do nothing
	}

	@Override
	public void update(final GameState curState, final MatchupManager matchupManager) {
		//Do nothing
	}

	@Override
	public void update(final Match.GameState curState, final Matchup matchup, final Board board) {
		switch (curState) {
			case INIT:
				tileLayer.clear();
				grid = new Tile[Board.SIZE][Board.SIZE];
				for (int x = 0; x < Board.SIZE; x++) {
					for (int y = 0; y < Board.SIZE; y++) {
						Tile t = new Tile(x, y, board.getState(x, y));
						grid[x][y] = t;
						tileLayer.add(t);
					}
				}
				break;
		}
	}

	@Override
	public void playerMadeMove(final Player player, final Move move, final FlipList flipList, final Board board) {
		for (Point p : flipList) {
			Point loc = p.getLocation();
			grid[loc.x][loc.y].setState(flipList.getState());
		}
	}

	@Override
	public void playerRanOutOfTime(final Player player) {
		//Do nothing
	}

	@Override
	public void playerGetsToMoveAgain(final Player player) {
		//Do nothing
	}

}
