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
 *
 * @author Andrew Blaine
 */
public interface IOutput {

	/**
	 * Should be the first hook called. This method is intended to allow
	 * implementations to run their initilization routines and general
	 * preparation work.
	 */
	void init();

	/**
	 * Reports prior to starting up the tournament.
	 * 
	 * @param settings The settings for this series of matches.
	 */
	void settings(final Settings settings);

	/**
	 * Reports once during <code>Tournament.GameState.TOURNAMENT_INIT</code> and
	 * <code>Tournament.GameState.TOURNAMENT_OVER</code>.
	 * 
	 * @param curState The current game state of the tournament loop. (Either TOURNAMENT_INIT or TOURNAMENT_OVER)
	 * @param matchupManager Access to the matchup manager if desired.
	 */
	void update(final Tournament.GameState curState, final MatchupManager matchupManager);

	/**
	 * Reports each time <code>Match.GameState.INIT</code> and
	 * <code>Match.GameState.GAME_OVER</code> are called.
	 * 
	 * @param curState The current game state of the match loop. (Either INIT or GAME_OVER)
	 * @param matchup Access to the current matchup if desired.
	 * @param board Access to the board
	 */
	void update(final Match.GameState curState, final Matchup matchup, final Board board);
	
	/**
	 * Reports just after a player makes a move to the game board.
	 *
	 * @param player The player who made the move.
	 * @param move The move that was made.
	 * @param flipList The list of points that were flipped.
	 * @param board The board after the flipList has been applied.
	 */
	void playerMadeMove(final Player player, final Move move, final FlipList flipList, final Board board);

	//NOTE: Consider passing in a timer?
	void playerRanOutOfTime(final Player player);
	void playerGetsToMoveAgain(final Player player);

	/**
	 * Guaranteed to be called once per tournament. This hook is intended to
	 * allow implementations to cleanup/save their state prior to the game
	 * ending.
	 */
	void cleanup();
}
