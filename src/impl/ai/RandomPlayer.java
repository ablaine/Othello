package impl.ai;

import api.PlayerLogic;
import api.struct.Board;
import java.awt.Point;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ablaine
 */
public class RandomPlayer extends PlayerLogic {
	private static final Random rand = new Random();

	@Override
	public void init() {
		System.out.println("This is the \"com.ablaine.Othello\" provided Random AI.");
	}

	@Override
	public Point makeMove(Board board, List<Point> validMoves) {
		return validMoves.get(rand.nextInt(validMoves.size()));
	}
}
