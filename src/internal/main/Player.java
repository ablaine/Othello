package internal.main;

import java.awt.Point;
import java.util.List;

import api.struct.Board;
import api.struct.Move;
import api.PlayerLogic;
import api.State;

/**
 *
 * @author ablaine
 */
public class Player {
	private final static PlayerLogicFactory factory = new PlayerLogicFactory();
	private int wins = 0;
	private int ties = 0;
	private int losses = 0;

	private final String logicClassName;
	private PlayerLogic logic;

	public Player(String playerLogic) {
		logicClassName = playerLogic;
	}

	public void init(State s) {
		logic = factory.createPlayerLogic(logicClassName, this, s);
		logic.init();
	}

	public void cleanup() {
		if (isActive()) {
			logic.gameOver();
			logic = null;
			System.gc(); //Attempt to cleanup leftover memory
		}
	}

	/**
	 * If the player is inactive, return null.
	 * If the player makes an invalid move, throw exception.
	 * Otherwise, return the move.
	 * 
	 * @param board
	 * @param validMoves
	 * @return
	 * @throws internal.main.BadMoveException
	 */
	public Move makeMove(Board board, List<Point> validMoves) throws BadMoveException {
		Move result = null;
		if (isActive()) {
			Point p = logic.makeMove(board, validMoves);
			if (p == null || !validMoves.contains(p)) {
				throw new BadMoveException(getFullName() + " tried to make an invalid move.");
			}
			result = new Move(getState(), p);
		}
		return result;
	}

	public boolean isActive() {
		return logic != null;
	}

	public String getName() {
		return logicClassName;
	}

	public String getFullName() {
		return getName() + "(" + getState().toString() + ")";
	}

	public State getState() {
		if (isActive()) {
			return logic.getState();
		}
		return State.EMPTY;
	}

	public void updateWins()   { wins++;   }
	public void updateTies()   { ties++;   }
	public void updateLosses() { losses++; }

	public int getWins()   { return wins;   }
	public int getTies()   { return ties;   }
	public int getLosses() { return losses; }
	public int getGamesPlayed() { return wins + ties + losses; }

	/**
	 * Returns this players score calculated by:
	 * <ul>
	 *   <li>wins = 2</li>
	 *   <li>ties = 1</li>
	 *   <li>losses = 0</li>
	 * </ul>
	 * @return
	 */
	public int getScore() { return wins*2 + ties; }

	@Override
	public String toString() {
		return getFullName();
	}

}
