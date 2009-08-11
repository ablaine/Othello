package implementations.ai;

import player.PlayerLogic;
import othello.*;
import java.awt.Point;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ablaine
 */
public class RandomPlayer extends PlayerLogic {
	private static Random rand = new Random();

	@Override
	public Point makeMove(Board board, List<Point> validMoves) {
		return validMoves.get(rand.nextInt(validMoves.size()));
	}
}
