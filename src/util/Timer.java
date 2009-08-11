//package util;
//
//import jig.engine.GameClock;
//import jig.engine.GameClock.Alarm;
//
///**
// *
// * @author ablaine
// */
//public class Timer {
//	private static final int GRANULARITY = 10;
//	private static GameClock clock;
//	private final Alarm alarm;
//	private static int totalTime;
//	private static int currentTurnTime;
//	private static double timeLimitPerTurn;
//
//	public static void init(GameClock clock, double timeLimitPerTurn) {
//		if (Timer.clock == null) {
//			Timer.clock = clock;
//			Timer.timeLimitPerTurn = timeLimitPerTurn * GRANULARITY;
//			totalTime = 0;
//			currentTurnTime = 0;
//		}
//	}
//
//}
