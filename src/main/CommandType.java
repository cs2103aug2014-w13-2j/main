/**
 * 
 */
package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nhan
 *
 */
public enum CommandType {
	ADD("put") {
	}, DELETE("del") {
	}, DISPLAY("disp", "show") {
	}, SEARCH("find");
	
	static private final Map<String, CommandType> ALIAS_TABLE = new HashMap<String, CommandType>();
	static {
		for (CommandType cmd: CommandType.values()) {
			// Normalising by lowercase all
			ALIAS_TABLE.put(cmd.toString().toLowerCase(), cmd);
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
		CommandType cmd = ALIAS_TABLE.get(value.toLowerCase());

		if (cmd == null) {
			throw new IllegalArgumentException("Not an alias: " + value);
		}
		
		return cmd;
	}
	
	static public CommandType fromOption(Option cmd) {
		if (cmd == null) {
			throw new NullPointerException("Null command");
		}
		
		return fromString(cmd.getOptName());
	}
	
	private List<String> aliases;
	private CommandType(String... aliases) {
		this.aliases = Arrays.asList(aliases);
	}
}
