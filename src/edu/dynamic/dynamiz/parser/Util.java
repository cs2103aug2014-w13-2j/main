package edu.dynamic.dynamiz.parser;

/**
 * Util class to store some utilities function in String manipulation
 * 
 * @author nhan
 * 
 *	Modelled after org.apache.commons.cli
 */
public final class Util {
	
	/**
	 * Remove the hyphens (single or double) from the given string and
	 * return a new String
	 * 
	 * @param str The string from which the hyphens should be removed.
	 * 
	 * @return the new stripped String.
	 */
	public static String stripLeadingHyphens(String str) {
		if (str == null) {
			return null;
		}
		
		if (str.startsWith("--")) {
			return str.substring(2, str.length()); 
		} else if (str.startsWith("-")) {
			return str.substring(1, str.length());
		} else {
			// Do nothing here
		}
		
		return str;
	}
	
	/**
	 * Remove the leading and ending quotes from the string and 
	 * return a new String
	 * 
	 * For example, '"Hello World"' should returns 'Hello World'
	 * 
	 * @param str The string from which the quotes should be removed.
	 * 
	 * @return the string without leading and trailing quotes.
	 */
	public static String stripLeadingAndTrailingQuotes(String str) {
		int length = str.length();
		
		boolean hasSufficientLength = str.length() > 1;
		boolean hasLeadingAndTrailingQuotes = str.startsWith("\"") && 
											  str.endsWith("\"");
		// Index -1 means not found.
		boolean hasNoMiddleQuotes = str.substring(1, length - 1).indexOf("\"") == -1;
		
		if (hasSufficientLength && hasLeadingAndTrailingQuotes && hasNoMiddleQuotes) {
			str = str.substring(1, length - 1);
		}
		
		return str;
	}
}
