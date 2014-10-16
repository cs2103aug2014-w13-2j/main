package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.DateTime;

/**
 * This is the boss right here
 * 
 * @author nhan
 *
 */
public class Parser {
	private static final char OPTION_SIGNAL_CHARACTER = '-';
	private static final String REGEX_DATE = "\\b(\\d{1,2}).(\\d{1,2}).(\\d{2}|\\d{4})\\b";
	private static final String REGEX_DATETIME = "\\b(\\d{1,2}).(\\d{1,2}).(\\d{2}|\\d{4}) (\\d{1,2}):(\\d{1,2})\\b";
	
	private static Parser parser = null;
	private final static Logger LoggerParser = Logger.getLogger(Parser.class.getName());
			
	private Parser() {
		
	}
	
	public static Parser getInstance() {
		if (parser == null) {
			parser = new Parser();
		}
		
		return parser;
	}
	
	/**
	 * Return a Date object after parsing from a String
	 * 
	 * @param date
	 * @return
	 */
	public Date parseDate(String date) {	
		assert(date != null);
		
		Pattern datePattern = Pattern.compile(REGEX_DATE);
		Pattern dateTimePattern = Pattern.compile(REGEX_DATETIME);
		
		Matcher dateMatcher = datePattern.matcher(date);
		Matcher dateTimeMatcher = dateTimePattern.matcher(date);
		
		Date parsedDate = null;
		if (dateTimeMatcher.find()) {
			int dd = Integer.parseInt(dateTimeMatcher.group(1));
			int mm = Integer.parseInt(dateTimeMatcher.group(2));
			int yy = Integer.parseInt(dateTimeMatcher.group(3));
			int hr = Integer.parseInt(dateTimeMatcher.group(4));
			int mn = Integer.parseInt(dateTimeMatcher.group(5));

			parsedDate = new DateTime(dd, mm, yy, hr, mn);
		} else if (dateMatcher.find()) {
			int dd = Integer.parseInt(dateMatcher.group(1));
			int mm = Integer.parseInt(dateMatcher.group(2));
			int yy = Integer.parseInt(dateMatcher.group(3));

			parsedDate = new Date(dd, mm, yy);
		} else {
			parsedDate = parseImplicitDate(date);
			if (parsedDate == null) {
				LoggerParser.severe("Invalid date string format");
			}
		}
		
		return parsedDate;
	}
	
	private Date parseImplicitDate(String date) {
		assert(date != null);
		
		date = date.trim().toLowerCase();
		
		// TODO: Parsing possible natural languages
		
		switch(date) {
			case "tomorrow" :
			case "today" :
			case "":
				default: break;
		}
		
		return null;
	}
	
	public Command parse(String inputCmd) {
		CommandLine cmdLine = parseCommandLine(inputCmd);
		return cmdLine.getCommand();
	}
	
	public CommandLine parseCommandLine(String inputCmd) {
		assert(inputCmd != null);
		
		String commandWord = Util.getFirstWord(inputCmd);
		CommandType cmdType = CommandType.fromString(commandWord);
		
		inputCmd = Util.stripFirstWord(inputCmd);
		Options options = new Options();
		
		String allAliases = "";
		for (String alias: OptionType.getAllAliases()) {
			String toBeAppended = null;
			
			if (alias.charAt(0) == OPTION_SIGNAL_CHARACTER) {
				String wordRegex = "[\\w" + OPTION_SIGNAL_CHARACTER + "]";
				toBeAppended = String.format("(?<!%1$s)(?=%1$s)%2$s\\b|", wordRegex, alias);  
			} else {
				toBeAppended = "\\b" + alias + "\\b" + "|";
			}
			allAliases += toBeAppended;
		}
		
		allAliases = allAliases.substring(0, allAliases.length() - 1);
		
		String optRegex = "(?<=(" + allAliases + "))" + // Lookahead for option keyword
						  "(.*?)" + // Arguments sandwiched between 2 keywords or 1 keywords and end of line
						  "(?=(" + allAliases + "|$))"; // Another keyword or end of line
		Pattern optPattern = Pattern.compile(optRegex, Pattern.CASE_INSENSITIVE);
		
		String paramRegex = String.format("^(.*?)(?=(%1$s|$))", allAliases);
		Pattern paramPattern = Pattern.compile(paramRegex, Pattern.CASE_INSENSITIVE);
		
		Matcher optMatcher = optPattern.matcher(inputCmd);
		Matcher paramMatcher = paramPattern.matcher(inputCmd);
		
		String param = "";
		if (paramMatcher.find()) {
			param = paramMatcher.group(1).trim();
		}
		
		while(optMatcher.find()) {
			String opt = optMatcher.group(1);
			String[] values = optMatcher.group(2).split("" + Option.DEFAULT_DELIMITER);
			
			List<String> newValues = Util.removeEmptyStringsInArray(values);
			
			Option option = new Option(opt, newValues);
			options.add(option);
		}
		
		CommandLine cmdLine = new CommandLine(cmdType, options, param);
		return cmdLine;
	}
	
	public static void main(String[] args) {
		String date1 = "15/10/2014 13:00";
		Parser parser = Parser.getInstance();
		Date d = parser.parseDate(date1);
		System.out.println(d);
	}
}
