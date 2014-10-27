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
 * @author nhan
 *
 */
public enum OptionType {
	PRIORITY("-p", "priority") {
		@Override
		public String getParsingRegex() {
			return String.format(SANDWICH_FORMAT, getAliasesRegex(), allAliasesRegex);
		}
	}, START_TIME("-s", "starttime", "from") {
		@Override
		public String getParsingRegex() {
			return String.format(SANDWICH_FORMAT, getAliasesRegex(), allAliasesRegex);
		}
	}, END_TIME("-d", "deadline", "endtime", "to", "by") {
		@Override
		String getParsingRegex() {
			return String.format(SANDWICH_FORMAT, getAliasesRegex(), allAliasesRegex);
		}
	}, ON_TIME("on") {
		@Override
		String getParsingRegex() {
			return String.format(SANDWICH_FORMAT, getAliasesRegex(), allAliasesRegex);
		}
	}, ORDER_BY("-o", "orderby", "sortby") {
		@Override
		String getParsingRegex() {
			return String.format(RIGHT_END_FORMAT, getAliasesRegex());
		}
	};
	
	private static final String SANDWICH_FORMAT = "(%1$s)" + // Lookahead for targeted option keyword
			  									  "(.*?)" +       // Arguments sandwiched between 2 keywords or 1 keywords and end of line
			  									  "(?=%2$s|$)"; // Another keyword or end of line to mark the end
	private static final String RIGHT_END_FORMAT = "(%1$s)(.*)$";
	
	private static final Map<String, OptionType> ALIAS_TABLE = new HashMap<String, OptionType>();
	
	private static String allAliasesRegex = "";
	
	public static int PRIORITY_URGENT = 8;
	public static int PRIORITY_HIGH = 4;
	public static int PRIORITY_MEDIUM = 2;
	public static int PRIORITY_LOW = 1;
	public static int PRIORITY_NONE = 0;
	public static int PRIORITY_UNCHANGED = -1;	
	
	private static final char OPTION_SIGNAL_CHARACTER = '-';
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
		OptionType opt = ALIAS_TABLE.get(value.toLowerCase());

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
			if (alias.charAt(0) == OPTION_SIGNAL_CHARACTER) {
				String wordRegex = "[\\w" + OPTION_SIGNAL_CHARACTER + "]";
				regEx.append(String.format("(?<!%1$s)(?=%1$s)%2$s\\b|", wordRegex, alias));  
			} else {
				regEx.append("\\b" + alias + "\\b" + "|");
			}
		}
		aliasesRegex = regEx.substring(0, regEx.length() - 1);
	}
	
	public String getAliasesRegex() {
		return aliasesRegex;
	}
	
	abstract String getParsingRegex();

}
