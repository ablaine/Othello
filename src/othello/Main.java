package othello;

import java.awt.Point;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.physics.vpe.VanillaAARectangle;
import player.Player;
import util.StateManager;
import view.BoardDisplay;
import view.View;

/**
 *
 * @author Andrew Blaine
 */
public class Main extends StaticScreenGame {

	private enum GameState { INIT, PLAYING, GAMEOVER }
	private final StateManager<GameState> stateManager = new StateManager<GameState>(GameState.INIT);

	public static final String RSC_PATH = "resources/";
	public static final String SPRITE_SHEET = RSC_PATH + "spritesheet.gif";

	private AbstractBodyLayer<VanillaAARectangle> tileLayer;

	private View view;
	private Match match;

	public Main() {
		super(View.WORLD_WIDTH, View.WORLD_HEIGHT, false);
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
		gameframe.setTitle("Othello");

		tileLayer = new AbstractBodyLayer.IterativeUpdate<VanillaAARectangle>();

		gameObjectLayers.add(tileLayer);

		Board b = new Board();
		BoardDisplay boardDisplay = new BoardDisplay(b, tileLayer);
		view = new View(boardDisplay);

		Player dark = new Player("RandomPlayer");
		Player light = new Player("RandomPlayer");
		match = new Match(b, dark, light);
//		Board b = new Board();
//
//		System.out.println("Here is the board:");
//		System.out.println(b.toString());
////		b.makeMove(new PlayerMove(new Point(2, 3), State.LIGHT));
//		b.makeMove(new Piece(new Point(2, 3), State.DARK));
//		System.out.println("Here is the new board:");
//		System.out.println(b.toString());
	}

	@Override
	public void update(final long deltaMs) {
		super.update(deltaMs);
		switch(stateManager.getCurState()) {
			case INIT:
				match.update(deltaMs);
				break;
			case PLAYING:
				break;
			case GAMEOVER:
				break;
		}
	}

	@Override
	public void render(final RenderingContext rc) {
		super.render(rc);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
}
