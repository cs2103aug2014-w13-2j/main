package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Util class to store some utilities function in String manipulation
 * 
 * @author nhan
 * 
 *	Modelled after org.apache.commons.cli
 */
public final class Util {
	/** This is the default delimiter to split the string, i.e. Whitespace*/
	private static final String DEFAULT_DELIMITER = "\\s+";
	public static final String ESCAPE_CHARACTER = ";";
	
	public static List<String> removeEmptyStringsInList(List<String> list) {
		List<String> newList = new ArrayList<String>();
		for (String s: list) {
			if (!s.isEmpty()) {
				newList.add(s.trim());
			}
		}
		
		return newList;
	}
	
	public static List<String> removeEmptyStringsInArray(String[] array) {
		List<String> list = Arrays.asList(array);
		return removeEmptyStringsInList(list);
	}
	/**
	 * Retrieve the string with its first word stripped off. 
	 * Default delimiter is applied here.
	 * 
	 * @param str the string to strip the first word off
	 * @return the stripped string
	 */
	public static String stripFirstWord(String str) {
		return stripFirstWord(str, DEFAULT_DELIMITER);
	}
	
	/**
	 * Retrieve the string with its first word stripped off.
	 * regEx specifies the delimiter for the string
	 * 
	 * @param str the string to strip the first word off
	 * @param regEx the regular expression string to split the string
	 * @return the stripped string
	 */
	public static String stripFirstWord(String str, String regEx) {
		if (str == null) {
			return null;
		}
		
		String firstWord = getFirstWord(str, regEx);
		
		return str.replaceFirst(firstWord, "").trim();
	}
	
	/**
	 * Retrieve the first word of the string. 
	 * Default delimiter is applied when splitting the string
	 * 
	 * @param str the string to get the first word from
	 * @return the first word of the string
	 */
	public static String getFirstWord(String str) {
		return getFirstWord(str, DEFAULT_DELIMITER);
	}
	
	/**
	 * Retrieve the first word of the string
	 * regEx specifies the delimiter for the string
	 * 
	 * @param str the string to get the first word from
	 * @param regEx the regular expression string to split the string
	 * @return the first word of the string
	 */
	public static String getFirstWord(String str, String regEx) {
		if (str == null) {
			return null;
		}
		
		String[] words = str.split(regEx);
		return words[0];
	}
	
	
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
	
	/**
	 * Check if a given string has a number format
	 * @param str the string to check for format
 	 * @return true if the string has a number format and false otherwise
	 */
	public static boolean isNumber(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	 * Check if a given string has an integer format
	 * @param str the string to check for format 
	 * @return true if the string has an integer format and false otherwise
	 */
	public static boolean isInteger(String str) {
		return str.matches("-?\\d+");
	}
	
	/**
	 * This will remove the escape character from the given input string
	 * Helping in parsing of ambiguous cases
	 * 
	 * @param input the given input to escape from
	 * @return the escaped string.
	 */
	public static String escapeString(String input) {
		return input.replaceAll(ESCAPE_CHARACTER, "");
	}
}
