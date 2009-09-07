package internal.util;

import java.util.Random;

/**
 *
 * @author ablaine
 */
public class RandomWordGenerator {
	private static final int DEFAULT_MIN_SIZE = 4;
	private static final int DEFAULT_MAX_SIZE = 10;
	private static final Random RANDOM = new Random();
	private static char[] ASCII = null;

	public static String createRandomWord(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(getRandomLetter());
		}
		return Character.toUpperCase(sb.charAt(0)) + sb.substring(1);
	}

	public static String createRandomWord() {
		return createRandomWord(RANDOM.nextInt(DEFAULT_MAX_SIZE - DEFAULT_MIN_SIZE) + DEFAULT_MIN_SIZE);
	}

	private static char getRandomLetter() {
		if (ASCII == null) {
			ASCII = new char[26];
			for (int i = 0; i < ASCII.length; i++) {
				ASCII[i] = (char) (i + 97);
			}
		}
		return ASCII[RANDOM.nextInt(ASCII.length)];
	}

//	public static void main(String[] args) {
//		RandomWordGenerator wordGen = new RandomWordGenerator();
//		System.out.println(wordGen.createRandomWord());
//		System.out.println(wordGen.createRandomWord());
//		System.out.println(wordGen.createRandomWord());
//	}
}
