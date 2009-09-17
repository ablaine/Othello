
import internal.Othello;

/**
 *
 * @author ablaine
 */
public class Driver {
	
	public static void main(String[] args) {
//		go("");
//		go("-d impl.ai -p RandomPlayer RandomPlayer RandomPlayer");
		go("-d impl.ai -t 3s -p RandomPlayer RandomPlayer RandomPlayer");
	}

	private static void go(String args) {
		Othello.main(args.split(" "));
	}
}
