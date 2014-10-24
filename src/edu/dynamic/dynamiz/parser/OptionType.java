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
	}, START_TIME("-s", "starttime", "from", "on") {
	}, END_TIME("-d", "deadline", "endtime", "to", "by") {
	}, ORDER_BY("-o", "orderby", "sortby");
	
	private static final Map<String, OptionType> ALIAS_TABLE = new HashMap<String, OptionType>();
	
	private static String ALL_ALIASES = "";
	
	public static int PRIORITY_URGENT = 8;
	public static int PRIORITY_HIGH = 4;
	public static int PRIORITY_MEDIUM = 2;
	public static int PRIORITY_LOW = 1;
	public static int PRIORITY_NONE = 0;
	public static int PRIORITY_UNCHANGED = -1;	
	
	private static final char OPTION_SIGNAL_CHARACTER = '-';
	static {
		StringBuffer allAliases = new StringBuffer();
		String toBeAppended = ""; 
		
		for (OptionType opt: OptionType.values()) {
			// Normalising by lowercase all
			for (String alias: opt.aliases) {
				ALIAS_TABLE.put(alias.toLowerCase(), opt);
				
				if (alias.charAt(0) == OPTION_SIGNAL_CHARACTER) {
					String wordRegex = "[\\w" + OPTION_SIGNAL_CHARACTER + "]";
					toBeAppended = String.format("(?<!%1$s)(?=%1$s)%2$s\\b|", wordRegex, alias);  
				} else {
					toBeAppended = "\\b" + alias + "\\b" + "|";
				}
				allAliases.append(toBeAppended);
			}
		}
		
		// Remove the last | character
		ALL_ALIASES = allAliases.substring(0, allAliases.length() - 1);
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
		
		return fromString(opt.getOptName());
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
		return ALL_ALIASES;
	}
	
	private List<String> aliases;
	private OptionType(String... aliases) {
		this.aliases = new ArrayList<String>(Arrays.asList(aliases));
		this.aliases.add(this.toString().toLowerCase());
	}
	
	public String getAliasesRegex() {
		StringBuffer regEx = new StringBuffer();
		for (String alias: aliases) {
			if (alias.charAt(0) == OPTION_SIGNAL_CHARACTER) {
				String wordRegex = "[\\w" + OPTION_SIGNAL_CHARACTER + "]";
				regEx.append(String.format("(?<!%1$s)(?=%1$s)%2$s\\b|", wordRegex, alias));  
			} else {
				regEx.append("\\b" + alias + "\\b" + "|");
			}
		}
		return regEx.substring(0, regEx.length() - 1);
	}
}
