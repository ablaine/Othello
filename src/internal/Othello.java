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
import java.util.LinkedList;
import java.util.List;
import jig.engine.GameClock.Alarm;

/**
 *
 * @author Andrew Blaine
 */
public class Othello extends StaticScreenGame {

	public static final String SYSTEM = "<SYSTEM> ";

	private enum GameState { INIT, TOURNAMENT_IN_SESSION, TOURNAMENT_OVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);

	public static final String RSC_PATH = "internal/resources/";
	public static final String SPRITE_SHEET = RSC_PATH + "spritesheet.gif";

	private final AbstractBodyLayer<VanillaAARectangle> tileLayer;

	private final View view;//TODO
	private final Tournament tournament;

	public Othello(String player1, String player2) {
		super(View.WORLD_WIDTH, View.WORLD_HEIGHT, false);
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
		gameframe.setTitle("Othello");

		tileLayer = new AbstractBodyLayer.IterativeUpdate<VanillaAARectangle>();
		gameObjectLayers.add(tileLayer);

		BoardDisplay boardDisplay = new BoardDisplay(tileLayer);
		view = new View(boardDisplay);

		long timePerTurn = 3; //Seconds
		Alarm alarm = theClock.setAlarm(UnitConversion.convert(timePerTurn, Unit.SECOND, Unit.NANOSECOND));
		MatchFactory matchFactory = new MatchFactory(alarm, boardDisplay);

		GameClock gameClock = new GameClock(alarm);

		Player[] players = new Player[] {
			new Player(player1, gameClock),
			new Player(player1, gameClock),
			new Player(player2, gameClock),
			new Player(player2, gameClock)
		};

		int totalMatches = 2;
		ContestantManager contestantManager =  new ContestantManager(players, totalMatches);
		tournament = new Tournament(matchFactory, contestantManager);
	}

	@Override
	public void update(final long deltaMs) {
		super.update(deltaMs);
		switch(stateManager.getCurState()) {
			case INIT:
				if (stateManager.isStateChange()) {
					stateManager.setCurState(GameState.TOURNAMENT_IN_SESSION);
				}
				break;
			case TOURNAMENT_IN_SESSION:
				tournament.update(deltaMs);
				break;
			case TOURNAMENT_OVER:
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
