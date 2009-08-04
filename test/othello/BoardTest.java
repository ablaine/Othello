/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package othello;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ablaine
 */
public class BoardTest {

    public BoardTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of makeMove method, of class OthelloBoard.
	 */
	@Test
	public void testMakeMove() {
		System.out.println("makeMove");
		PlayerMove playerMove = new PlayerMove(new Point(2, 3), State.DARK);
		Board instance = new Board();
		instance.makeMove(playerMove);
		State[][] s = new State[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				s[x][y] = State.EMPTY;
			}
		}
		s[4][4] = State.LIGHT;
		s[3][3] = State.DARK; //Changed
		s[3][4] = State.DARK;
		s[4][3] = State.DARK;
		s[2][3] = State.DARK; //Changed
		
		Board expResult = new Board(s);
		assertArrayEquals(new Board(s).toString() +
							"==================\n" +
							instance.toString(), expResult.getBoard(), instance.getBoard());
	}

	/**
	 * Test of withinBounds method, of class OthelloBoard.
	 */
	@Test
	public void testWithinBounds() {
		System.out.println("withinBounds");
		Board instance = new Board();

		assertFalse(instance.withinBounds(new Point(0, 8)));
		assertFalse(instance.withinBounds(new Point(8, 0)));
		assertFalse(instance.withinBounds(new Point(-1, 7)));
		assertFalse(instance.withinBounds(new Point(7, -1)));
		
		assertTrue(instance.withinBounds(new Point(0, 7)));
		assertTrue(instance.withinBounds(new Point(7, 0)));
		assertTrue(instance.withinBounds(new Point(0, 0)));
		assertTrue(instance.withinBounds(new Point(7, 7)));
		assertTrue(instance.withinBounds(new Point(5, 5)));
	}

	/**
	 * Test of getBoard method, of class OthelloBoard.
	 */
	@Test
	public void testGetBoard() {
		System.out.println("getBoard");
		State[][] expResult = new State[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				expResult[x][y] = State.EMPTY;
			}
		}
		expResult[4][4] = State.LIGHT;
		expResult[3][3] = State.LIGHT;
		expResult[3][4] = State.DARK;
		expResult[4][3] = State.DARK;

		Board instance = new Board();
		State[][] result = instance.getBoard();
		
		assertArrayEquals(expResult, result);
	}

	/**
	 * Test of toString method, of class OthelloBoard.
	 */
	@Test
	public void testToString() {
		System.out.println("toString");
		StringBuilder sb = new StringBuilder();
//		sb.append("  a b c d e f g h \n");
//		sb.append("1 . . . . . . . . \n");
//		sb.append("2 . . . . . . . . \n");
//		sb.append("3 . . . . . . . . \n");
//		sb.append("4 . . . O X . . . \n");
//		sb.append("5 . . . X O . . . \n");
//		sb.append("6 . . . . . . . . \n");
//		sb.append("7 . . . . . . . . \n");
//		sb.append("8 . . . . . . . . \n");
		sb.append("  0 1 2 3 4 5 6 7 \n");
		sb.append("0 . . . . . . . . \n");
		sb.append("1 . . . . . . . . \n");
		sb.append("2 . . . . . . . . \n");
		sb.append("3 . . . O X . . . \n");
		sb.append("4 . . . X O . . . \n");
		sb.append("5 . . . . . . . . \n");
		sb.append("6 . . . . . . . . \n");
		sb.append("7 . . . . . . . . \n");
		String expResult = sb.toString();

		Board instance = new Board();
		String result = instance.toString();
		
		assertEquals(expResult, result);
	}

}