package internal.util;

import api.struct.FlipList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class Observable {
	private List<BoardObserver> boardObservers = new ArrayList<BoardObserver>();
	private List<GameOverObserver> gameOverObservers = new ArrayList<GameOverObserver>();

	protected void notifyBoardObservers(FlipList flipList) {
		for (BoardObserver o : boardObservers) {
			o.updateBoard(flipList);
		}
	}

	protected void notifyGameOverObservers() {
		for (GameOverObserver o : gameOverObservers) {
			o.updateGameOver();
		}
	}

	public void registerObserver(BoardObserver o)    { boardObservers.add(o); }
	public void registerObserver(GameOverObserver o) { gameOverObservers.add(o); }

	public void removeObserver(BoardObserver o) {
		int i = boardObservers.indexOf(o);
		if (i != -1)
			boardObservers.remove(i);
	}

	public void removeObserver(GameOverObserver o) {
		int i = gameOverObservers.indexOf(o);
		if (i != -1)
			gameOverObservers.remove(i);
	}

}
