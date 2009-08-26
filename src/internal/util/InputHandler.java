package internal.util;

import impl.ai.RandomPlayer;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class InputHandler {
	private static final String defaultPlayer = RandomPlayer.class.getCanonicalName();

	private final InputParser parser;
	private String player1;
	private String player2;

	public InputHandler(InputParser parser) {
		this.parser = parser;

		handleQuickExits();
		handlePlayers();
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	private void handlePlayers() {
		/* The vars to fill */
		String p1, p2;
		p1 = p2 = null;

		/* Handle player strings: The #'s show precedence. */
		List<String> players = parser.parsePref("players", "p");
		parser.setDefaultValue(null);
/*1*/	if (players != null && players.size() >= 2) {
			p1 = players.get(0);
			p2 = players.get(1);
/*2*/	} else if (parser.exists("player1", "p1") || parser.exists("player2", "p2")) {
			/* Allows just one player to be specified and defaulting the other. */
			p1 = parser.handle("player1", "p1");
			p2 = parser.handle("player2", "p2");
/*3*/	} else {
			/* Requires the first to arguments to be non-flags and non-null. */
			if (parser.isValue(0) && parser.isValue(1)) {
				p1 = parser.getArg(0);
				p2 = parser.getArg(1);
			}
		}
		String dir = parser.handle("directory", "d"); //Only used in user supplied players
		p1 = prependDirectory(p1, dir);
		p2 = prependDirectory(p2, dir);
		if (p1 == null) {
			p1 = defaultPlayer;
		}
		if (p2 == null) {
			p2 = defaultPlayer;
		}
		player1 = p1;
		player2 = p2;
	}

	private String prependDirectory(String player, String dir) {
		if (player != null && dir != null) {
			player = dir + "." + player;
		}
		return player;
	}

	private void handleQuickExits() {
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
	}
}
