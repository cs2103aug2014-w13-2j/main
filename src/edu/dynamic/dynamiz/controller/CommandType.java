package edu.dynamic.dynamiz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.dynamic.dynamiz.parser.OptionType;

/**
 * CommandType is the enumeration class of all the different types of commands.
 * 
 * This allows aliases of the same command type to be understood and returned as a particular
 * command type.
 * 
 * Also, this allows each command type to declare its applicable options.
 * 
 * For example, applicable options for ADD type of command is OptionType.START_TIME, OptionType.END_TIME,
 * and OptionType.PRIORITY.
 * 
 * @author nhan
 *
 */
public enum CommandType {
	ADD("a", "put") { 
		private final OptionType[] optionsArray = {OptionType.START_TIME, 
												   OptionType.END_TIME, 
												   OptionType.PRIORITY,
												   OptionType.ON_TIME};

		@Override
		public List<OptionType> getApplicableOptions() {
			// TODO Auto-generated method stub
			return Arrays.asList(optionsArray);
		}
		
	}, DELETE("d", "del", "remove") {
		private final OptionType[] optionsArray = {};

		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, UPDATE("u", "upd", "modify", "mod", "edit", "change") {
		private final OptionType[] optionsArray = {OptionType.START_TIME, 
												   OptionType.END_TIME, 
												   OptionType.PRIORITY,
												   OptionType.ON_TIME};

		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, LIST("l", "ls", "display", "show") {
		private final OptionType[] optionsArray = {OptionType.ORDER_BY,
												   OptionType.START_TIME,
												   OptionType.END_TIME,
												   OptionType.PRIORITY,
												   OptionType.ON_TIME};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, SEARCH("s", "find") {
		private final OptionType[] optionsArray = {OptionType.ORDER_BY, 
												   OptionType.START_TIME, 
												   OptionType.END_TIME, 
												   OptionType.PRIORITY,
												   OptionType.ON_TIME};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, UNDO("un", "ud") {
		private final OptionType[] optionsArray = {};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, REDO("re", "rd") {
		private final OptionType[] optionsArray = {};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, HELP("h", "man") {
		private final OptionType[] optionsArray = {};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, MARK("m", "done", "do", "tick") {
		private final OptionType[] optionsArray = {};

		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, UNMARK("um", "undone", "untick") {
		private final OptionType[] optionsArray = {};

		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	},  SHOW("sh") {
		private final OptionType[] optionsArray = {};

		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, EXIT("e", "quit", "bye", "!", "close") {
		private final OptionType[] optionsArray = {};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
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
		CommandType opt = ALIAS_TABLE.get(value.toLowerCase().trim());
		
		if (opt == null) {
			throw new IllegalArgumentException("Not a known alias for CommandType");
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
	
	/**
	 * Retrieve a list of applicable OptionType for the corresponding command type.
	 * 
	 * @return a List<OptionType> that contains the applicable OptionType. null if there is none.
	 */
	public abstract List<OptionType> getApplicableOptions();
}
