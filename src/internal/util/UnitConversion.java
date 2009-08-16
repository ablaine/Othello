package internal.util;

/**
 *
 * @author ablaine
 */
public class UnitConversion {
	private final static long second = 1000000000L;
	private final static long millisecond = 1000000L;
	private final static long microsecond = 1000L;

	public static long nanosecondToSecond(long num) {
		return num / second;
	}

	public static long nanosecondToMillisecond(long num) {
		return num / millisecond;
	}

	public static long nanosecondToMicrosecond(long num) {
		return num / microsecond;
	}

	public static long secondToNanosecond(long num) {
		return num * second;
	}

	public static long secondToMillisecond(long num) {
		return num * millisecond;
	}

	public static long secondToMicrosecond(long num) {
		return num * microsecond;
	}

}
