package internal;

import api.PlayerLogic;
import api.State;
import impl.ai.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Launcher;

/**
 *
 * @author ablaine
 */
public class PlayerLogicFactory {
	public static final String HUMAN_PLAYER = "HumanPlayer";

	public static PlayerLogic createPlayerLogic(String className, Player player, State playerState) {
		if (className.equals(HUMAN_PLAYER)) {
			return null;//TODO
		} else {
			PlayerLogic result = findPlayerLogic(className);
			result._init(player, playerState);
			return result;
		}
	}

	private static PlayerLogic findPlayerLogic(String qualifiedName) {
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

	public static List<String> findPlayerLogicClassNames(String directory) {
		return new Helper(directory).getResult();
	}
}

class Helper {
	private final String aiDirectory;
	
	Helper(String aiDirectory) {
		this.aiDirectory = aiDirectory;
	}

	public List<String> getResult() {
		return searchDirectory(dotToSlash(aiDirectory));
	}

	/**
	 * Expects a directory in "slash" notation.
	 *
	 * @param slashDirectory
	 * @return
	 */
	private List<String> searchDirectory(String slashDirectory) {
		List<String> playerNames = new ArrayList<String>();

		URL url = Launcher.class.getResource(slashDirectory);
		File directory = new File(url.getFile());
		if (directory.exists()) {
			String[] files = directory.list();// Get a list of files in directoryName
			for (String file : files) {
				if (file.endsWith(".class")) {
					String name = checkForAIClass(slashToDot(slashDirectory), file);
					if (name != null) {
						playerNames.add(name);
					}
				} else {
					playerNames.addAll(searchDirectory(slashDirectory + "/" + file));
				}
			}
		}
		return playerNames;
	}

	/**
	 * Returns valid AI names and null for invalid names.
	 *
	 * Expects the prefix in "dot" notation and the className
	 * to have the ".class" on the end.
	 *
	 * @param dotDirectory
	 * @param className
	 * @return
	 */
	private String checkForAIClass(String dotDirectory, String className) {
		String name = className.substring(0, className.length() - ".class".length());// removes the .class extension
		String qualifiedName = dotDirectory + "." + name;
		try {
			Object o = Class.forName(qualifiedName).newInstance();// Try to create an instance of the object
			// if the class implements the PlayerLogic interface we add it to the list
			if (o instanceof PlayerLogic) {
				return qualifiedName;
			}
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} catch (InstantiationException e) {
			// no default constructor
		} catch (IllegalAccessException e) {
			// class is not public
		}
		return null;
	}

	private static String dotToSlash(String s) {
		if (s.length() <= 0)
			return "";
		return "/" + s.replace('.', '/');
	}

	private static String slashToDot(String s) {
		if (s.length() <= 0)
			return "";
		return s.substring(1).replace('/', '.');
	}

}
