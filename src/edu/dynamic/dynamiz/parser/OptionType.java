package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OptionType to help with aliases for Option
 * 
 * @author nhan
 *
 */
public enum OptionType {
	PRIORITY("-p", "--priority", "priority") {
		private final int noOfArgs = ARGS_UNLIMITED;
		
		@Override
		public int getNoOfArgs() {
			return noOfArgs;
		}
	}, START_TIME("-s", "--starttime", "from") {
		private final int noOfArgs = ARGS_ONE;
		
		@Override
		public int getNoOfArgs() {
			return noOfArgs;
		}
	}, END_TIME("-d", "--deadline", "--endtime", "to", "by") {
		private final int noOfArgs = ARGS_ONE;
		
		@Override
		public int getNoOfArgs() {
			return noOfArgs;
		}
	};
	
	static private final Map<String, OptionType> ALIAS_TABLE = new HashMap<String, OptionType>();
	
	static {
		for (OptionType opt: OptionType.values()) {
			// Normalising by lowercase all
			for (String alias: opt.aliases) {
				ALIAS_TABLE.put(alias.toLowerCase(), opt);
			}
		}
	}
	
	static public OptionType fromString(String value) {
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
	
	static public OptionType fromOption(Option opt) {
		if (opt == null) {
			throw new NullPointerException("Null option");
		}
		
		return fromString(opt.getOptName());
	}
	
	public List<String> getAliases() {
		return this.aliases;
	}
	
	static public List<String> getAllAliases() {
		List<String> allAliases = new ArrayList<String>();
		for (OptionType opts: OptionType.values()) {
			allAliases.addAll(opts.aliases);
		}
		
		return allAliases;
	}

	
	/** constant that specifies if the number of required arguments has not been specified */
	public static final int ARGS_UNINITIALISED = -99;
	
	/** constant that specifies if the required number of arguments is 0 */
	public static final int ARGS_NONE = 0;
	
	/** constant that specifies if the required number of arguments is 1 */
	public static final int ARGS_ONE = 1;
	
	/** constant that specifies if the required number of arguments is 2 */
	public static final int ARGS_TWO = 2;
	
	/** constant that specifies if the number of arguments is at least 1 */
	public static final int ARGS_UNLIMITED = -2;
	
	/** constant that specifies if the number of arguments is none or many */
	public static final int ARGS_OPTIONAL = -1;
	
	/** constant that specifies the default character for delimiter of argument values */
	public static final char DEFAULT_DELIMITER = ',';
	
	
	private List<String> aliases;
	private OptionType(String... aliases) {
		this.aliases = new ArrayList<String>(Arrays.asList(aliases));
		this.aliases.add(this.toString().toLowerCase());
	}
	
	public abstract int getNoOfArgs();
}
