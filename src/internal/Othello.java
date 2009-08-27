package internal;

import api.GameClock;
import internal.util.InputParser;
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
import impl.ai.RandomPlayer;
import internal.util.InputHandler;
import api.util.UnitConversion;
import api.util.UnitConversion.Unit;
import java.util.List;
import jig.engine.GameClock.Alarm;

/**
 *
 * @author Andrew Blaine
 */
public class Othello extends StaticScreenGame {

	public static final String SYSTEM = "<SYSTEM> ";

	private enum GameState { INIT, PLAYING, GAMEOVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);

	public static final String RSC_PATH = "internal/resources/";
	public static final String SPRITE_SHEET = RSC_PATH + "spritesheet.gif";

	private final AbstractBodyLayer<VanillaAARectangle> tileLayer;

	private final View view;
	private final Match match;

	public Othello(String player1, String player2) {
		super(View.WORLD_WIDTH, View.WORLD_HEIGHT, false);
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
		gameframe.setTitle("Othello");

		tileLayer = new AbstractBodyLayer.IterativeUpdate<VanillaAARectangle>();

		gameObjectLayers.add(tileLayer);

		Board board = BoardFactory.createDefaultOthelloBoard();
		BoardDisplay boardDisplay = new BoardDisplay(board, tileLayer);
		view = new View(boardDisplay);

		//Both players and the match have the same instance of "alarm", but the
		//players gets one wrapped in GameClock, which provides a different API.
		int timePerTurn = 3; //Seconds
		Alarm alarm = theClock.setAlarm(UnitConversion.convert(timePerTurn, Unit.SECOND, Unit.NANOSECOND));
		GameClock gameClock = new GameClock(alarm);
		Player dark = new Player(player1, gameClock);
		Player light = new Player(player2, gameClock);

		match = new Match(alarm, board, dark, light);
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
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		InputHandler handler = new InputHandler(new InputParser(args));
		String p1 = handler.getPlayer1();
		String p2 = handler.getPlayer2();
		
		Othello othello = new Othello(p1, p2);
		othello.run();
	}
}
