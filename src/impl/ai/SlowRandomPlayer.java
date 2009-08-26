package impl.ai;

import api.GameLogic;
import api.struct.Board;
import api.PlayerLogic;
import internal.Othello;
import internal.util.UnitConversion;
import java.awt.Point;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ablaine
 */
public class SlowRandomPlayer extends PlayerLogic {
	private static final Random rand = new Random();
	private static final int delay = 1;

	@Override
	public void init() {
		System.out.println(Othello.SYSTEM + "This is the \"com.ablaine.Othello\" provided Slow Random AI.");
	}

	@Override
	public Point makeMove(Board board, List<Point> validMoves) {
		while (GameLogic.getRemainingTime() > UnitConversion.secondToNanosecond(1)) {
			System.out.print("");//Wierd bug..
			if (delay > 0) {
				if (GameLogic.getElapsedTime() >= UnitConversion.secondToNanosecond(delay)) {
					break;
				}
			}
		}
		return validMoves.get(rand.nextInt(validMoves.size()));
	}
}
