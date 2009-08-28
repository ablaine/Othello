package internal;

import api.struct.Board;
import internal.view.BoardDisplay;
import jig.engine.GameClock.Alarm;

/**
 *
 * @author ablaine
 */
public class MatchFactory {
	private final Alarm alarm;
	private final BoardDisplay boardDisplay;

	public MatchFactory(Alarm alarm, BoardDisplay boardDisplay) {
		this.alarm = alarm;
		this.boardDisplay = boardDisplay;
	}

	public Match createMatch(Matchup matchup) {
		Board board = BoardFactory.createDefaultOthelloBoard();
		boardDisplay.provideBoard(board);
		return new Match(matchup, board, alarm);
	}
}
