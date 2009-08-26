package internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ablaine
 */
public class InputParser {
	private List<String> inputs;
	private String defaultValue = "";

	public InputParser(String[] args) {
		inputs = new ArrayList<String>(Arrays.asList(args));
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("//")) {
				inputs = inputs.subList(0, i);
				break;
			}
		}
	}

	public String getArg(int item) {
		return isArg(item) ? inputs.get(item) : null;
	}

	/**
	 * Tests 
	 */
//	public static void main(String[] args) {
//		String[] myArgs = "-blah something again --testing // no ok blah -asdf".split(" ");
//
//		InputParser ip = new InputParser(myArgs);
//		List<String> blah = ip.parsePref("blah");
//		System.out.println(":: -blah");
//		for (String s : blah) {
//			System.out.println(s);
//		}
//		List<String> testing = ip.parsePref("testing");
//		System.out.println(":: -testing");
//		for (String s : testing) {
//			System.out.println(s);
//		}
//		List<String> notfound = ip.parsePref("notfound");
//		System.out.println(":: -notfound");
//		if (notfound != null) {
//			for (String s : notfound) {
//				System.out.println(s);
//			}
//		}
//	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isValue(int index) {
		return !isFlag(getArg(index));
	}

	private boolean isArg(int index) {
		return index < inputs.size();
	}

	public boolean isFlag(int index) {
		return isFlag(getArg(index));
	}

	public boolean isFlag(String flag) {
		return flag != null ? flag.startsWith("-") : false;
	}

	public boolean exists(String flag) {
		return exists(flag, flag);
	}

	public boolean exists(String fullFlag, String shortFlag) {
		return parsePref(fullFlag, shortFlag) != null;
	}

	public String handle(String flag) {
		return handle(flag, flag);
	}

	public String handle(String fullFlag, String shortFlag) {
		List<String> parsed = parsePref(fullFlag, shortFlag);
		if (parsed != null) {
			if (parsed.size() >= 1) {
				return parsed.get(0);
			}
		}
		return defaultValue;
	}

	public List<String> parsePref(String flag) {
		return parsePref(flag, flag);//Whats the worst that could happen.. :D
	}

	public List<String> parsePref(String fullFlag, String shortFlag) {
		List<String> result = null;
		for (String s : inputs) {
			if (result == null) {//Have not found the flag yet.
				//Check to see if this is the flag.
				if (s.equals("--" + fullFlag) || s.equals("-" + shortFlag)) {
					result = new ArrayList<String>();//Sigifies flag was found
				}
			} else {//We have the flag, add following words until we hit "-"
				if (isFlag(s)) {
					break;//That must be a different flag
				} else {
					result.add(s);
				}
			}
		}
		return result;
	}
}
