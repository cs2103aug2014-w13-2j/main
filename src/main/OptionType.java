package main;

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
	}, START_TIME("-s", "--starttime", "from") {
	}, END_TIME("-d", "--deadline", "--endtime", "to", "by") {
	};
	
	static private final Map<String, OptionType> ALIAS_TABLE = new HashMap<String, OptionType>();
	static {
		for (OptionType opt: OptionType.values()) {
			// Normalising by lowercase all
			ALIAS_TABLE.put(opt.toString().toLowerCase(), opt);
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
	
	private List<String> aliases;
	private OptionType(String... aliases) {
		this.aliases = Arrays.asList(aliases);
	}
}
