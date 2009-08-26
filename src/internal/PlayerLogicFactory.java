package internal;

import api.PlayerLogic;
import api.State;
import impl.ai.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ablaine
 */
public class PlayerLogicFactory {
	public static final String HUMAN_PLAYER = "HumanPlayer";

	public PlayerLogic createPlayerLogic(String className, Player player, State playerState) {
		if (className.equals(HUMAN_PLAYER)) {
			return null;//TODO
		} else {
			PlayerLogic result = findPlayerLogic(className);
			result._init(player, playerState);
			return result;
		}
	}

	private PlayerLogic findPlayerLogic(String qualifiedName) {
		try {
			Class<?> c = Class.forName(qualifiedName);
			Object o = c.newInstance();
			PlayerLogic logic = (PlayerLogic)o;
			return logic;
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Does this path/to/className.class exist: " + qualifiedName.replace('.', '/') + ".class");
			System.exit(0);
		}
		return null;
	}
}
