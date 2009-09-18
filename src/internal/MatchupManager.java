package internal;

import java.util.LinkedList;
import java.util.List;

public class MatchupManager {
	private final List<Player> players;
	private final List<Matchup> matchups;
	private int matchupIndex = 0; //Always points at the current matchup in play

	public MatchupManager(final List<Player> players, final int totalMatches) {
		this.players = players;
		matchups = new LinkedList<Matchup>();
		for (int i = 0; i < players.size(); i++) {
			Player p1 = players.get(i);
			for (int j = i + 1; j < players.size(); j++) {
				Player p2 = players.get(j);
				matchups.add(new Matchup(p1, p2, totalMatches));
			}
		}
	}

	public boolean hasMoreMatchups() {
		return getNextMatchup() != null;
	}

	public Matchup getNextMatchup() {
		Matchup matchup = null;
		while (matchup == null) {
			if (matchupIndex < matchups.size()) {// Within range
				if (matchups.get(matchupIndex).hasMoreMatches()) {//Try current
					matchup = matchups.get(matchupIndex);//Good to go
				} else {
					matchupIndex++;//Try next matchup
				}
			} else {
				return null;//No more matchups
			}
		}
		return matchup;
	}

	public List<Matchup> getAllMatchups() {
		return matchups;
	}

	public List<Player> getAllPlayers() {
		return players;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matchups.size(); i++) {
			sb.append("\t#" + (i + 1) + " " + matchups.get(i).toString() + "\n");
		}
		return sb.toString();
	}
}
