package internal.output;

import api.struct.Board;
import api.struct.FlipList;
import api.struct.Move;
import internal.Match;
import internal.Matchup;
import internal.MatchupManager;
import internal.Player;
import internal.Settings;
import internal.Tournament;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides Othello updates to observers. To add new listeners, just
 * call <code>registerObserver</code> on a class that implements the
 * <code>IOutput</code> interface.
 * 
 * @author Andrew Blaine
 */
public class OutputManager implements IOutput {
	private List<IOutput> observers = new LinkedList<IOutput>();

	@Override
	public void init() {
		for (IOutput o : observers) {
			o.init();
		}
	}

	@Override
	public void settings(final Settings settings) {
		for (IOutput o : observers) {
			o.settings(settings);
		}
	}

	@Override
	public void update(final Tournament.GameState curState, final MatchupManager matchupManager) {
		for (IOutput o : observers) {
			o.update(curState, matchupManager);
		}
	}

	@Override
	public void update(final Match.GameState curState, final Matchup matchup, final Board board) {
		for (IOutput o : observers) {
			o.update(curState, matchup, board);
		}
	}

	@Override
	public void playerMadeMove(final Player player, final Move move, final FlipList flipList, final Board board) {
		for (IOutput o : observers) {
			o.playerMadeMove(player, move, flipList, board);
		}
	}

	@Override
	public void playerRanOutOfTime(final Player player) {
		for (IOutput o : observers) {
			o.playerRanOutOfTime(player);
		}
	}

	@Override
	public void playerGetsToMoveAgain(final Player player) {
		for (IOutput o : observers) {
			o.playerGetsToMoveAgain(player);
		}
	}

	@Override
	public void cleanup() {
		for (IOutput o : observers) {
			o.cleanup();
		}
	}

	/**
	 * Subscribes the IOutput to a list of observers that wish to be notified of
	 * updates.
	 * 
	 * @param o The IOutput implementing class you want to recieve updates.
	 */
	public void registerObserver(IOutput o) {
		observers.add(o);
	}

	/**
	 * Unsubscribes the IOutput from this update list.
	 *
	 * This method only removes the first instance of the class found in the
	 * list. Calling this method on an element that is not in the list does
	 * nothing.
	 * 
	 * @param o The IOutput implementing class you want to remove.
	 */
	public void removeObserver(IOutput o) {
		int i = observers.indexOf(o);
		if (i != -1)
			observers.remove(i);
	}
}
