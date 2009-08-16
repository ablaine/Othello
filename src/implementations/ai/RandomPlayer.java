package implementations.ai;

import api.GameLogic;
import api.struct.Board;
import api.PlayerLogic;
import internal.util.UnitConversion;
import java.awt.Point;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ablaine
 */
public class RandomPlayer extends PlayerLogic {
	private static final Random rand = new Random();
	private long test;

	@Override
	public Point makeMove(Board board, List<Point> validMoves) {
		System.out.println("FIRST: " + (long)GameLogic.getRemainingTime() + " (" + UnitConversion.nanosecondToSecond((long)GameLogic.getRemainingTime()) + "s)");
		for (long i = 0; i < 1000000000L; i++) {
			test = i + i * i;
		}
		System.out.println("SECND: " + (long)GameLogic.getRemainingTime() + " (" + UnitConversion.nanosecondToSecond((long)GameLogic.getRemainingTime()) + "s)");
		if (test == 0 ) {
			System.out.println("WOAH");
		}
		return validMoves.get(rand.nextInt(validMoves.size()));
	}
}
