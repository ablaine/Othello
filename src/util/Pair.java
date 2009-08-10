
package util;

/**
 *
 * @author ablaine
 */
public class Pair<F, S> {
	private F fst;
	private S snd;

	public Pair(F f, S s) {
		fst = f;
		snd = s;
	}

	public F getFirst() {
		return fst;
	}

	public S getSecond() {
		return snd;
	}

	public String toString() {
		return "(" + fst.toString() + ", " + snd.toString() + ")";
	}

}
