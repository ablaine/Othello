package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import internal.Match;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Tournament;

/**
 *
 * @author Andrew Blaine
 */
public interface IOutput {
	public void update(final MatchupManager matchupManager, final Tournament.GameState curState);
	public void update(final Matchup matchup, final Match.GameState curState);
	public void update(final Board board, final Match.GameState curState);
	public void update(final FlipList flipList, final Match.GameState curState);
	public void playerRanOutOfTime(final Player player);
	public void playerGetsToMoveAgain(final Player player);
}
