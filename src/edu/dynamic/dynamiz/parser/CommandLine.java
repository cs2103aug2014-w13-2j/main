package edu.dynamic.dynamiz.parser;

import java.util.List;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.CommandType;

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
	private CommandType commandType;
	private Options options;
	private String param;
	private Command command;
	
	public CommandLine() {
		this.commandType = null;
		this.options = null;
		this.param = null;
	}
	
	public CommandLine(CommandType cmdType, Options options, String param) {
		this.commandType = cmdType;
		this.options = options;
		this.param = param;
		
		if (!initialiseCommand()) {
			System.out.println("Something is not right");
		}
	}

	private boolean initialiseCommand() {
		switch (commandType) {
		}
		
		return true;
	}
	/*
	 * ========================================================================
	 * Getters & Setters
	 * ========================================================================
	 */
	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType command) {
		this.commandType = command;
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
	
	public Command getCommand() {
		return command;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	/*
	 * ========================================================================
	 * End of Getters & Setters
	 * ========================================================================
	 * 
	 */
	
	public int getNumberOfOptions() {
		return options.getNumOfOptions();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Command Type: " + commandType.toString() + "\n");
		sb.append("Value" + param + "\n");
		sb.append("Options: \n" + options.toString());
		
		return sb.toString();
	}
	
	/**
	 * This function will call the corresponding Command its execute.
	 * For example, if the parsed CommandLine has the CommandType of 
	 * Add. It will call CommandAdd's execute.
	 */
	public void execute() {
		if (command != null) {
			command.execute();
		} else {
			throw new IllegalArgumentException("Null command");
		}
	}
}
