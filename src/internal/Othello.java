package internal;

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
import internal.util.UnitConversion;
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

	private static final String defaultPlayer = RandomPlayer.class.getCanonicalName().toString();

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
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		InputParser parser = new InputParser(args);
		if (parser.exists("help", "h")) {
			//Print usage..
			System.exit(0);
		}
		if (parser.exists("version", "v")) {
			//Print version
			System.out.println("Othello version 2.2");
			System.out.println("Written by Andrew Blaine [http://ablaine.com]");
			System.exit(0);
		}

		/* The vars to fill */
		String dir, p1, p2;
		dir = "";
		p1 = p2 = defaultPlayer;
		
		/* Handle player strings: The #'s show precedence. */
		List<String> players = parser.parsePref("players", "p");	
/*1*/	if (players != null && players.size() >= 2) {
			p1 = players.get(0);
			p2 = players.get(1);
/*2*/	} else if (parser.exists("player1", "p1") || parser.exists("player2", "p2")) {
			/* Allows just one player to be specified and defaulting the other. */
			parser.setDefaultValue(defaultPlayer);
			p1 = parser.handle("player1", "p1");
			p2 = parser.handle("player2", "p2");
			parser.setDefaultValue("");
/*3*/	} else if (args.length >= 2 && !parser.isFlag(args[0]) && !parser.isFlag(args[1])) {
			/* Requires the first to arguments to be non-flags. */
			p1 = args[0];
			p2 = args[1];
/*4*/	} else {
			p1 = defaultPlayer;
			p2 = defaultPlayer;
		}

		/* Handle various flags */
		dir = parser.handle("directory", "d");

		/* TODO, need to handle adding the dir on here... 
		 * IE: add user supplied directory for user supplied players, but
		 * do not touch the defaultPlayer..
		 */
		
		Othello othello = new Othello(dir, p1, p2);
		othello.run();
	}
}
