package othello;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import player.Player;
import util.StateManager;

/**
 *
 * @author ablaine
 */
public class Match {
	private enum GameState { INIT, PLAYING, GAMEOVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);
	
//	private enum PlayerTurn { DARK, LIGHT }
//	private final StateManager<PlayerTurn> playerTurn = new StateManager<PlayerTurn>(PlayerTurn.DARK);

	private final Board board;
//	private final Timer timer;
	private final Player dark;
	private final Player light;
	private Player curPlayer;

	public Match(Board board,/* Timer timer,*/ Player dark, Player light) {
		this.board = board;
//		this.timer = timer;
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

	/**
	 * Allows the input player to play a single ply of Othello. (Make a single move on the board).
	 * If the player has no available moves, this will switch to the other player w/o requesting a move.
	 * If the game is over, this will switch to the GAMEOVER state.
	 *
	 * @param player The player to make a move.
	 */
	private void playPly(Player player) {
//		if (stateManager.stateChange()) {
//			player.resetPossibleMoves();//Get rid of the cached list as the board has changed
//			notifyPlayerObservers(player.getState(), player.isHuman());//There is a new player..
//			notifyBoardObservers();//The board has probably been updated.
//			notifyScoreObservers();//The score has probably changed.

			Player otherPlayer = otherPlayer(player);
			
			MoveList validMoves = board.getValidMoves(player.getState());
			if (validMoves.size() > 0) { //Make your move.
				board.updateBoard(player.makeMove(board.clone(), validMoves.toPoints()));
				curPlayer = otherPlayer;
			} else {
				validMoves = board.getValidMoves(otherPlayer.getState());
				if (validMoves.size() > 0) { //Other player goes again.
					board.updateBoard(otherPlayer.makeMove(board.clone(), validMoves.toPoints()));
					curPlayer = player; //No change
				} else { //GameOver
					stateManager.setCurState(GameState.GAMEOVER);
				}
			}
			
//			if (isGameOver()) {
//				stateManager.setCurState(GameState.GAMEOVER);
//				return;
//			}
//			if (player.hasValidMoves()) {
//				notifyPossibleMovesObservers(player.getState(), player.getValidMoves());//Need new transparent move overlays
//			} else {//Skip this player
//				System.out.println(out() + player.getFullName() + " can't move!");
//				setCurGameState(player.getState().getOpposite());
//				return;
//			}
//			aiThread = new AIThread(player);
//			timer.resetCurrentTurnTime();//Start timer and request player move.
//			aiThread.start();
//			return;
//		}
//		Point move = aiThread.getMove();
//		if (move != null) {
//			boardLogic.makeMove(player.getState(), move);
//			setCurGameState(player.getState().getOpposite());
//		} else if (timer.outOfTime()) {
//			if (!player.isHuman()) {
//				System.out.println(out() + player.getFullName() + " ran out of time taking their turn!");
//				System.exit(0);
//			}
//		}
	}

	public void update(long deltaMs) {
		switch (stateManager.getCurState()) {
			case INIT:
				if (stateManager.stateChange()) {
					dark.init(State.DARK);
					light.init(State.LIGHT);
					System.out.println(dark.getName() + " vs " + light.getName());
					stateManager.setCurState(GameState.PLAYING);
				}
				break;
			case PLAYING:
				playPly(curPlayer);
				break;
			case GAMEOVER:
				if (stateManager.stateChange()) {
					System.out.println("Game is over!");
				}
				break;
		}

	}

}
