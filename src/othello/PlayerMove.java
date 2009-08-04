package othello;

import java.awt.Point;

/**
 *
 * @author ablaine
 */
public class PlayerMove {
	private Point point;
	private State player;

	public PlayerMove(Point point, State player) {
		this.point = point;
		this.player = player;
	}

	public State getPlayer() {
		return player;
	}

	public Point getPoint() {
		return point;
	}
}
