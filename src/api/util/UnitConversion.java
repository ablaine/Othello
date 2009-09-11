package api.util;

/**
 * A utility class for converting between seconds, milliseconds, microseconds
 * and nanoseconds. Everything is handled in <code>long</code>s. Moving from
 * smaller units to larger units will likely result in a loss of precision.
 * 
 * @author Andrew Blaine
 */
public class UnitConversion {
	/**
	 * Simple enum to represent a small set of units in time.
	 */
	public enum Unit { SECOND/*s*/, MILLISECOND/*ms*/, MICROSECOND/*mu*/, NANOSECOND/*ns*/ }

	/**
	 * Simple unit converter. Moving from smaller units to larger units will
	 * likely result in a loss of precision.
	 * 
	 * @param value The value to convert.
	 * @param from The unit of the value being passed in.
	 * @param to The unit of the value being returned.
	 * @return the value represented in the new unit
	 */
	public static long convert(long value, Unit from, Unit to) {
		switch (from) {
			case SECOND:
				switch (to) {
					case SECOND:      return value * 1L;
					case MILLISECOND: return value * 1000L;
					case MICROSECOND: return value * 1000000L;
					case NANOSECOND:  return value * 1000000000L;
				}
				break;
			case MILLISECOND:
				switch (to) {
					case SECOND:      return value / 1000L;
					case MILLISECOND: return value * 1L;
					case MICROSECOND: return value * 1000L;
					case NANOSECOND:  return value * 1000000L;
				}
				break;
			case MICROSECOND:
				switch (to) {
					case SECOND:      return value / 1000000L;
					case MILLISECOND: return value / 1000L;
					case MICROSECOND: return value * 1L;
					case NANOSECOND:  return value * 1000L;
				}
				break;
			case NANOSECOND:
				switch (to) {
					case SECOND:      return value / 1000000000L;
					case MILLISECOND: return value / 1000000L;
					case MICROSECOND: return value / 1000L;
					case NANOSECOND:  return value * 1L;
				}
				break;
		}
		System.err.println("Switch statement does not handle all cases:");
		System.err.println("@ convert(long value, Unit from, Unit to) in " +
				UnitConversion.class.getCanonicalName());
		System.exit(0);
		return 0;
	}
}
