package player;

/**
 *
 * @author ablaine
 */
public class PlayerLogicFactory {
	private static final String AI_PACKAGE = "implementations.ai";
	public static final String HUMAN_PLAYER = "HumanPlayer";

	public PlayerLogic createPlayerLogic(String className) {
		if (className.equals(HUMAN_PLAYER)) {
			return null;//TODO
		} else {
			return findPlayerLogic(className);
		}
	}

	private PlayerLogic findPlayerLogic(String className) {
		String qualifiedName = AI_PACKAGE + "." + className;
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
			System.err.println("Is " + className + ".class in the " + AI_PACKAGE.replace('.', '/') + " directory?");
			System.exit(0);
		}
		return null;
	}
}
