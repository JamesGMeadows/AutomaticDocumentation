package james.meadows.hri.util;

public class ParseUtil {
	
	public static String removeExcessSpaces(String string, boolean replaceSingleSpace) {
		if (replaceSingleSpace && string.length() > 2) {
			for (int i = 1; i < string.length()-2; i++) {
				Character c = string.charAt(i);
				if (!c.equals(' ')) {
					continue;
				}
				if (countSpaces(string.substring(i-1, i+2)) == 1 ) {
						string = string.substring(0,i) + "_" + string.substring(i + 1);
				}
			}
			
		}
		String s = string.replaceAll("[ \\t]+", ",");
		
		return s;
	}
	
	public static int countSpaces(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			if (c.equals(' ')) {
				count++;
			}
		}
		return count;
	}
	
	public static boolean ArrayContainsString(String[] array, String check) {
		
		for (String s : array) {
			if (s.equals(check))return true;
		}
		return false;
	}
}
