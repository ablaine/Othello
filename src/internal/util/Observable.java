package internal.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew Blaine
 */
public abstract class Observable {
	private List<IGameOverObserver> gameOverObservers = new ArrayList<IGameOverObserver>();

	protected void notifyGameOverObservers() {
		for (IGameOverObserver o : gameOverObservers) {
			o.updateGameOver();
		}
	}

	public void registerObserver(IGameOverObserver o) { gameOverObservers.add(o); }

	public void removeObserver(IGameOverObserver o) {
		int i = gameOverObservers.indexOf(o);
		if (i != -1)
			gameOverObservers.remove(i);
	}
}
