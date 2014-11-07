//@author A0113855E
package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OptionType enum class is a class to hold the various options applicable to many commands.
 * Using OptionType allows the users to specify the PRIORITY, START_TIME, END_TIME, or
 * ORDER_BY options for each items.
 * 
 * This enum class mainly facilitates the functionalities of Command classes as well as help in
 * Parsing of the command.
 *
 */
public enum OptionType {
	PRIORITY("-p") {
		@Override
		public String getParsingRegex() {
			String format = Util.addEscapeCapacityToRegex(SANDWICH_FORMAT);
			return String.format(format, getAliasesRegex(), allAliasesRegex);
		}
	}, START_TIME("-s", "starttime", "from", "startdate") {
		@Override
		public String getParsingRegex() {
			String format = Util.addEscapeCapacityToRegex(SANDWICH_FORMAT);
			return String.format(format, getAliasesRegex(), allAliasesRegex);
		}
	}, END_TIME("-d", "-e", "deadline", "endtime", "enddate", "to", "by", "due") {
		@Override
		String getParsingRegex() {
			String format = Util.addEscapeCapacityToRegex(SANDWICH_FORMAT);
			return String.format(format, getAliasesRegex(), allAliasesRegex);
		}
	}, ON_TIME("on") {
		@Override
		String getParsingRegex() {
			String format = Util.addEscapeCapacityToRegex(SANDWICH_FORMAT);
			return String.format(format, getAliasesRegex(), allAliasesRegex);
		}
	}, ORDER_BY("-o", "orderby", "sortby") {
		@Override
		String getParsingRegex() {
			String format = Util.addEscapeCapacityToRegex(RIGHT_END_FORMAT);
			return String.format(format, getAliasesRegex());
		}
	}, STATUS("stat", "st") {
		@Override
		String getParsingRegex() {
			String format = Util.addEscapeCapacityToRegex(SANDWICH_FORMAT);
			return String.format(format, getAliasesRegex(), allAliasesRegex);
		}
	};
	
	
	private static final String SANDWICH_FORMAT = "(%1$s)" + // Lookahead for targeted option keyword.
			  									  "(.*?)" +       // Arguments sandwiched between 2 keywords or 1 keywords and end of line
			  									  "(?=%2$s|$)"; // Another keyword or end of line to mark the end
	private static final String RIGHT_END_FORMAT = "(%1$s)(.*)$";
	
	private static final Map<String, OptionType> ALIAS_TABLE = new HashMap<String, OptionType>();
	private static final Map<String, Integer> PRIORITY_TABLE = new HashMap<String, Integer>();
	private static final Map<String, String> STATUS_TABLE = new HashMap<String, String>();
	
	private static String allAliasesRegex = "";
	
	public static final int PRIORITY_URGENT = 8;
	public static final int PRIORITY_HIGH = 4;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_LOW = 1;
	public static final int PRIORITY_NONE = 0;
	public static final int PRIORITY_UNCHANGED = -1;	
	public static final int PRIORITY_INVALID = -2;
	
	public static final String STATUS_PENDING = "pending";
	public static final String STATUS_COMPLETED = "completed";
	public static final String STATUS_INVALID = "invalid";
	
	public static final String OPTION_SIGNAL_CHARACTER = "-";
	
	/** This character is added to escape an OptionType keyword from being parsed*/
	public static final String OPTION_ESCAPE_CHARACTER = ";";
	
	static {
		StringBuffer allAliases = new StringBuffer();
		
		for (OptionType opt: OptionType.values()) {
			// Normalising by lowercase all
			for (String alias: opt.aliases) {
				ALIAS_TABLE.put(alias.toLowerCase(), opt);
			}
			allAliases.append(opt.aliasesRegex);
			allAliases.append("|");
		}
		
		// Remove the last | character
		allAliasesRegex = allAliases.substring(0, allAliases.length() - 1);
		
		// Initialise priority table
		PRIORITY_TABLE.put("u", PRIORITY_URGENT);
		PRIORITY_TABLE.put("urg", PRIORITY_URGENT);
		PRIORITY_TABLE.put("urgent", PRIORITY_URGENT);
		
		PRIORITY_TABLE.put("h", PRIORITY_HIGH);
		PRIORITY_TABLE.put("hig", PRIORITY_HIGH);
		PRIORITY_TABLE.put("high", PRIORITY_HIGH);
		
		PRIORITY_TABLE.put("m", PRIORITY_MEDIUM);
		PRIORITY_TABLE.put("med", PRIORITY_MEDIUM);
		PRIORITY_TABLE.put("medium", PRIORITY_MEDIUM);
		
		PRIORITY_TABLE.put("l", PRIORITY_LOW);
		PRIORITY_TABLE.put("low", PRIORITY_LOW);
		
		PRIORITY_TABLE.put("n", PRIORITY_NONE);
		PRIORITY_TABLE.put("non", PRIORITY_NONE);
		PRIORITY_TABLE.put("none", PRIORITY_NONE);
		
		PRIORITY_TABLE.put("unchanged", PRIORITY_UNCHANGED);
		
		// Initialise status table
		STATUS_TABLE.put("pending", STATUS_PENDING);
		STATUS_TABLE.put("pend", STATUS_PENDING);
		STATUS_TABLE.put("p", STATUS_PENDING);
		
		STATUS_TABLE.put("completed", STATUS_COMPLETED);
		STATUS_TABLE.put("complete", STATUS_COMPLETED);
		STATUS_TABLE.put("comp", STATUS_COMPLETED);
		STATUS_TABLE.put("c", STATUS_COMPLETED);
		
		
	}
	
	/**
	 * Retrieve the OptionType instance given the string of one of the option
	 * alias
	 * 
	 * @param value the String identification of the option
	 * @return OptionType instance corresponding to the string value
	 */
	public static OptionType fromString(String value) {
		if (value == null) {
			throw new NullPointerException("Null alias");
		}

		// Normalising input
		OptionType opt = ALIAS_TABLE.get(value.toLowerCase().trim());

		if (opt == null) {
			throw new IllegalArgumentException("Not an alias: " + value);
		}
		
		return opt;
	}
	
	/**
	 * Retrieve the OptionType instance given a parsed Option.
	 * 
	 * @param opt the Option to get the OptionType from
	 * @return the OptionType instance indicating the Option given
	 */
	public static OptionType fromOption(Option opt) {
		if (opt == null) {
			throw new NullPointerException("Null option");
		}
		
		return opt.getOptionType();
	}
	
	/**
	 * To check if the String priority given is an alias of any of the
	 * existing PRIORITY levels
	 * 
	 * @param priority the string containing the priority level
	 * @return true if it is a valid alias. False otherwise
	 */
	public static boolean isValidPriority(String priority) {
		return PRIORITY_TABLE.containsKey(priority.toLowerCase());
	}
	
	/**
	 * Retrieve the numerical value of the Priority from the given string
	 * 
	 * @param priority the string containing the priority level
	 * @return the integer value of the priority given in the string.
	 */
	public static int getPriority(String priority) {
		priority = priority.toLowerCase();
		if (isValidPriority(priority)) {
			return PRIORITY_TABLE.get(priority); 
		}
		
		return PRIORITY_INVALID;
	}
	
	/**
	 * To check if the String status given is an alias of any of the
	 * existing Status states
	 * 
	 * @param status the string containing the status state
	 * @return true if it is a valid alias. False otherwise
	 */
	public static boolean isValidStatus(String status) {
		return STATUS_TABLE.containsKey(status.toLowerCase());
	}
	
	/**
	 * Retrieve the String value of the Status from the given string
	 * 
	 * @param status the string containing the status state
	 * @return the string value of the status given in the string.
	 */
	public static String getStatus(String status) {
		status = status.toLowerCase();
		if (isValidStatus(status)) {
			return STATUS_TABLE.get(status);
		}
		
		return STATUS_INVALID;
	}
	
	/**
	 * Retrieve a List<String> of all the aliases of all the OptionType
	 * 
	 * @return a List of String of all the string representation of  the OptionType
	 */
	public static List<String> getAllAliases() {
		List<String> allAliases = new ArrayList<String>();
		for (OptionType opts: OptionType.values()) {
			allAliases.addAll(opts.aliases);
		}
		
		return allAliases;
	}
	
	public static String getAllAliasesRegex() {
		return allAliasesRegex;
	}
	
	private List<String> aliases;
	private String aliasesRegex;
	private OptionType(String... aliases) {
		this.aliases = new ArrayList<String>(Arrays.asList(aliases));
		this.aliases.add(this.toString().toLowerCase());
		
		StringBuffer regEx = new StringBuffer();
		for (String alias: this.aliases) {
			if (alias.charAt(0) == OPTION_SIGNAL_CHARACTER.charAt(0)) {
				String wordRegex = "[\\w" + OPTION_SIGNAL_CHARACTER + "]";
				regEx.append(String.format("(?<!%1$s)%2$s\\b|", wordRegex, alias));
			} else {
				regEx.append(String.format("\\b%1$s\\b|", alias));
			}
		}
		aliasesRegex = regEx.substring(0, regEx.length() - 1);
	}
	
	public String getAliasesRegex() {
		return aliasesRegex;
	}
	
	abstract String getParsingRegex();

}
