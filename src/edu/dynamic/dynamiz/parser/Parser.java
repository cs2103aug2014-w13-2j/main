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
	public MyDate parseDate(String dateStr) {	
		assert dateStr != null;
		
		String dateStrUS = changeDateFormatUKToUS(dateStr);
		
		com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
		List<DateGroup> groups = nattyParser.parse(dateStrUS);
		if (!groups.isEmpty()) {
			DateGroup group = groups.get(0);
			Date date = group.getDates().get(0);
			if (group.isTimeInferred()) {
				return new MyDate(date);
			} else {
				return new MyDateTime(date);
			}
		} else {
			LoggerParser.warning("Invalid date string: " + dateStr);
			return null;
		}	
	}	

	/**
	 * Change date format from dd/mm/yy(yy) to mm/dd/yy(yy) format for natty parsing
	 * 
	 * @param dateStr date string in format dd/mm/yy(yy)
	 * @return date string in format mm/dd/yy(yy)
	 */
	public String changeDateFormatUKToUS(String dateStr) {
		assert(dateStr != null);
		
		Pattern datePattern = Pattern.compile(REGEX_DATE);
		Matcher dateMatcher = datePattern.matcher(dateStr);
		StringBuffer sb = new StringBuffer(dateStr.length());
		while(dateMatcher.find()) {
			String newDate = String.format("%1$s/%2$s/%3$s", dateMatcher.group(2), 
															 dateMatcher.group(1), 
														 	 dateMatcher.group(3));
			
			dateMatcher.appendReplacement(sb, Matcher.quoteReplacement(newDate));
		}
		
		dateStr = dateMatcher.appendTail(sb).toString();
		
		return dateStr;
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
		
		// Parse orderby
		String orderRegex = String.format("(.*)(%1$s)(.*$)", OptionType.ORDER_BY.getAliasesRegex());
		Pattern orderPattern = Pattern.compile(orderRegex, Pattern.CASE_INSENSITIVE);
		Matcher orderMatcher = orderPattern.matcher(inputCmd);
		
		if (orderMatcher.find()) {
			inputCmd = orderMatcher.group(1);
			String opt = orderMatcher.group(2);
			String[] values = orderMatcher.group(3).split("" + Option.DEFAULT_DELIMITER);
			
			List<String> newValues = Util.removeEmptyStringsInArray(values);
			
			Option option = new Option(opt, newValues);
			options.add(option);
		}
		
		String allAliases = OptionType.getAllAliasesRegex();
		
		String optRegex = "(?<=(" + allAliases + "))" + // Lookahead for option keyword
						  "(.*?)" + 					// Arguments sandwiched between 2 keywords or 1 keywords and end of line
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
		com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
		List<DateGroup> groups = parser.parse("the day before next thursday at noon");
		for(DateGroup group: groups) {
		  List dates = group.getDates();
		  int line = group.getLine();
		  int column = group.getPosition();
		  String matchingValue = group.getText();
		  System.out.println(group.isTimeInferred());
		  String syntaxTree = group.getSyntaxTree().toStringTree();
		  System.out.println(syntaxTree);
		  Date recursUntil = group.getRecursUntil();
		}
		
	}
}
