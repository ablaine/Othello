package player;

import java.awt.Point;
import java.util.List;
import othello.Board;
import othello.State;
import othello.IState;
import othello.Move;

/**
 *
 * @author ablaine
 */
public class Player implements IState {
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
		logic = factory.createPlayerLogic(logicClassName);
		logic.setName(logicClassName);
		logic.setState(s);
		logic.init();
	}

	public void cleanup() {
		if (isActive()) {
			logic.gameOver();
			logic = null;
			System.gc(); //Attempt to cleanup leftover memory
		}
	}

	public Move makeMove(Board board, List<Point> validMoves) {
		if (isActive()) {
			return new Move(logic.makeMove(board, validMoves), getState(), board);
		}
		return null;//TODO look into this.
	}

	public boolean isActive() {
		return logic != null;
	}

	public String getName() {
		if (isActive()) {
			return logic.getName();
		}
		return "<Non-active player>";
	}

	public State getState() {
		if (isActive()) {
			return logic.getState();
		}
		return State.EMPTY;
	}

	public String getFullName() {
		return getName() + "(" + getState().toString() + ")";
	}

}
