package internal;

import api.GameClock;
import internal.util.InputParser;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;

import internal.util.StateManager;
import internal.view.BoardDisplay;
import internal.view.View;
import internal.util.InputHandler;
import internal.output.*;
import internal.timer.*;
import java.util.ArrayList;
import java.util.List;

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

	public Othello(boolean tournamentMode, List<String> playerClassNames, int gamesPerMatchup, long timeLimitInNanoseconds) {
		super(View.WORLD_WIDTH, View.WORLD_HEIGHT, false);
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
		gameframe.setTitle("Othello");

		tileLayer = new AbstractBodyLayer.IterativeUpdate<VanillaAARectangle>();
		gameObjectLayers.add(tileLayer);

		BoardDisplay boardDisplay = new BoardDisplay(tileLayer);
		view = new View(boardDisplay);

		ITimer timer = null;
		if (timeLimitInNanoseconds == 0) {
			timer = new InfiniteTimer(theClock.setAlarm(Long.MAX_VALUE));
		} else {
			timer = new TimedTimer(theClock.setAlarm(timeLimitInNanoseconds));
		}

		IOutput output = new DummyOutput();
		boolean printBoard = false;
		output = new ConsoleDecorator(output, printBoard);
//		output = new GUIOutput(output);
//		output = new LoggerOutput(output, "out.log");

		MatchFactory matchFactory = new MatchFactory(output, timer, boardDisplay);

		//Wrap the timer in a "GameClock" before we give its access to the players
		GameClock gameClock = new GameClock(timer);
		
		List<Player> players = new ArrayList<Player>(playerClassNames.size());
		for (String s : playerClassNames) {
			players.add(new Player(s, gameClock));
		}

		MatchupManager matchupManager = new MatchupManager(players, gamesPerMatchup);

		tournament = new Tournament(output, matchFactory, matchupManager);
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
		InputHandler handler = new InputHandler(InputParser.createKeyValueStore(args));
		
		boolean tourn = handler.isTournamentMode();
		List<String> players = handler.getPlayers();
		int gamesPerMatchup = handler.getGamesPerMatchup();
		long timeLimitInNanoseconds = handler.getTimeLimitPerTurn();
		
		//Printout of settings
		System.out.println("\n============SETTINGS==============");
		System.out.println("Tournament       : " + (tourn						? "True"	 : "False"));
		System.out.println("GamesPerMatchup  : " + (gamesPerMatchup <= 0		? "Infinite" : gamesPerMatchup));
		System.out.println("TimeLimitPerTurn : " + (timeLimitInNanoseconds <= 0	? "None"	 : timeLimitInNanoseconds + "ns"));
//		System.out.println("Logging          : " + (outputFile == null			? "None"	 : outputFile));
//		System.out.println("RandomizedStates : " + (randomizedStates			? "True"	 : "False"));
//		System.out.println("Transparencies   : " + (trans						? "True"	 : "False"));
		System.out.println(  "==================================");

		Othello othello = new Othello(tourn, players, gamesPerMatchup, timeLimitInNanoseconds);
		othello.run();
	}
}
