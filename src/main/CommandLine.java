package main;

import java.util.List;

/**
 * This is a class Object which stores the information of the parsed 
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
	private List<String> parameteres;
	
	
	
	public CommandType getCommandType() {
		return commandType;
	}
	
	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}
	
	public Options getOptions() {
		return options;
	}
	
	public void setOptions(Options options) {
		this.options = options;
	}
	
	public List<String> getParameteres() {
		return parameteres;
	}
	
	public void setParameteres(List<String> parameteres) {
		this.parameteres = parameteres;
	}
}
