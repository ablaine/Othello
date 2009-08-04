package othello;

import java.awt.Point;

/**
 *
 * @author ablaine
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Board b = new Board();
		
		System.out.println("Here is the board:");
		System.out.println(b.toString());
//		b.makeMove(new PlayerMove(new Point(2, 3), State.LIGHT));
		b.makeMove(new PlayerMove(new Point(2, 3), State.DARK));
		System.out.println("Here is the new board:");
		System.out.println(b.toString());

	}
}
