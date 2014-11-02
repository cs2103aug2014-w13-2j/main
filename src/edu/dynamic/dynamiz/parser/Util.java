package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;

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
	private static final String ESCAPE_CHARACTER = ";";
	private static final String POSITIVE_NUMBER_RANGE_REGEX = "(\\d+)\\s*(-)\\s*(\\d+)";
	private static final int START_NUMBER_GROUP = 1;
	private static final int END_NUMBER_GROUP = 3;
	
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
		
		String[] words = str.trim().split(regEx);
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
		return str.trim().matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	 * Check if a given string has an integer format
	 * @param str the string to check for format 
	 * @return true if the string has an integer format and false otherwise
	 */
	public static boolean isInteger(String str) {
		return str.trim().matches("-?\\d+");
	}
	
	/**
	 * Check if a given string contains a valid positive integer number range
	 * A valid positive integer number range consists of NUMBER_FROM - NUMBER_TO
	 * where NUMBER_FROM <= NUMBER_TO
	 * 
	 * @param range the string to check the number range format from
	 * @return true if the given string satisfies the number range format. False otherwise.
	 */
	public static boolean isValidNumberRange(String range) {
		Pattern rangePat = Pattern.compile(POSITIVE_NUMBER_RANGE_REGEX);
		Matcher rangeMat = rangePat.matcher(range.trim());
		if (rangeMat.matches()) {
			int startNum = Integer.parseInt(rangeMat.group(START_NUMBER_GROUP));
			int endNum = Integer.parseInt(rangeMat.group(END_NUMBER_GROUP));
			
			return (startNum <= endNum);
		}
		
		return false;
	}
	
	/**
	 * Retrieve a List<Integer> of a given string containing a valid positive integer number
	 * range
	 * 
	 * @param range the string to get the number list from
	 * @return a List<Integer> of the number from the range inclusively. Empty if the 
	 * string matches format but not valid. Null otherwise.
	 */
	public static List<Integer> getNumberListFromRange(String range) {
		Pattern rangePat = Pattern.compile(POSITIVE_NUMBER_RANGE_REGEX);
		Matcher rangeMat = rangePat.matcher(range.trim());
		if (rangeMat.matches()) {
			int startNum = Integer.parseInt(rangeMat.group(START_NUMBER_GROUP));
			int endNum = Integer.parseInt(rangeMat.group(END_NUMBER_GROUP));
			
			List<Integer> numList = new ArrayList<Integer>();
			for (int i = startNum; i <= endNum; i++) {
				numList.add(i);
			}
			
			return numList;
		} else {
			return null;
		}
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
	
	/**
	 * Convert a List<Integer> into the primitive type array
	 * 
	 * @param intList the List<Integer> to convert to
	 * @return a converted array of int primitive type
	 */
	public static int[] toIntArray(List<Integer> intList) {
		assert intList != null;
		if (intList == null) {
			return null;
		}
		
		int[] ints = new int[intList.size()];
		for (int i = 0; i < intList.size(); i++) {
			ints[i] = intList.get(i);
		}
		
		return ints;
	}
	
	/**
	 * A simple dispatcher to convert a date string representation into respective {@link MyDate} object
	 * If the string matches {@link MyDateTime.REGEX_DATETIME} then a MyDateTime object will be returned.
	 * If the string matches {@link MyDate.REGEX_DATE} then a MyDate object will be returned.
	 * A null object will be returned of neither of the above matches.
	 * 
	 * @param dateStr a string representation of the date
	 * @return corresponding MyDate object if applicable. Otherwise return null
	 */
	public static MyDate convertStringToMyDate(String dateStr) {
		if (dateStr.matches(MyDateTime.REGEX_DATETIME)) {
			return MyDateTime.makeDateTime(dateStr);
		} else if (dateStr.matches(MyDateTime.REGEX_DATE)) {
			return MyDate.makeDate(dateStr);
		} else {
			return null;
		}
	}
}
