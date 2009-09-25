package internal.output;

import jig.engine.physics.vpe.VanillaAARectangle;
import jig.engine.util.Vector2D;
import internal.Othello;
import api.State;

/**
 *
 * @author ablaine
 */
public class Tile extends VanillaAARectangle {
	private enum Frame {
		LIGHT(0), DARK(1), LIGHT_TRANS(2), DARK_TRANS(3), EMPTY(4);

		private int value;

		Frame(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	private final int x;
	private final int y;
	private State state;

	public Tile (int x, int y, State s) {
		super(Othello.SPRITE_SHEET + "#othello-piece");
		this.x = x;
		this.y = y;
		setState(s);
		setPosition(new Vector2D(
				((getWidth()  + GUIOutput.GRID_SPACING) * getX()) + GUIOutput.GRID_SPACING,
				((getHeight() + GUIOutput.GRID_SPACING) * getY()) + GUIOutput.GRID_SPACING));
	}

	public void setState(State s) {
		state = s;
		switch(state) {
			case EMPTY: setFrame(Frame.EMPTY.getValue()); break;
			case DARK:  setFrame(Frame.DARK.getValue());  break;
			case LIGHT: setFrame(Frame.LIGHT.getValue()); break;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public State getState() {
		return state;
	}

	@Override
	public void update(long deltaMs) {
		//Do nothing
	}
}
