package internal;

import api.struct.Board;
import api.struct.Move;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class AIThread extends Thread {
	private final Player player;
	private final Board board;
	private final List<Point> validMoves;
	private boolean isReady = false;
	private Move move = null;

	public AIThread(Player player, Board board, List<Point> validMoves) {
		this.player = player;
		this.board = board;
		this.validMoves = validMoves;
		this.setName("AIThread - " + player.getFullName() + "(" + player.getState() + ")");
	}

	@Override
	public void run() {
		makeMove();//Ends the thread when the move has been made.
	}

	private void makeMove() {
		Move m = null;
		try {
			m = player.makeMove(board, validMoves);
		} catch (BadMoveException e) {
			System.err.println(e.getMessage());
			setMove(null);//Player forfeits match by timeout.
			return;
		}
		if (m == null) {
			System.err.println(player.getFullName() + "(" + player.getState() +
					") has no moves available and was asked to make a move!");
			System.exit(1);//Bug checking
		} else {
			setMove(m);
		}
	}

	public synchronized boolean isReady() {
		return isReady;
	}

	private synchronized void setMove(Move m) {
		move = m;
		isReady = true; //Tell Game we have made our move
	}

	public synchronized Move getMove() {
		return move;
	}
}
