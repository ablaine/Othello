package internal;

import api.struct.Board;
import internal.output.IOutput;
import internal.timer.ITimer;
import internal.view.BoardDisplay;

/**
 *
 * @author ablaine
 */
public class MatchFactory {
	private final IOutput output;
	private final ITimer timer;
	private final BoardDisplay boardDisplay;

	public MatchFactory(IOutput output, ITimer timer, BoardDisplay boardDisplay) {
		this.output = output;
		this.timer = timer;
		this.boardDisplay = boardDisplay;
	}

	public Match createMatch(Matchup matchup) {
		Board board = BoardFactory.createDefaultOthelloBoard();
		boardDisplay.provideBoard(board);
		return new Match(matchup, board, output, timer);
	}
}
