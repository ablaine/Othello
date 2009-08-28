package internal;

/**
 *
 * @author ablaine
 */
public class Matchup {
	private final Player player1;
	private final Player player2;
	private Player[] winners;
	private int currentMatchIndex = 0;

	public Matchup(Player player1, Player player2, int totalMatches) {
		this.player1 = player1;
		this.player2 = player2;
		winners = new Player[totalMatches];
	}

	public boolean hasMoreMatches() {
		return currentMatchIndex < winners.length;
	}

	public void playedMatch(Player winner) {
		if (player1 == winner) {
			player1.updateWins();
			player2.updateLosses();
		} else if (player2 == winner) {
			player2.updateWins();
			player1.updateLosses();
		} else {
			player1.updateTies();
			player2.updateTies();
		}
		winners[currentMatchIndex++] = winner;
		player1.cleanup();
		player2.cleanup();
		System.gc();
	}

	public Player getFirst() {
		return player1;
	}

	public Player getSecond() {
		return player2;
	}
}
