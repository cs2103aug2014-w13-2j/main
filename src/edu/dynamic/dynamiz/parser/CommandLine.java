package edu.dynamic.dynamiz.parser;

import java.util.List;
import java.util.Map.Entry;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

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
	private Storage storage;
	
	public CommandLine() {
		this.commandType = null;
		this.options = null;
		this.param = null;
		this.storage = null;
	}
	
	public CommandLine(Storage storage, CommandType cmdType, Options options, String param) {
		this.commandType = cmdType;
		this.options = options;
		this.param = param;
		this.storage = storage;
		
		parseCommand();
	}
	
	public CommandLine(CommandType cmdType, Options options, String param) {
		this.commandType = cmdType;
		this.options = options;
		this.param = param;
		
		parseCommand();
	}

	private boolean parseCommand() {
		switch (commandType) {
			case ADD : 
				parseAdd();
				break;
			case UPDATE : 
				parseUpdate();
				break;
			case DELETE : 
				parseDelete();
				break;
			case LIST : 
			case SEARCH :
			case REDO :
			case UNDO :
			case EXIT :
			default : return false;
		}
		return true;
	}
	
	public void parseAdd() {
		ToDoItem item = null;
		Parser parser = new Parser();
		
		Options opts = extractOptions(this.options);
			boolean hasStart = opts.hasOption(OptionType.START_TIME);
			boolean hasEnd = opts.hasOption(OptionType.END_TIME);
			boolean hasBoth = hasStart && hasEnd;
			
			if (hasBoth) {
				Option startDateOpt = opts.getOptions(OptionType.START_TIME).get(0);
				String startDate = startDateOpt.getValues().get(0);
				
				Option endDateOpt = opts.getOptions(OptionType.END_TIME).get(0);
				String endDate = endDateOpt.getValues().get(0);
				
				item = new EventItem(param, parser.parseDate(startDate), parser.parseDate(endDate));
			} else if (hasEnd) {
				Option endDateOpt = opts.getOptions(OptionType.END_TIME).get(0);
				String endDate = endDateOpt.getValues().get(0);
				
				item = new TaskItem(param, parser.parseDate(endDate));
			} else {
				item = new ToDoItem(param);
			}
			
			command = new CommandAdd(item);
	}
	
	public void parseUpdate() {
		
	}
	
	public void parseDelete() {
		
	}
	
    public Options extractOptions(Options options) {
		Options opts = new Options();

		for (OptionType optType: CommandType.ADD.getApplicableOptions()) {
			if (options.hasOption(optType)) {
				List<Option> optList = options.getOptions(optType);
				opts.add(optList);
			}
		}
		
		return opts;
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

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
		if (this.command != null) {
			this.command.setStorage(storage);
		}
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
		sb.append("Value: " + param + "\n");
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
