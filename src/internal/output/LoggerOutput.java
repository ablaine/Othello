package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import api.struct.Move;
import api.util.UnitConversion.Unit;
import internal.Match;
import internal.MatchStatus;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Settings;
import internal.Tournament;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

/**
 *
 * @author Andrew Blaine
 */
public class LoggerOutput implements IOutput {
	private final static String OTHELLO = "othello";
	private final static String SETTINGS = "settings";
	private final static String PLAYERS = "players";
	private final static String TOURNAMENT = "tournament";
	private final static String MATCHES = "matches";
	private final static String MATCH = "match";
	private final static String RESULTS = "results";
	private final static String MADE_MOVE = "madeMove";
	private final static String LOCATION = "location";
	
	private XMLLogger log;

	public LoggerOutput(String fileName) {
		try {
			log = new XMLLogger(fileName);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (XMLStreamException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void init() {
		try {
			log.openTag(OTHELLO);
		} catch (XMLStreamException ex) {
			Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void settings(final Settings s) {
		String tourn	= s.isTournamentMode()			? "True"				: "False";
		String games	= s.isInfiniteGamesPerMatchup() ? "Infinite"			: String.valueOf(s.getGamesPerMatchup());
		String time		= s.isTimeLimited()				? "Infinite"			: s.getTimeLimit(Unit.NANOSECOND) + "ns";
		String logFile	= s.hasLogFile()				? s.getLogFileName()	: "None";
		try {
			log.openTag(SETTINGS);
			log.createNode("tournamentMode", tourn);
			log.createNode("gamesPerMatch", games);
			log.createNode("timeLimitPerTurn", time);
			log.createNode("logFileName", logFile);
			log.closeTag(SETTINGS);
		} catch (XMLStreamException ex) {
			Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void update(final Tournament.GameState curState, final MatchupManager matchupManager) {
		switch (curState) {
			case TOURNAMENT_INIT:
				try {
					log.openTag(TOURNAMENT);
					log.openTag(PLAYERS);
					for (Player p : matchupManager.getAllPlayers()) {
						log.createNode("player", p.toString());
					}
					log.closeTag(PLAYERS);
					log.openTag(MATCHES);
				} catch (XMLStreamException ex) {
					Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
				}
				break;
			case TOURNAMENT_OVER:
				try {
					log.closeTag(MATCHES);
					log.openTag(RESULTS);
					for (Player p : matchupManager.getAllPlayersSortedByScore()) {
						log.createNode(p.getFullName(), p.getScore());
					}
					log.closeTag(RESULTS);
					log.closeTag(TOURNAMENT);
				} catch (XMLStreamException ex) {
					Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
				}
				break;
		}
	}

	@Override
	public void update(final Match.GameState curState, final Matchup matchup, final Board board) {
		switch (curState) {
			case INIT:
				try {
					List<Attribute> attributes = new ArrayList<Attribute>();
					attributes.add(log.getAttribute("dark", matchup.getFirst().getFullName()));
					attributes.add(log.getAttribute("light", matchup.getSecond().getFullName()));
					log.openTag(MATCH, attributes.iterator());
				} catch (XMLStreamException ex) {
					Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
				}
				break;
			case PLAYING:
				break;
			case GAME_OVER:
				try {
					log.openTag(RESULTS);
					for (MatchStatus ms : matchup.getMatchupResults()) {
						switch (ms.getState()) {
							case WINNER:
								log.createNode("winner", ms.getWinner().getFullName());
								break;
							case TIED:
								log.createNode("tie");
								break;
							default:
								log.createNode("error", "unknownState");
								break;
						}
					}

					log.closeTag(RESULTS);
					log.closeTag(MATCH);
				} catch (XMLStreamException ex) {
					Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
				}
				break;
		}
	}

	@Override
	public void playerMadeMove(final Player player, final Move move, final FlipList flipList, final Board board) {
		try {
			List<Attribute> attributes = new ArrayList<Attribute>();
			attributes.add(log.getAttribute("player", player.getFullName()));
			log.openTag(MADE_MOVE, attributes.iterator());
			
			log.openTag(LOCATION);
			log.createNode("x", String.valueOf(move.getLocation().getX()));
			log.createNode("y", String.valueOf(move.getLocation().getY()));
			log.closeTag(LOCATION);
			
			log.createNode("tilesFlipped", flipList.size());
			
			log.closeTag(MADE_MOVE);
		} catch (XMLStreamException ex) {
			Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void playerRanOutOfTime(final Player player) {
		try {
			log.createNode("ranOutOfTime", player.getFullName());
		} catch (XMLStreamException ex) {
			Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void playerGetsToMoveAgain(final Player player) {
		try {
			log.createNode("getsToMoveAgain", player.getFullName());
		} catch (XMLStreamException ex) {
			Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void cleanup() {
		try {
			log.closeTag(OTHELLO);
			log.close();
		} catch (XMLStreamException ex) {
			Logger.getLogger(LoggerOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
