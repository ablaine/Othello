package internal;

import api.GameLogic;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;

import internal.util.StateManager;
import internal.view.BoardDisplay;
import internal.view.View;
import api.struct.Board;
import internal.util.UnitConversion;
import jig.engine.GameClock.Alarm;

/**
 *
 * @author Andrew Blaine
 */
public class Othello extends StaticScreenGame {

	private enum GameState { INIT, PLAYING, GAMEOVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);

	public static final String RSC_PATH = "internal/resources/";
	public static final String SPRITE_SHEET = RSC_PATH + "spritesheet.gif";

	private AbstractBodyLayer<VanillaAARectangle> tileLayer;

	private View view;
	private Match match;

	public Othello(String directory, String player1, String player2) {
		super(View.WORLD_WIDTH, View.WORLD_HEIGHT, false);
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
		gameframe.setTitle("Othello");

		tileLayer = new AbstractBodyLayer.IterativeUpdate<VanillaAARectangle>();

		gameObjectLayers.add(tileLayer);

		Board board = BoardFactory.createDefaultOthelloBoard();
		BoardDisplay boardDisplay = new BoardDisplay(board, tileLayer);
		view = new View(boardDisplay);
		int seconds = 3;

		Alarm alarm = theClock.setAlarm(UnitConversion.secondToNanosecond(seconds));
		Player dark = new Player(directory, player1);
		Player light = new Player(directory, player2);
//		Player light = new Player("SlowRandomPlayer");
//		Player light = new Player("RandomClojurePlayer");
//		Player light = new Player("MinimaxClojurePlayer");

		GameLogic._init(alarm);
		match = new Match(alarm, board, dark, light);
	}

	public Othello() {
		this("implementations.ai", "RandomPlayer", "RandomPlayer");
	}

	@Override
	public void update(final long deltaMs) {
		super.update(deltaMs);
		switch(stateManager.getCurState()) {
			case INIT:
				match.update(deltaMs);
				break;
			case PLAYING:
				break;
			case GAMEOVER:
				break;
		}
	}

	@Override
	public void render(final RenderingContext rc) {
		super.render(rc);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String dir = args[0];
		String p1  = args[1];
		String p2  = args[2];
		Othello othello = new Othello(dir, p1, p2);
		othello.run();
	}
}
