package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import internal.Match;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Tournament;

/**
 * This is a noop implementation serving as the base class for the IOutput
 * decorator. By default, we do not output anything.
 *
 * @author Andrew Blaine
 */
public class DummyOutput implements IOutput {

	@Override
	public void update(final MatchupManager matchupManager, final Tournament.GameState curState) {
		//Do nothing
	}

	@Override
	public void update(final Matchup matchup, final Match.GameState curState) {
		//Do nothing
	}

	@Override
	public void update(final Board board, final Match.GameState curState) {
		//Do nothing
	}

	@Override
	public void update(final FlipList flipList, final Match.GameState curState) {
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
