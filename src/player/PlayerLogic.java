package player;

import othello.*;
import java.awt.Point;
import java.util.List;
import othello.IState;

/**
 *
 * @author ablaine
 */
public abstract class PlayerLogic implements IState {
	private String name;
	private State state;

	void setName(String s) {
		name = s;
	}

	void setState(State s) {
		state = s;
	}

	public String getName() {
		return name;
	}

	public State getState() {
		return state;
	}

	public void init() { }

	public void gameOver() { }

	public abstract Point makeMove(Board board, List<Point> validMoves);
}
