/**
 * 
 */
package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * CommandType includes the types for each command keyword.
 * 
 * There are currently 4 command types, i.e.
 * 	- Add: Adding an item to the storage
 *  - Delete: Delete an item in the storage
 *  - Display: Display the items in the storage
 *  - Search: Query for item/items matching certain filters.
 *  
 * This CommandType enum class is able to recognise aliases.
 * The current aliases for the CommandType now are:
 *  - Add: put,
 *  - Delete: del,
 *  - Display: disp, show,
 *  - Search: find, query,
 * @author nhan
 *
 */
public enum CommandType {
	ADD("put") {
	}, DELETE("del") {
	}, DISPLAY("disp", "show") {
	}, SEARCH("find", "query");
	
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
