package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
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
 * This is a Parser class that will parse the given input from the user into CommandLine object
 * which in turn into Command object of suitable type. It also provides the parsing of Date in explicit form
 * e.g. 13/12/2014 or implicit form, e.g. next Thurs. 
 * 
 * @author nhan
 *
 */
public class Parser {
	private static final String REGEX_DATE = "\\b(\\d{1,2})\\D*(\\d{1,2})\\D*(\\d{2}|\\d{4})\\b";
	
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
	 * Retrieve a MyDate object of 
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
			LoggerParser.warning("Unrecognised date string: " + dateStr);
			return null;
		}	
	}
	
	public List<String> parseDateList(List<String> unparsedList) {
		List<String> parsedList = new ArrayList<String>();
		
		for (String dateStr: unparsedList) {
			MyDate date = parseDate(dateStr);
			if (date != null) {
				parsedList.add(date.toString());
			}
		}
		
		return parsedList;
	}	

	public List<String> parsePriorityList(List<String> unparsedList) {
		List<String> parsedList = new ArrayList<String>();
		for (String priority: unparsedList) {
			// TODO: Implement checking of valid Priority String
			if (true) {//if (OptionType.PRIORITY.isValidString(priority)) {
				parsedList.add(priority);
			}
		}
		
		return parsedList;
	}

	/**
	 * Parse the input with the given regular expression based on the OptionType given.
	 * The general format of the Pattern is as followed:
	 * - Group 0: part to remove if there is a matching
	 * - Group 1: matching keyword of OptionType
	 * - Group 2: argument associating matching option type
	 * 
	 * @param input The unparsed input
	 * @param type OptionType to match the Option in return
	 * @return an Option that is correctly parse or a null if there is no matching.
	 */
	public Option parseOptionAndExtract(StringBuffer input, OptionType type) {
		Option option = null;
		String regEx = type.getParsingRegex();
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input.toString());

		while (matcher.find()) {
			String argument = matcher.group(2);
			String[] values = argument.split("" + Option.DEFAULT_DELIMITER);

			// Sanitise values
			List<String> valueList = Util.removeEmptyStringsInArray(values);
			
			switch (type) {
				case START_TIME : // Fall through
				case END_TIME :   // Fall through
				case ON_TIME : 
					List<String> dateList = parseDateList(valueList);
					if (!dateList.isEmpty()) {
						option = new Option(type, dateList);
						input.replace(matcher.start(), matcher.end(), "");
					}
					break;
				case PRIORITY :
					List<String> priorityList = parsePriorityList(valueList);
					if (!priorityList.isEmpty()) {
						option = new Option(type, priorityList);
						input.replace(matcher.start(), matcher.end(), "");
					}
					break;
				case ORDER_BY :
					option = new Option(type, valueList);
					input.replace(matcher.start(), matcher.end(), "");
					break;
				default: throw new IllegalArgumentException("Invalid OptionType is given");
			}
		}
		
		return option;
	}

	/**
	 * Change date format from dd/mm/yy(yy) to mm/dd/yy(yy) format for natty parsing
	 * 
	 * @param dateStr date string in format dd/mm/yy(yy)
	 * @return date string in format mm/dd/yy(yy)
	 */
	public String changeDateFormatUKToUS(String dateStr) {
		assert(dateStr != null);
		
		return dateStr.replaceAll(REGEX_DATE, "$2/$1/$3");
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
		
		StringBuffer inputCmdBuffer = new StringBuffer(inputCmd);
	
		for (OptionType optType: cmdType.getApplicableOptions()) {
			Option option = parseOptionAndExtract(inputCmdBuffer, optType);
			if (option != null) {
				options.add(option);
			}
		}
		
		String param = inputCmdBuffer.toString().trim();
		CommandLine cmdLine = new CommandLine(cmdType, options, param);
		return cmdLine;
	}
	
	public static void main(String[] args) {
		Parser parser = Parser.getInstance();
		
	}
}
