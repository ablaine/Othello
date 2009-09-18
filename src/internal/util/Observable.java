package internal.util;

import api.struct.FlipList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class Observable {
	private List<IBoardObserver> boardObservers = new ArrayList<IBoardObserver>();
	private List<IGameOverObserver> gameOverObservers = new ArrayList<IGameOverObserver>();

	protected void notifyBoardObservers(FlipList flipList) {
		for (IBoardObserver o : boardObservers) {
			o.updateBoard(flipList);
		}
	}

	protected void notifyGameOverObservers() {
		for (IGameOverObserver o : gameOverObservers) {
			o.updateGameOver();
		}
	}

	public void registerObserver(IBoardObserver o)    { boardObservers.add(o); }
	public void registerObserver(IGameOverObserver o) { gameOverObservers.add(o); }

	public void removeObserver(IBoardObserver o) {
		int i = boardObservers.indexOf(o);
		if (i != -1)
			boardObservers.remove(i);
	}

	public void removeObserver(IGameOverObserver o) {
		int i = gameOverObservers.indexOf(o);
		if (i != -1)
			gameOverObservers.remove(i);
	}

}
