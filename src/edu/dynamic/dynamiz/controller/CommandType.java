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
	ADD("put") { 
		private final OptionType[] optionsArray = {OptionType.START_TIME, OptionType.END_TIME, OptionType.PRIORITY};

		@Override
		public List<OptionType> getApplicableOptions() {
			// TODO Auto-generated method stub
			return Arrays.asList(optionsArray);
		}
		
	}, DELETE("del", "remove") {
		private final OptionType[] optionsArray = {};

		@Override
		public List<OptionType> getApplicableOptions() {
			// TODO Auto-generated method stub
			return Arrays.asList(optionsArray);
		}
	}, UPDATE("upd", "modify", "mod", "edit", "change") {
		private final OptionType[] optionsArray = {OptionType.START_TIME, OptionType.END_TIME, OptionType.PRIORITY};

		@Override
		public List<OptionType> getApplicableOptions() {
			// TODO Auto-generated method stub
			return Arrays.asList(optionsArray);
		}
	}, LIST("display", "show") {
		private final OptionType[] optionsArray = {};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, SEARCH("find") {
		private final OptionType[] optionsArray = {OptionType.START_TIME, OptionType.END_TIME, OptionType.PRIORITY};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, UNDO("un") {
		private final OptionType[] optionsArray = {};
		
		@Override
		public List<OptionType> getApplicableOptions() {
			return Arrays.asList(optionsArray);
		}
	}, REDO("re") {
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
	
	/**
	 * Retrieve a list of applicable OptionType for the corresponding command type.
	 * 
	 * @return a List<OptionType> that contains the applicable OptionType. null if there is none.
	 */
	public abstract List<OptionType> getApplicableOptions();
}
