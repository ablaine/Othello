package internal;

import api.GameLogic;
import api.State;
import api.struct.MoveList;
import api.struct.Board;
import api.struct.Move;
import internal.util.Observable;
import internal.util.StateManager;
import jig.engine.GameClock.Alarm;

/**
 * Handles the details for playing two players against each other in a game of
 * Othello. This object is good for one round.
 * 
 * @author Andrew Blaine
 */
public class Match extends Observable {
	private enum GameState { INIT, PLAYING, GAME_OVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);

	private final Matchup matchup;
	private final Board board;
	private final Alarm alarm;
	private final Player dark;//TODO: Avoid holding these two vars in favor of 'matchup' ?
	private final Player light;
	private Player curPlayer;
	private AIThread aiThread;

	public Match(Matchup matchup, Board board, Alarm alarm) {
		this.matchup = matchup;
		this.board = board;
		this.alarm = alarm;
		dark = matchup.getFirst();
		light = matchup.getSecond();
		curPlayer = dark;
	}

	/**
	 * Allows the input player to play a single ply of Othello. (Make a single move on the board).
	 * If the player has no available moves, this will switch to the other player w/o requesting a move.
	 * If the game is over, this will switch to the GAMEOVER state.
	 *
	 */
	private void playPly() {
		if (stateManager.isStateChange()) {
			MoveList validMoves = GameLogic.getValidMoves(board, curPlayer.getState());
			if (validMoves.size() > 0) { //Make your move.
				aiThread = new AIThread(curPlayer, board.clone(), validMoves.toPoints());
				alarm.reset();
				aiThread.start();
				return;
			} else {
				nextPlayer();
				validMoves = GameLogic.getValidMoves(board, curPlayer.getState());
				if (validMoves.size() > 0) { //Other player goes again.
					System.out.println(Othello.SYSTEM + curPlayer.getNicknameAndState() + " gets to move again!");
					aiThread = new AIThread(curPlayer, board.clone(), validMoves.toPoints());
					alarm.reset();
					aiThread.start();
					return;
				} else { //GameOver
					gameOver();
					return;
				}
			}
		}
		if (aiThread.isReady()) {
			Move move = aiThread.getMove();
			if (move != null) {
				GameLogic.makeDestructiveMove(board, move);
				nextPlayer();
				stateManager.setCurState(GameState.PLAYING);
			} else { // Forfeit turn due to invalid move.
				gameOver(matchup.otherPlayer(curPlayer));
				return;
			}
		} else if (alarm.expired()) {
			System.out.println(Othello.SYSTEM + curPlayer.getNicknameAndState() + " has just run out of time.");
			gameOver(matchup.otherPlayer(curPlayer));
			return;
		}
	}

	private void gameOver() {
		int darkScore = board.getScore(State.DARK);
		int lightScore = board.getScore(State.LIGHT);
		if (darkScore > lightScore) {
			gameOver(dark);
		} else if (lightScore > darkScore) {
			gameOver(light);
		} else {
			gameOver(null);// It was a tie.
		}
	}
	
	private void gameOver(Player winner) {
		matchup.playedMatch(winner);
		stateManager.setCurState(GameState.GAME_OVER);
	}

	public void update(long deltaMs) {
		switch (stateManager.getCurState()) {
			case INIT:
				if (stateManager.isStateChange()) {
					dark.init(State.DARK);
					light.init(State.LIGHT);
					System.out.println(Othello.SYSTEM + dark.getNicknameAndState() + " vs " +
														light.getNicknameAndState());
					stateManager.setCurState(GameState.PLAYING);
				}
				break;
			case PLAYING:
				playPly();
				break;
			case GAME_OVER:
				if (stateManager.isStateChange()) {
					System.out.print(Othello.SYSTEM + "Game is over... ");
					MatchStatus matchStatus = matchup.getPreviousMatchState();
					if (matchStatus.getState() == MatchStatus.State.TIED) {
						System.out.println("it was a tie!");
					} else if (matchStatus.getState() == MatchStatus.State.WINNER) {
						System.out.println(matchStatus.getWinner().getNicknameAndState() + " is the winner!");
					}
					notifyGameOverObservers();
				}
				break;
		}
	}

	/* MINOR CONVENIENCE METHODS
	 **************************************************************************/

	private void nextPlayer() {//NOTE: Do NOT set GameState.PLAYING here.
		curPlayer = matchup.otherPlayer(curPlayer);
	}
}
