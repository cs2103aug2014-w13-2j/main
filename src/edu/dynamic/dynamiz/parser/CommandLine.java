package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.List;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.CommandDelete;
import edu.dynamic.dynamiz.controller.CommandDo;
import edu.dynamic.dynamiz.controller.CommandHelp;
import edu.dynamic.dynamiz.controller.CommandList;
import edu.dynamic.dynamiz.controller.CommandRedo;
import edu.dynamic.dynamiz.controller.CommandSearch;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.controller.CommandUndo;
import edu.dynamic.dynamiz.controller.CommandUpdate;
import edu.dynamic.dynamiz.structure.MyDate;
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
			case HELP:
				this.command = parseHelp();
				break;
			case DO:
				this.command = parseDo();
				break;
			case EXIT:
				this.command = parseExit();
				break;
			default:
		}

		return true;
	}

	
	private Command parseAdd() {
		Options commandOptions = extractOptions(this.options, CommandType.ADD);
		ToDoItem commandItem = null;

		// Handling date
		boolean hasStart = commandOptions.hasOption(OptionType.START_TIME);
		boolean hasEnd = commandOptions.hasOption(OptionType.END_TIME);
		boolean hasBoth = hasStart && hasEnd;

		try {
			MyDate startDate = null;
			MyDate endDate = null;
			
			if (hasStart) {
				startDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.START_TIME));
			}
			
			if (hasEnd) {
				endDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.END_TIME));
			}
			
			if (commandOptions.hasOption(OptionType.ON_TIME)) {
				MyDate onDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.ON_TIME));
				int dd = onDate.getDayOfMonth();
				int mm = onDate.getMonth();
				int yy = onDate.getYear();
				
				if (startDate != null) {
					startDate.setDate(dd, mm, yy);
				} else {
					startDate = onDate;
				}
				
				if (endDate != null) {
					endDate.setDate(dd, mm, yy);
				} else {
					endDate = onDate;
				}
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
		Options commandOptions = extractOptions(this.options, CommandType.LIST);
		
		// Parse Start and End Date
		List<MyDate> commandStartDateList = new ArrayList<MyDate>();
		List<MyDate> commandEndDateList = new ArrayList<MyDate>();
		List<Integer> commandPriorityList = new ArrayList<Integer>();
		List<OptionType> commandOrderList = new ArrayList<OptionType>();
		
		if (commandOptions.hasOption(OptionType.START_TIME)) {
			commandStartDateList = extractDateList(commandOptions, OptionType.START_TIME);
		}
		
		if (commandOptions.hasOption(OptionType.END_TIME)) {
			commandEndDateList = extractDateList(commandOptions, OptionType.END_TIME);
		}
		
		if (commandOptions.hasOption(OptionType.PRIORITY)) {
			commandPriorityList = extractPriorityList(commandOptions);
		}
		
		if (commandOptions.hasOption(OptionType.ORDER_BY)) {
			commandOrderList = extractOptionTypeList(commandOptions);
		}
		
		int[] priorities = null;
		MyDate[] startDates = null;
		MyDate[] endDates = null;
		OptionType[] orderings = null;
		
		if (!commandStartDateList.isEmpty()) {
			startDates = commandStartDateList.toArray(new MyDate[commandStartDateList.size()]);
		}
		
		if (!commandEndDateList.isEmpty()) {
			endDates = commandEndDateList.toArray(new MyDate[commandEndDateList.size()]);
		}
		
		if (!commandPriorityList.isEmpty()) {
			priorities = new int[commandPriorityList.size()];
			for (int i = 0; i < commandPriorityList.size(); i++) {
				priorities[i] = commandPriorityList.get(i);
			}
		}
		
		if (!commandOrderList.isEmpty()) {
			orderings = commandOrderList.toArray(new OptionType[commandOrderList.size()]);
		}
		
		return new CommandList(priorities, startDates, endDates, orderings);
	}


	private Command parseSearch() {
		// TODO: Implement ability to search with keywords and options
		Options commandOptions = extractOptions(this.options, CommandType.SEARCH);
		
		// Parse Start and End Date
		MyDate commandStartDate = null;
		MyDate commandEndDate = null;
		int commandPriority = OptionType.PRIORITY_UNCHANGED;
		
		List<OptionType> commandOrderList= new ArrayList<OptionType>();
		
		if (commandOptions.hasOption(OptionType.START_TIME)) {
			commandStartDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.START_TIME));
		}
		
		if (commandOptions.hasOption(OptionType.END_TIME)) {
			commandEndDate = parser.parseDate(getFirstOptionValue(commandOptions, OptionType.END_TIME));
		}
		
		if (commandOptions.hasOption(OptionType.PRIORITY)) {
			commandPriority = Integer.parseInt(getFirstOptionValue(commandOptions, OptionType.PRIORITY));
		}
		
		if (commandOptions.hasOption(OptionType.ORDER_BY)) {
			List<String> commandOrderStrList = getFirstOptionValues(commandOptions, OptionType.ORDER_BY);
			for (String s: commandOrderStrList) {
				commandOrderList.add(OptionType.fromString(s));
			}
		}
		
		return new CommandSearch(param, commandPriority, commandStartDate, commandEndDate,
									commandOrderList.toArray(new OptionType[commandOrderList.size()]));
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

		Options commandOptions = extractOptions(this.options, CommandType.UPDATE);

		// Parse Start and End Date
		MyDate commandStartDate = null;
		MyDate commandEndDate = null;
		
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

	private Command parseHelp() {
		CommandType type = CommandType.fromString(param);
		if (type != null) {
			return new CommandHelp(type);
		} else {
			return new CommandHelp();
		}
	}
	
	private Command parseDo() {
		return new CommandDo(param);
	}
	
	private Command parseExit() {
		return null;
	}
	
	
	private String getFirstOptionValue(Options commandOptions, OptionType optionType) {
		List<Option> optionList = commandOptions.getOptions(optionType);
		Option option = optionList.get(optionList.size() - 1);
		String optionStr = option.getValues().get(0);
			
		return optionStr;
	}
	
	private List<String> getFirstOptionValues(Options commandOptions, OptionType optionType) {
		Option option = commandOptions.getOptions(optionType).get(0);
		return option.getValues();
	}

	/**
	 * Get the list of applicable options from all the options given
	 * 
	 * @param options
	 *            The unchecked collection of Option
	 * @return the checked collection of Option
	 */
	public Options extractOptions(Options options, CommandType commandType) {
		Options opts = new Options();

		for (OptionType optType : commandType.getApplicableOptions()) {
			if (options.hasOption(optType)) {
				List<Option> optList = options.getOptions(optType);
				opts.add(optList);
			}
		}

		return opts;
	}
	
	public List<MyDate> extractDateList(Options options, OptionType dateType) {
		List<String> values = options.getOptions(dateType).get(0).getValues();
		List<MyDate> dateList = new ArrayList<MyDate>();
		
		for (String value: values) {
			MyDate date = parser.parseDate(value);
			if (date != null) {
				dateList.add(date);
			}
		}
		
		return dateList;
	}
	
	public List<Integer> extractPriorityList(Options options) {
		List<String> values = options.getOptions(OptionType.PRIORITY).get(0).getValues();
		List<Integer> priorityList = new ArrayList<Integer>();
		
		for (String value: values) {
			Integer priority = Integer.parseInt(value);
			priorityList.add(priority);
		}
		
		return priorityList;
	}
	
	public List<OptionType> extractOptionTypeList(Options options) {
		List<String> values = options.getOptions(OptionType.ORDER_BY).get(0).getValues();
		List<OptionType> typeList = new ArrayList<OptionType>();
		
		for (String value: values) {
			typeList.add(OptionType.fromString(value));
		}
		
		return typeList;
	}
	
	// TODO: Need tested for refactor
	public List<Object> extractValueList(Options options, OptionType type) {
		List<String> values = options.getOptions(type).get(0).getValues();
		List<Object> valueList = new ArrayList<Object>();
		
		for (String value: values) {
			switch (type) {
				case START_TIME : // Fall through
				case END_TIME :
					MyDate date = parser.parseDate(value);
					if (date != null) {
						valueList.add(date);
					}
					break;
				case PRIORITY :
					Integer priority = Integer.parseInt(value);
					if (priority != null) {
						valueList.add(priority);
					}
					break;
				case ORDER_BY :
					OptionType optType = OptionType.fromString(value);
					if (optType != null) {
						valueList.add(optType);
					}
					break;
				default: throw new IllegalArgumentException();
			}
		}
		
		return valueList;
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
