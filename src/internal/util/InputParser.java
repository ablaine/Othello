package internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ablaine
 */
public class InputParser extends HashMap<String, List<String>> {
	public final static String COMMENT = "//";
	public final static String FLAG = "-";

	private InputParser(Map<String, List<String>> map) {
		super(map);
	}

	public static InputParser createKeyValueStore(String[] args) {
		return new InputParser(groupKeysToValues(removeComments(args)));
	}
	
	private static Map<String, List<String>> groupKeysToValues(String[] args) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for (int key = 0; key < args.length; key++) {
			if (args[key].startsWith(FLAG)) {
				List<String> values = new ArrayList<String>();
				for (int value = key + 1; value < args.length; value++) {
					if (args[value].startsWith(FLAG)) {
						break;
					} else {
						values.add(args[value]);
					}
				}
				result.put(args[key], values);
			}
		}
		return result;
	}

	private static String[] removeComments(String[] args) {
		String[] result = args;
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith(COMMENT)) {
				result = Arrays.copyOfRange(args, 0, i);
				break;
			}
		}
		return result;
	}

	public boolean containsKey(Object... keys) {
		for (Object o : keys) {
			if (containsKey(o)) {
				return true;
			}
		}
		return false;
	}

	public List<String> get(Object... keys) {
		List<String> result = new LinkedList<String>();
		for (Object o : keys) {
			List<String> items = get(o);
			if (items != null) {
				result.addAll(items);
			}
		}
		return result;
	}
}
