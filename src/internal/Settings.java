package internal;

import api.util.UnitConversion;
import api.util.UnitConversion.Unit;
import java.util.List;

/**
 *
 * @author Andrew Blaine
 */
public class Settings {
	private final List<String> playerClassNames; //Required
	private boolean tournamentMode;
	private int gamesPerMatchup;
	private long timeLimitInNanoseconds;

	//Optional... denoted with a 'null' value
	private String logFile;

	private Settings(List<String> playerClassNames) {
		this.playerClassNames = playerClassNames;
		//Set everything to the defaults
		tournamentMode = false;
		gamesPerMatchup = 0; //Infinite
		timeLimitInNanoseconds = 0; //Infinite
		logFile = null; //None
	}

	public static Settings newInstance(List<String> playerClassNames) {
		return new Settings(playerClassNames);
	}

/* SETTERS
 ******************************************************************************/
	public void setTournamentMode(boolean tournamentMode) {
		this.tournamentMode = tournamentMode;
	}

	public void setGamesPerMatchup(int gamesPerMatchup) {
		this.gamesPerMatchup = gamesPerMatchup;
	}

	public void setTimeLimitInNanoseconds(long timeLimitInNanoseconds) {
		this.timeLimitInNanoseconds = timeLimitInNanoseconds;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public List<String> getPlayerClassNames() {
		return playerClassNames;
	}

/* GETTERS
 ******************************************************************************/
	public boolean isTournamentMode() {
		return tournamentMode;
	}

	public boolean isInfiniteGamesPerMatchup() {
		return gamesPerMatchup == 0;
	}

	public boolean isTimeLimited() {
		return timeLimitInNanoseconds > 0;
	}

	public boolean hasLogFile() {
		return logFile != null;
	}

	public int getGamesPerMatchup() {
		return gamesPerMatchup;
	}

	public long getTimeLimit(Unit unit) {
		return UnitConversion.convert(timeLimitInNanoseconds, Unit.NANOSECOND, unit);
	}

	public String getLogFileName() {
		return logFile;
	}
}
