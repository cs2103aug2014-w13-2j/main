package edu.dynamic.dynamiz.parser;

import java.util.List;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.CommandDelete;
import edu.dynamic.dynamiz.controller.CommandList;
import edu.dynamic.dynamiz.controller.CommandRedo;
import edu.dynamic.dynamiz.controller.CommandSearch;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.controller.CommandUndo;
import edu.dynamic.dynamiz.controller.CommandUpdate;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * This is a class which stores the information of the parsed command line given
 * by the user.
 * 
 * Represents a list of arguments parsed against an option argument.
 * 
 * Also represents a list of commands parsed (?! if it is necessary)
 * 
 * This allows querying of a boolean hasOption(String opt), and retrieving value
 * of the option getOptionValue(String opt)
 * 
 * @author nhan
 *
 */
public class CommandLine {
	private Parser parser;

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
		this.parser = Parser.getInstance();

		this.commandType = cmdType;
		this.options = options;
		this.param = param;

		if (!initialiseCommand()) {
			System.out.println("Something is not right");
		}
	}

	private boolean initialiseCommand() {
		switch (this.commandType) {
			case ADD:
				this.command = parseAdd();
				break;
			case DELETE:
				this.command = parseDelete();
				break;
			case UPDATE:
				this.command = parseUpdate();
				break;
			case LIST:
				this.command = parseList();
				break;
			case SEARCH:
				this.command = parseSearch();
				break;
			case UNDO:
				this.command = parseUndo();
				break;
			case REDO:
				this.command = parseRedo();
				break;
			case EXIT:
				this.command = parseExit();
				break;
			default:
		}

		return true;
	}

	private String getFirstOptionValue(Options commandOptions, OptionType optionType) {
		Option option = commandOptions.getOptions(optionType).get(0);
		String optionStr = option.getValues().get(0);
			
		return optionStr;
	}

	private Command parseAdd() {
		Options commandOptions = extractOptions(this.options);
		ToDoItem commandItem = null;

		// Handling date
		boolean hasStart = commandOptions.hasOption(OptionType.START_TIME);
		boolean hasEnd = commandOptions.hasOption(OptionType.END_TIME);
		boolean hasBoth = hasStart && hasEnd;

		try {
			Date startDate = null;
			Date endDate = null;
			
			if (hasStart) {
				startDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.START_TIME));
			}
			
			if (hasEnd) {
				endDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.END_TIME));
			}
			
			if (hasBoth) {
				// TODO: Handle ambiguity here
				commandItem = new EventItem(this.param, startDate, endDate);
			} else if (hasEnd) {
				commandItem = new TaskItem(this.param, endDate);
			} else {
				commandItem = new ToDoItem(this.param);
			}
		} catch (IllegalArgumentException e) {
			// TODO: Implement Exception handling here
		}

		// Handling Priority (if applicable)
		if (commandOptions.hasOption(OptionType.PRIORITY)) {
			int priority = Integer.parseInt(getFirstOptionValue(commandOptions, OptionType.PRIORITY));
			commandItem.setPriority(priority);
		}
		
		return new CommandAdd(commandItem);
	}

	private Command parseDelete() {
		// TODO: Implement ability to delete tasks having the same option
		return new CommandDelete(param);
	}

	private Command parseList() {
		return new CommandList();
	}

	private Command parseSearch() {
		// TODO: Implement ability to search with keywords and options
		Options commandOptions = extractOptions(this.options);
		
		// Parse Start and End Date
		Date commandStartDate = null;
		Date commandEndDate = null;
		int commandPriority = OptionType.PRIORITY_UNCHANGED;
		
		if (commandOptions.hasOption(OptionType.START_TIME)) {
			commandStartDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.START_TIME));
		}
		
		if (commandOptions.hasOption(OptionType.END_TIME)) {
			commandEndDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.END_TIME));
		}
		
		if (commandOptions.hasOption(OptionType.PRIORITY)) {
			commandPriority = Integer.parseInt(getFirstOptionValue(commandOptions, OptionType.PRIORITY));
		}
		
		return new CommandSearch(param, commandPriority, commandStartDate, commandEndDate);
	}

	private Command parseUndo() {
		return new CommandUndo();
	}

	private Command parseRedo() {
		return new CommandRedo();
	}

	private Command parseUpdate() {
		// check param. If have more than just item ID, update the description
		String itemID = Util.getFirstWord(this.param);
		String extraDescription = Util.stripFirstWord(this.param);

		Options commandOptions = extractOptions(this.options);

		// Parse Start and End Date
		Date commandStartDate = null;
		Date commandEndDate = null;
		
		if (commandOptions.hasOption(OptionType.START_TIME)) {
			commandStartDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.START_TIME));
		}
		
		if (commandOptions.hasOption(OptionType.END_TIME)) {
			commandEndDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.END_TIME));
		}
		// Parse Priority
		int commandPriority = OptionType.PRIORITY_UNCHANGED;
		if (commandOptions.hasOption(OptionType.PRIORITY)) {
			commandPriority = Integer.parseInt(getFirstOptionValue(commandOptions, OptionType.PRIORITY));
		}
		

		return new CommandUpdate(itemID, extraDescription, commandPriority,
				commandStartDate, commandEndDate);
	}

	private Command parseExit() {
		return null;
	}

	/**
	 * Get the list of applicable options from all the options given
	 * 
	 * @param options
	 *            The unchecked collection of Option
	 * @return the checked collection of Option
	 */
	public Options extractOptions(Options options) {
		Options opts = new Options();

		for (OptionType optType : CommandType.ADD.getApplicableOptions()) {
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

	/*
	 * ========================================================================
	 * End of Getters & Setters
	 * ========================================================================
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
	 * This function will call the corresponding Command its execute. For
	 * example, if the parsed CommandLine has the CommandType of Add. It will
	 * call CommandAdd's execute.
	 */
	public void execute() {
		if (command != null) {
			command.execute();
		} else {
			throw new IllegalArgumentException("Null command");
		}
	}
}
