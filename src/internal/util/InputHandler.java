package internal.util;

import impl.ai.RandomPlayer;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class InputHandler {
	public static final String[] PLAYER_FLAGS = { "-p", "--player", "--players" };
	public static final String[] DIRECTORY_FLAGS = { "-d", "-dir", "-directory" };
	public static final String[] NUMBER_OF_GAMES_FLAGS = { "-g", "--games" };
	public static final String[] TIME_LIMIT_FLAGS = { "-t", "--timeLimit" };
	public static final String DEFAULT_PLAYER = RandomPlayer.class.getCanonicalName();

	private final InputParser parser;

	public InputHandler(InputParser parser) {
		this.parser = parser;
		handleQuickExits();
	}

	public List<String> getPlayers() {
		List<String> players = new LinkedList<String>();
		if (parser.containsKey((Object[])PLAYER_FLAGS)) {
			players = parser.get((Object[])PLAYER_FLAGS);
		}
		List<String> result = prependDirectory(players);
		while (result.size() < 2) {
			result.add(DEFAULT_PLAYER);
		}
		return result;
	}

	public int getGamesPerMatchup() {
		String error = "<ERROR> --games\n\t";
		int result = 1;
		if (parser.containsKey((Object[])NUMBER_OF_GAMES_FLAGS)) {
			List<String> games = parser.get((Object[])NUMBER_OF_GAMES_FLAGS);
			if (games.size() != 1) {
				printUsageAndQuit(error + "Please pass only single number.");
			}
			try {
				result = Integer.parseInt(games.get(0));
			} catch (NumberFormatException e) {
				printUsageAndQuit(error + "Please use digits.");
			}
			if (result < 0) {
				printUsageAndQuit(error + "Please use non-negative number.");
			}
		}

		return result;
	}

	private List<String> prependDirectory(List<String> players) {
		String directory = getDirectory();
		if (directory == null) {
			return players;
		}
		List<String> result = new LinkedList<String>();
		for (String player : players) {
			result.add(directory + "." + player);
		}
		return result;
	}

	public String getDirectory() {
		String error = "<ERROR> --directory\n\t";
		String result = null;
		if (parser.containsKey((Object[])DIRECTORY_FLAGS)) {
			List<String> directory = parser.get((Object[])DIRECTORY_FLAGS);
			if (directory.size() != 1) {
				printUsageAndQuit(error + "Please pass only a single directory.");
			}
			result = directory.get(0);
		}
		return result;
	}

	public long getTimeLimitPerTurn() {
		String error = "<ERROR> --timeLimit\n\t";
		long result = 0;
		if (parser.containsKey((Object[])TIME_LIMIT_FLAGS)) {
			List<String> timeLimit = parser.get((Object[])TIME_LIMIT_FLAGS);
			if (timeLimit.size() != 1) {
				printUsageAndQuit(error + "Please pass only a single value.");
			}
			try {
				result = Long.parseLong(timeLimit.get(0));
			} catch (NumberFormatException e) {//TODO: Parse for more than nanoseconds.
				printUsageAndQuit(error + "Please use digits only, specified in nanoseconds.");
			}
			if (result < 0) {
				printUsageAndQuit(error + "Please use non-negative number.");
			}
		}
		return result;
	}

	public String usage() {
		return "";
	}

	private void printUsageAndQuit(String errorMessage) {
		System.err.println(errorMessage);
		printUsageAndQuit();
	}

	private void printUsageAndQuit() {
		System.out.println(usage());
		System.exit(0);
	}

	private void handleQuickExits() {
		if (parser.containsKey("-h", "--help")) {
			printUsageAndQuit();
		}
		if (parser.containsKey("-v", "--version")) {
			//Print version
			System.out.println("Othello version 2.5");
			System.out.println("Written by Andrew Blaine [http://ablaine.com]");
			System.exit(0);
		}
	}
}
