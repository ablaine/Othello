package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import api.struct.Move;
import internal.Match;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Settings;
import internal.Tournament;

/**
 * This is a noop implementation serving as the base class for the IOutput
 * decorator. By default, we do not output anything.
 *
 * @author Andrew Blaine
 */
public class DummyOutput implements IOutput {

	@Override
	public void settings(final Settings settings) {
		//Do nothing
	}

	@Override
	public void update(final Tournament.GameState curState, final MatchupManager matchupManager) {
		//Do nothing
	}

	@Override
	public void update(final Match.GameState curState, final Matchup matchup) {
		//Do nothing
	}

	@Override
	public void playerMadeMove(final Player player, final Move move, final FlipList flipList, final Board board) {
		//Do nothing
	}

	@Override
	public void playerRanOutOfTime(final Player player) {
		//Do nothing
	}

	@Override
	public void playerGetsToMoveAgain(final Player player) {
		//Do nothing
	}
}
