package edu.dynamic.dynamiz.parser;

import java.util.List;

import edu.dynamic.dynamiz.logic.Command;

/**
 * This is a class which stores the information of the parsed 
 * command line given by the user.
 * 
 * Represents a list of arguments parsed against an option argument.
 * 
 * Also represents a list of commands parsed (?! if it is necessary)
 * 
 * This allows querying of a boolean hasOption(String opt), 
 *  and retrieving value of the option getOptionValue(String opt)
 *  
 * @author nhan
 *
 */
public class CommandLine {
	private Command command;
	private Options options;
	private String param;
	
	public CommandLine() {
		this.command = null;
		this.options = null;
		this.param = null;
	}
	
	public CommandLine(Command command, Options options, String param) {
		this.command = command;
		this.options = options;
		this.param = param;
	}

	/*
	 * ========================================================================
	 * Getters & Setters
	 * ========================================================================
	 */
	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	/*
	 * ========================================================================
	 * End of Getters & Setters
	 * ========================================================================
	 * 
	 */
	
	public String getCommandType() {
		return command.getType();
	}
	
	public int getNumberOfOptions() {
		return options.getNumOfOptions();
	}
	
	
}
