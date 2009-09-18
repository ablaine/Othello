package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import internal.Match;
import internal.MatchStatus;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Tournament;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Andrew Blaine
 */
public class ConsoleOutput extends OutputDecorator {
	private static String OPEN = "<< ";
	private static String CLOSE = " >>";

	private final boolean printBoard;
	
	public ConsoleOutput(IOutput output, boolean printBoard) {
		super(output);
		this.printBoard = printBoard;
	}

	@Override
	public void update(final MatchupManager matchupManager, final Tournament.GameState curState) {
		output.update(matchupManager, curState);
		switch(curState) {
			case INIT:
				sayln("The matchup(s)");
				System.out.print(matchupManager.toString());
				System.out.println();// One blank line
				break;
			case TOURNAMENT_OVER:
				sayln("Tournament is over!");
				List<Player> players = new ArrayList<Player>(matchupManager.getAllPlayers());
				Collections.sort(players, new Comparator<Player>() {
					@Override
					public int compare(Player p1, Player p2) {
						return p2.getScore() - p1.getScore();
					}
				});
				sayln("Results of the tournament as -> (score) wins-ties-losses :: class.name#nickname");
				for (Player p : players) {
					System.out.println("\t(" + p.getScore() + ") " +
							p.getWins() + "-" + p.getTies() + "-" + p.getLosses() +
							" :: " + p.getFullName());
				}
				//TODO: Congratulate the tournament champion or declare tie.
				break;
		}
	}

	@Override
	public void update(final Matchup matchup, final Match.GameState curState) {
		output.update(matchup, curState);
		switch(curState) {
			case INIT:
				sayln(matchup.getFirst().getNicknameAndState() + " vs " +
					  matchup.getSecond().getNicknameAndState());
				break;
			case GAME_OVER:
				System.out.print(OPEN + "Game is over... ");
				MatchStatus matchStatus = matchup.getPreviousMatchState();
				if (matchStatus.getState() == MatchStatus.State.TIED) {
					System.out.println("it was a tie!" + CLOSE);
				} else if (matchStatus.getState() == MatchStatus.State.WINNER) {
					System.out.println(matchStatus.getWinner().getFullName() + " is the winner!" + CLOSE);
				}
				System.out.println();
				break;
		}
	}

	@Override
	public void update(final Board board, final Match.GameState curState) {
		if (printBoard) {
			System.out.println(board.toString());
		}
	}

	@Override
	public void update(final FlipList flipList, final Match.GameState curState) {
		//Do nothing
	}

	@Override
	public void playerRanOutOfTime(final Player player) {
		output.playerRanOutOfTime(player);
		sayln(player.getNicknameAndState() + " has just run out of time.");
	}

	@Override
	public void playerGetsToMoveAgain(final Player player) {
		output.playerGetsToMoveAgain(player);
		sayln(player.getNicknameAndState() + " gets to move again!");
	}

	private void sayln(final String msg) {
		System.out.println(OPEN + msg + CLOSE);
	}
}
