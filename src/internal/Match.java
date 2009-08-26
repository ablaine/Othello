package internal;

import api.GameLogic;
import api.State;
import api.struct.MoveList;
import api.struct.Board;
import api.struct.Move;
import internal.util.StateManager;
import jig.engine.GameClock.Alarm;

/**
 *
 * @author ablaine
 */
public class Match {
	private enum GameState { INIT, PLAYING, GAMEOVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);

	private final Board board;
	private Alarm alarm;
	private final Player dark;
	private final Player light;
	private Player curPlayer;
	private AIThread aiThread;
	private State winner = null;

	public Match(Alarm alarm, Board board, Player dark, Player light) {
		this.alarm = alarm;
		this.board = board;
		this.dark = dark;
		this.light = light;
		curPlayer = dark;
	}

	private Player otherPlayer(Player p) {
		if (dark == p) { //Checking memory address
			return light;
		} else {
			return dark;
		}
	}

	private void nextPlayer() {//NOTE: Do NOT set GameState PLAYING here.
		curPlayer = otherPlayer(curPlayer);
	}

	/**
	 * Allows the input player to play a single ply of Othello. (Make a single move on the board).
	 * If the player has no available moves, this will switch to the other player w/o requesting a move.
	 * If the game is over, this will switch to the GAMEOVER state.
	 *
	 */
	private void playPly() {
		if (stateManager.stateChange()) {
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
					System.out.println(Othello.SYSTEM + curPlayer.getFullName() + " gets to move again!");
					aiThread = new AIThread(curPlayer, board.clone(), validMoves.toPoints());
					alarm.reset();
					aiThread.start();
					return;
				} else { //GameOver
					updateWinner();
					return;
				}
			}
		}
		if (aiThread.isReady()) {
			Move move = aiThread.getMove();
			if (move != null) {
				GameLogic.makeMove(board, move);
				nextPlayer();
				stateManager.setCurState(GameState.PLAYING);
			} else { // Forfeit turn due to invalid move.
				updateWinner(otherPlayer(curPlayer).getState());
				return;
			}
		} else if (alarm.expired()) {
			System.out.println(Othello.SYSTEM + curPlayer.getFullName() + " has just run out of time.");
			updateWinner(otherPlayer(curPlayer).getState());
			return;
		}
	}

	private void updateWinner() {
		int darkScore = board.getScore(State.DARK);
		int lightScore = board.getScore(State.LIGHT);
		if (darkScore > lightScore) {
			updateWinner(State.DARK);
		} else if (lightScore > darkScore) {
			updateWinner(State.LIGHT);
		} else {
			updateWinner(State.EMPTY);
		}
	}

	private void updateWinner(State winner) {
		switch (winner) {
			case DARK:
				dark.updateWins();
				light.updateLosses();
				break;
			case LIGHT:
				light.updateWins();
				dark.updateLosses();
				break;
			default:
				dark.updateTies();
				light.updateTies();
				break;
		}
		this.winner = winner;
		stateManager.setCurState(GameState.GAMEOVER);
	}

	public void update(long deltaMs) {
		switch (stateManager.getCurState()) {
			case INIT:
				if (stateManager.stateChange()) {
					dark.init(State.DARK);
					light.init(State.LIGHT);
					System.out.println(Othello.SYSTEM + dark.getName() + " vs " + light.getName());
					stateManager.setCurState(GameState.PLAYING);
				}
				break;
			case PLAYING:
				playPly();
				break;
			case GAMEOVER:
				if (stateManager.stateChange()) {
					System.out.println(Othello.SYSTEM + "Game is over!");
					switch (winner) {
						case EMPTY:
							System.out.println(Othello.SYSTEM + "It was a tie!");
							break;
						default:
							System.out.println(Othello.SYSTEM + winner.toString() + " is the winner!");
							break;
					}
				}
				break;
		}
	}
}
