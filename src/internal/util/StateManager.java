package internal.util;

/**
 * Manages state information for a class by providing setters/getters and
 * a method for finding out if the state has changed recently.
 *
 * Originally envisioned as something a class would extend, this is
 * now a object held by the object wishing that functionality. This allows
 * for more versatility for the class using it.
 *
 * @author Andrew Blaine
 *
 * @param <E> A user specified <code>enum</code> for different states this class can be in.
 */
public class StateManager<E> {
	private E curState;
	private boolean stateChange = false;

	public StateManager(E initialState) {
		setCurState(initialState);
	}

	/**
	 * Returns the current state.
	 * @return
	 */
	public E getCurState() {
		return curState;
	}

	/**
	 * Sets the current state and sets the "state is changed" flag to true.
	 * @param state
	 */
	public void setCurState(E state) {
		curState = state;
		stateChange = true;
	}

	/**
	 * Returns true only once per state change.
	 * @return
	 */
	public boolean isStateChange() {
		if (stateChange) {
			stateChange = false;
			return true;
		}
		return false;
	}
}
