package internal.util;

import api.util.UnitConversion;
import api.util.UnitConversion.Unit;
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
			List<String> values = parser.get((Object[])TIME_LIMIT_FLAGS);
			if (values.size() != 1) {
				printUsageAndQuit(error + "Please pass only a single value.");
			}
			result = parseValueInNanoseconds(values.get(0), error);
			if (result < 0) {
				printUsageAndQuit(error + "Please use non-negative number.");
			}
		}
		return result;
	}

	private long parseValueInNanoseconds(String timeAndUnit, String error) {
		long result = 0;
		String sUnit = timeAndUnit.substring(timeAndUnit.length() - 2);//Get last two char
		String sTime = "";
		Unit fromUnit = null;
		Unit toUnit = Unit.NANOSECOND;
		if (sUnit.equals("ns")) {
			sTime = timeAndUnit.substring(0, timeAndUnit.length() - 2);//rm last two char
			fromUnit = Unit.NANOSECOND;
		} else if (sUnit.equals("mu")) {
			sTime = timeAndUnit.substring(0, timeAndUnit.length() - 2);//rm last two char
			fromUnit = Unit.MICROSECOND;
		} else if (sUnit.equals("ms")) {
			sTime = timeAndUnit.substring(0, timeAndUnit.length() - 2);//rm last two char
			fromUnit = Unit.MILLISECOND;
		} else if (sUnit.substring(sUnit.length() - 1).equals("s")) {
			sTime = timeAndUnit.substring(0, timeAndUnit.length() - 1);//rm last char
			fromUnit = Unit.SECOND;
		} else {// Specify/use correct units
			printUsageAndQuit(error + "Please specify what unit. For example, -t 5ms\n" +
					"\tValid units are: seconds(s), milliseconds(ms), microseconds(mu) and nanoseconds(ns).");
		}
		try {
			result = Long.parseLong(sTime);
		} catch (NumberFormatException e) {
			printUsageAndQuit(error + "Please format correctly. For example, -t 10mu");
		}
		return UnitConversion.convert(result, fromUnit, toUnit);
	}

	public String usage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<< Usage >>");
		sb.append("java -jar Othello.jar [--help | -h] | [--version | -v]");
		sb.append("\n\t| [--players <path.to.player>*           | -p <path.to.player>* ]");
		sb.append("\n\t| [--directory <path.to.implementations> | -d <path.to.implementations> ]");
		sb.append("\n\t| [--timeLimit <timeAndUnit>             | -t <timeAndUnit> ]");
		sb.append("\n\t| [--games <gamesPerMatchup>             | -g <gamesPerMatchup> ]");
		sb.append("\n\n\tComments are signified by '//' (without the quotes) and hides all following arguments.");
		return sb.toString();
	}

	private void printUsageAndQuit(String errorMessage) {
		System.err.println(errorMessage);
		printUsageAndQuit();
	}

	private void printUsageAndQuit() {
		System.err.println(usage());
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
