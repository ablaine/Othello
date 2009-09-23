package internal;

import api.struct.Board;
import internal.output.IOutput;
import internal.timer.ITimer;

/**
 *
 * @author Andrew Blaine
 */
public class MatchFactory {
	private final IOutput output;
	private final ITimer timer;

	public MatchFactory(IOutput output, ITimer timer) {
		this.output = output;
		this.timer = timer;
	}

	public Match createMatch(Matchup matchup) {
		Board board = BoardFactory.createDefaultOthelloBoard();
		return new Match(matchup, board, output, timer);
	}
}
