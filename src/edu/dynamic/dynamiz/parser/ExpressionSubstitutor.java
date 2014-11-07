//@author A0113855E
package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a singleton class to substitute abbreviations of commonly used expressions
 * to full length expressions. The reasons to implement this in a separate class 
 * instead of merging with Util are:
 * 	- This serves a different purpose than the general purpose Util class
 *  - This is open for extension in which the user is able to define their own abbreviation
 *  - Organising purposes.
 *  
 */

public class ExpressionSubstitutor {
	private static ExpressionSubstitutor substitutor;
	private HashMap<String, String> dictionary;

	/**
	 * Singleton construction.
	 * For the current implementation, the expressions are hard-coded.
	 * User-defined abbr will be retrieved from files.
	 * These are considered as default values.
	 */
	private ExpressionSubstitutor() {
		dictionary = new HashMap<String, String>();
		
		dictionary.put("tmr", "tomorrow");
		dictionary.put("2mr", "tomorrow");
		dictionary.put("tmw", "tomorrow");
		
		dictionary.put("ytd", "yesterday");
		dictionary.put("yst", "yesterday");
		
		dictionary.put("td", "today");
		dictionary.put("t", "today");
		
		dictionary.put("nxt", "next");
		dictionary.put("nt", "next");

		dictionary.put("lt", "last");
		dictionary.put("lst", "last");
	}
	
	/**
	 * Retrieve an instance of ExpressionSubstitutor to utilise its functionality
	 * @return
	 */
	public static ExpressionSubstitutor getInstance() {
		if (substitutor == null) {
			substitutor = new ExpressionSubstitutor();
		}
		
		return substitutor;
	}
	
	/**
	 * Retrieve a new String after substituted with the values existing in the 
	 * look-up table
	 * 
	 * @param input The string in which abbrs are to be found and substituted (if any)
	 * @return a new string whereby abbrs are substituted.
	 */
	public String subCommonDateAbbreviation(String input) {
		assert input != null;
		
		Pattern pat = null;
		Matcher mat = null;
		for (String abbr: dictionary.keySet()) {
			pat = Pattern.compile(makeWordRegEx(abbr), Pattern.CASE_INSENSITIVE);
			mat = pat.matcher(input);
			input = mat.replaceAll(dictionary.get(abbr));
		}
		
		return input;
	}
	
	/**
	 * Retrieve a List<String> after substituted with the values existing in the
	 * look-up table
	 * 
	 * @param inputList the List<String> input in which abbrs are to be found and
	 * substituted (if any)
	 * @return a new List<String> whereby arrbs are substituted.
	 */
	public List<String> subCommonDateAbbrList(List<String> inputList) {
		assert inputList != null;
		List<String> outputList = new ArrayList<String>();
		
		for (String s: inputList) {
			outputList.add(subCommonDateAbbreviation(s));
		}
		
		return outputList;
	}
	
	/**
	 * Retrieve a RegEx expression of a plain input.
	 * 
	 * @param input the word to be transformed into RegEx to be used by this class
	 * @return a new RegEx that allows the ability to ignore escaped words.
	 */
	private String makeWordRegEx(String input) {
		return Util.addEscapeCapacityToRegex(String.format("\\b(%1$s)\\b", input));
	}
	
}
