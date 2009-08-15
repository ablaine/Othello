//package othello;
//
//import java.awt.Point;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author ablaine
// */
//public class BoardTest {
//
//    public BoardTest() {
//    }
//
//	@BeforeClass
//	public static void setUpClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownClass() throws Exception {
//	}
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//	/**
//	 * Test of updateBoard method, of class Board.
//	 */
//	@Test
//	public void testUpdateBoard_Point_State() {
//		System.out.println("updateBoard");
//		Board instance = new Board();
//		instance.updateBoard(new Point(2, 3), State.DARK);
//		State[][] s = new State[8][8];
//		for (int x = 0; x < 8; x++) {
//			for (int y = 0; y < 8; y++) {
//				s[x][y] = State.EMPTY;
//			}
//		}
//		s[4][4] = State.LIGHT;
//		s[3][3] = State.DARK; //Changed
//		s[3][4] = State.DARK;
//		s[4][3] = State.DARK;
//		s[2][3] = State.DARK; //Changed
//
//		Board expResult = new Board(s);
//		assertArrayEquals(new Board(s).toString() +
//							"==================\n" +
//							instance.toString(), expResult.clone().getBoard(), instance.clone().getBoard());
//	}
//
//	/**
//	 * Test of updateBoard method, of class Board.
//	 */
//	@Test
//	public void testUpdateBoard_Move() {
//		System.out.println("updateBoard");
//		Board instance = new Board();
//		MyMove playerMove = new MyMove(new Point(2, 3), State.DARK, instance);
//		instance.updateBoard(playerMove);
//		State[][] s = new State[8][8];
//		for (int x = 0; x < 8; x++) {
//			for (int y = 0; y < 8; y++) {
//				s[x][y] = State.EMPTY;
//			}
//		}
//		s[4][4] = State.LIGHT;
//		s[3][3] = State.DARK; //Changed
//		s[3][4] = State.DARK;
//		s[4][3] = State.DARK;
//		s[2][3] = State.DARK; //Changed
//
//		Board expResult = new Board(s);
//		assertArrayEquals(new Board(s).toString() +
//							"==================\n" +
//							instance.toString(), expResult.clone().getBoard(), instance.clone().getBoard());
//	}
//
//	/**
//	 * Test of isWithinBounds method, of class Board.
//	 */
//	@Test
//	public void testIsWithinBounds_Point() {
//		System.out.println("isWithinBounds");
//		Board instance = new Board();
//
//		assertFalse(instance.isWithinBounds(new Point(0, 8)));
//		assertFalse(instance.isWithinBounds(new Point(8, 0)));
//		assertFalse(instance.isWithinBounds(new Point(-1, 7)));
//		assertFalse(instance.isWithinBounds(new Point(7, -1)));
//
//		assertTrue(instance.isWithinBounds(new Point(0, 7)));
//		assertTrue(instance.isWithinBounds(new Point(7, 0)));
//		assertTrue(instance.isWithinBounds(new Point(0, 0)));
//		assertTrue(instance.isWithinBounds(new Point(7, 7)));
//		assertTrue(instance.isWithinBounds(new Point(5, 5)));
//	}
//
//	/**
//	 * Test of isWithinBounds method, of class Board.
//	 */
//	@Test
//	public void testIsWithinBounds_int_int() {
//		System.out.println("isWithinBounds");
//		Board instance = new Board();
//
//		assertFalse(instance.isWithinBounds(0, 8));
//		assertFalse(instance.isWithinBounds(8, 0));
//		assertFalse(instance.isWithinBounds(-1, 7));
//		assertFalse(instance.isWithinBounds(7, -1));
//
//		assertTrue(instance.isWithinBounds(0, 7));
//		assertTrue(instance.isWithinBounds(7, 0));
//		assertTrue(instance.isWithinBounds(0, 0));
//		assertTrue(instance.isWithinBounds(7, 7));
//		assertTrue(instance.isWithinBounds(5, 5));
//	}
//
////	/**
////	 * Test of getState method, of class Board.
////	 */
////	@Test
////	public void testGetState_Point() {
////		System.out.println("getState");
////		Point p = null;
////		Board instance = new Board();
////		State expResult = null;
////		State result = instance.getState(p);
////		assertEquals(expResult, result);
////		// TODO review the generated test code and remove the default call to fail.
////		fail("The test case is a prototype.");
////	}
////
////	/**
////	 * Test of getState method, of class Board.
////	 */
////	@Test
////	public void testGetState_int_int() {
////		System.out.println("getState");
////		int x = 0;
////		int y = 0;
////		Board instance = new Board();
////		State expResult = null;
////		State result = instance.getState(x, y);
////		assertEquals(expResult, result);
////		// TODO review the generated test code and remove the default call to fail.
////		fail("The test case is a prototype.");
////	}
////
////	/**
////	 * Test of getBoard method, of class Board.
////	 */
////	@Test
////	public void testGetBoard() {
////		System.out.println("getBoard");
////		Board instance = new Board();
////		State[][] expResult = null;
////		State[][] result = instance.getBoard();
////		assertEquals(expResult, result);
////		// TODO review the generated test code and remove the default call to fail.
////		fail("The test case is a prototype.");
////	}
//
//	/**
//	 * Test of clone method, of class Board.
//	 */
//	@Test
//	public void testClone() {
//		System.out.println("clone");
//		State[][] expResult = new State[8][8];
//		for (int x = 0; x < 8; x++) {
//			for (int y = 0; y < 8; y++) {
//				expResult[x][y] = State.getRandom();
//			}
//		}
//
//		Board instance = new Board(expResult).clone();
//
//		assertArrayEquals(expResult, instance.getBoard());
//	}
//
//	/**
//	 * Test of toString method, of class Board.
//	 */
//	@Test
//	public void testToString() {
//		System.out.println("toString");
//		StringBuilder sb = new StringBuilder();
//		sb.append("  0 1 2 3 4 5 6 7 \n");
//		sb.append("0 . . . . . . . . \n");
//		sb.append("1 . . . . . . . . \n");
//		sb.append("2 . . . . . . . . \n");
//		sb.append("3 . . . O X . . . \n");
//		sb.append("4 . . . X O . . . \n");
//		sb.append("5 . . . . . . . . \n");
//		sb.append("6 . . . . . . . . \n");
//		sb.append("7 . . . . . . . . \n");
//		String expResult = sb.toString();
//
//		Board instance = new Board();
//		String result = instance.toString();
//
//		assertEquals(expResult, result);
//	}
//
//}