package internal;

import java.util.LinkedList;
import java.util.List;

public class ContestantManager {
	private final List<Matchup> matchups;
	private int matchupIndex = 0; //Always points at the current matchup in play

	public ContestantManager(Player[] players, int totalMatches) {
		matchups = new LinkedList<Matchup>();
		for (int i = 0; i < players.length; i++) {
			Player p1 = players[i];
			for (int j = i+1; j < players.length; j++) {
				Player p2 = players[j];
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
}
