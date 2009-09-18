package internal;

import api.struct.Board;
import internal.output.IOutput;
import internal.timer.Timer;
import internal.view.BoardDisplay;
import jig.engine.GameClock.Alarm;

/**
 *
 * @author ablaine
 */
public class MatchFactory {
	private final IOutput output;
	private final Timer timer;
	private final BoardDisplay boardDisplay;

	public MatchFactory(IOutput output, Timer timer, BoardDisplay boardDisplay) {
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
