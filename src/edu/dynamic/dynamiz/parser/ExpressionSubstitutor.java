package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionSubstitutor {
	private static ExpressionSubstitutor substitutor;
	private HashMap<String, String> dictionary;
	
	private ExpressionSubstitutor() {
		// TODO: Initialise substitution dictionaries
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
	
	public static ExpressionSubstitutor getInstance() {
		if (substitutor == null) {
			substitutor = new ExpressionSubstitutor();
		}
		
		return substitutor;
	}
	
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
	
	public List<String> subCommonDateAbbrList(List<String> inputList) {
		assert inputList != null;
		List<String> outputList = new ArrayList<String>();
		
		for (String s: inputList) {
			outputList.add(subCommonDateAbbreviation(s));
		}
		
		return outputList;
	}
	
	public String makeWordRegEx(String input) {
		return Util.addEscapeCapacityToRegex(String.format("\\b(%1$s)\\b", input));
	}
	
	public static void main(String[] args) {
		String s = "TD td Td tD";
		ExpressionSubstitutor sub = ExpressionSubstitutor.getInstance();
		System.out.println(sub.subCommonDateAbbreviation(s));
	}
}
