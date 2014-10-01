package edu.dynamic.dynamiz.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CommandType {
	ADD("put") {
	}, DELETE("del", "remove") {
	}, UPDATE("upd", "modify", "mod", "edit", "change") {
	};
	
	static private final Map<String, CommandType> ALIAS_TABLE = new HashMap<String, CommandType>();
	static {
		for (CommandType cmd: CommandType.values()) {
			// Normalising by lowercase all
			for (String alias: cmd.aliases) {
				ALIAS_TABLE.put(alias.toLowerCase(), cmd);
			}
		}
	}
	
	static public CommandType fromString(String value) {
		if (value == null) {
			throw new NullPointerException("Null alias");
		}

		// Normalising input
		CommandType opt = ALIAS_TABLE.get(value.toLowerCase());

		if (opt == null) {
			throw new IllegalArgumentException("Not an alias: " + value);
		}
		
		return opt;
	}
	
	static public List<String> getAliases(CommandType cmd) {
		return cmd.aliases;
	}
	
	private List<String> aliases;
	private CommandType(String... aliases) {
		this.aliases = new ArrayList<String>(Arrays.asList(aliases));
		this.aliases.add(this.toString());
	}
}
