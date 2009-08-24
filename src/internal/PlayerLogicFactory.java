package internal;

import api.PlayerLogic;
import api.State;

/**
 *
 * @author ablaine
 */
public class PlayerLogicFactory {
	private static final String AI_PACKAGE = "impl.ai";
	public static final String HUMAN_PLAYER = "HumanPlayer";

	public PlayerLogic createPlayerLogic(String directory, String className, Player player, State playerState) {
		if (className.equals(HUMAN_PLAYER)) {
			return null;//TODO
		} else {
			PlayerLogic result = findPlayerLogic(directory, className);
			result._init(player, playerState);
			return result;
		}
	}

	public PlayerLogic createPlayerLogic(String className, Player player, State playerState) {
		return createPlayerLogic(AI_PACKAGE, className, player, playerState);
	}

	private PlayerLogic findPlayerLogic(String directory, String className) {
		String qualifiedName = directory + "." + className;
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
			System.err.println("Is " + className + ".class in the " + directory.replace('.', '/') + " directory?");
			System.exit(0);
		}
		return null;
	}
}
