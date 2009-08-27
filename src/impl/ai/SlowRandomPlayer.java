package impl.ai;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import internal.Othello;
import api.GameClock;
import api.PlayerLogic;
import api.struct.Board;
import api.util.UnitConversion.Unit;

/**
 *
 * @author ablaine
 */
public class SlowRandomPlayer extends PlayerLogic {
	private static final Random rand = new Random();
	private static final long delay = 1; // In seconds
	private static final long minTimeLeft = 1; // In seconds
	private GameClock clock;

	@Override
	public void init() {
		System.out.println(Othello.SYSTEM + "This is the \"com.ablaine.Othello\" provided Slow Random AI.");
		clock = getGameClock();
	}

	@Override
	public Point makeMove(Board board, List<Point> validMoves) {
		while (clock.getRemainingTime(Unit.SECOND) > minTimeLeft) {
			System.out.print("");//Wierd bug..
			if (delay > 0) {
				if (clock.getElapsedTime(Unit.SECOND) >= delay) {
					break;
				}
			}
		}
		return validMoves.get(rand.nextInt(validMoves.size()));
	}
}
