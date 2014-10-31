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
 * <p>
 * The Parser will divide an input mainly into three groups:
 * 	1> Command keyword
 *  2> Parameter
 *  3> Options
 *  <p>
 * Firstly, the Parser will assume that the first word is always a Command keyword (group 1) and remove it from
 * the input string and check it against the known Command keyword. Then, the Parser will base on the parsed Command
 * keyword to continue parsing the correct structure corresponding to the {@link CommandType}.
 * <p>
 * Then, the Parser will attempt to parse the {@link OptionType} ORDER_BY and then extract if it is applicable to 
 * the parsed {@link CommandType} 
 * <p>
 * Next, the Parser will attempt to parse Option token by recognising a pair of [OptionType value]. The parser will
 * attempt to parse the value if it is suitable with the OptionType. If both match, then it is considered as a valid 
 * token and hence get extracted from the input string. 
 * <p>
 * The remaining will be assumed to be the parameter (group 2).
 * <p>
 * The Parser class follows the Singleton pattern.
 * 
 * @author nhan
 *
 */
public class Parser {
	/** A regular expression matching explicit date format*/
	private static final String REGEX_DATE = "\\b(\\d{1,2})[/-](\\d{1,2})[/-](\\d{2}|\\d{4})\\b";
	
	/** An attribute for Singleton pattern*/
	private static Parser parser = null;
	
	/** A logger instance for this class*/
	private final static Logger LoggerParser = Logger.getLogger(Parser.class.getName());
			
	/**
	 * Explicitly declare Constructor to be private to override the default Constructor
	 */
	private Parser() {
	}
	
	/**
	 * Retrieve the Singleton instance of Parser class
	 * @return
	 */
	public static Parser getInstance() {
		if (parser == null) {
			parser = new Parser();
		}
		
		return parser;
	}
	
	/**
	 * Retrieve a MyDate object parsed from a string. Using natty library as the main parsing 
	 * mechanism
	 * 
	 * @param dateStr the string that may contain a date information
	 * @return a parsed MyDate object if applicable, otherwise null
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
	
	/**
	 * Retrieve from a list of string a list of parsed dates wherever applicable
	 * 
	 * @param unparsedList the unparsed list of string
	 * @return a Date parsed list of string containing the date
	 */
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

	/**
	 * Retrieve from a list of string a list of parsed Priority wherever applicable
	 * 
	 * @param unparsedList the unparsed list of string
	 * @return a Priority-parsed list of string containing the priorities
	 */
	public List<String> parsePriorityList(List<String> unparsedList) {
		List<String> parsedList = new ArrayList<String>();
		for (String priority: unparsedList) {
			if (OptionType.isValidPriority(priority)) {
				parsedList.add("" + OptionType.getPriority(priority));
			}
		}
		
		return parsedList;
	}
	
	/**
	 * Retrieve from a list of string a list of parsed OptionType wherever applicable
	 * 
	 * @param unparsedList the unparsed list of string
	 * @return an OptionType-parsed list of string containing the OptionType for ordering
	 */
	public List<String> parseOrderingList(List<String> unparsedList) {
		List<String> parsedList = new ArrayList<String>();
		for (String ordering: unparsedList) {
			OptionType type = OptionType.fromString(ordering);
			if (type != null) {
				parsedList.add(type.name());
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
					List<String> orderingList = parseOrderingList(valueList);
					if (!orderingList.isEmpty()) {
						option = new Option(type, orderingList);
						input.replace(matcher.start(), matcher.end(), "");
					}
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
	
	/**
	 * Retrieve a Command object after parsing against the given input
	 * 
	 * @param inputCmd the unparsed string input given
	 * @return a parsed Command with appropriate fields
	 */
	public Command parse(String inputCmd) {
		CommandLine cmdLine = parseCommandLine(inputCmd);
		return cmdLine.getCommand();
	}
	
	/**
	 * Retrieve a CommandLine object after parsing against the given input
	 *
	 * The different between a CommandLine object and Command object is that
	 * the CommandLine object will simply contain the information regardless 
	 * of the applicability of that information to the command. Whereas, the 
	 * Command object will check for the applicability
	 *  
	 * @param inputCmd the unparsed string input given
	 * @return a parsed CommandLine with information (token) parsed
	 */
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
		
		String param = Util.escapeString(inputCmdBuffer.toString().trim());
		CommandLine cmdLine = new CommandLine(cmdType, options, param);
		return cmdLine;
	}
}
