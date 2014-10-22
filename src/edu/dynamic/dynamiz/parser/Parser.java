package edu.dynamic.dynamiz.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;

/**
 * This is the boss right here
 * 
 * @author nhan
 *
 */
public class Parser {
	private static final String REGEX_DATE = "\\b(\\d{1,2})\\D*(\\d{1,2})\\D*(\\d{2}|\\d{4})\\b";
	private static final String REGEX_DATETIME = "\\b(\\d{1,2})\\D*(\\d{1,2})\\D*(\\d{2}|\\d{4})\\s+(\\d{1,2})\\D*(\\d{1,2})\\b";
	
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
	public MyDate parseDate(String date) {	
		assert(date != null);
		Pattern datePattern = Pattern.compile(REGEX_DATE);
		Pattern dateTimePattern = Pattern.compile(REGEX_DATETIME);
		
		Matcher dateMatcher = datePattern.matcher(date);
		Matcher dateTimeMatcher = dateTimePattern.matcher(date);
		
		MyDate parsedDate = null;
		if (dateTimeMatcher.find()) {
			int dd = Integer.parseInt(dateTimeMatcher.group(1));
			int mm = Integer.parseInt(dateTimeMatcher.group(2));
			int yy = Integer.parseInt(dateTimeMatcher.group(3));
			int hr = Integer.parseInt(dateTimeMatcher.group(4));
			int mn = Integer.parseInt(dateTimeMatcher.group(5));

			parsedDate = new MyDateTime(dd, mm, yy, hr, mn);
		} else if (dateMatcher.find()) {
			int dd = Integer.parseInt(dateMatcher.group(1));
			int mm = Integer.parseInt(dateMatcher.group(2));
			int yy = Integer.parseInt(dateMatcher.group(3));

			parsedDate = new MyDate(dd, mm, yy);
		} else {
			parsedDate = parseImplicitDate(date);
			if (parsedDate == null) {
				LoggerParser.severe("Invalid date string format:" + date);
			}
		}
		
		return parsedDate;
	}
	
	private MyDate parseImplicitDate(String dateStr) {
		assert(dateStr != null);
		
		com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
		List<DateGroup> groups = nattyParser.parse(dateStr);
		if (!groups.isEmpty()) {
			DateGroup group = groups.get(0);
			Date date = group.getDates().get(0);
			
			return new MyDateTime(date);
		} else {
			return null;
		}
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
		
		String allAliases = OptionType.getAllAliasesRegex();
		
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
		String dateStr = "from Tuesday 2pm to Wednesday 4pm";
		com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
		List<DateGroup> groups = nattyParser.parse(dateStr);
		
		for (DateGroup group: groups) {
			List<Date> dates = group.getDates();
			for (Date date: dates) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				System.out.println(cal.get(Calendar.DAY_OF_MONTH));
			}
		}
	}
}
