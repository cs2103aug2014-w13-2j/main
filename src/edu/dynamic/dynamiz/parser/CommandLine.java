package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.CommandDelete;
import edu.dynamic.dynamiz.controller.CommandMark;
import edu.dynamic.dynamiz.controller.CommandHelp;
import edu.dynamic.dynamiz.controller.CommandList;
import edu.dynamic.dynamiz.controller.CommandRedo;
import edu.dynamic.dynamiz.controller.CommandSearch;
import edu.dynamic.dynamiz.controller.CommandShow;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.controller.CommandUndo;
import edu.dynamic.dynamiz.controller.CommandUnmark;
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
	
	private final int INVALID_ID = -1;
	
	/** A logger instance for this class*/
	private final static Logger LoggerCommandLine = Logger.getLogger(CommandLine.class.getName());
	
	private final static String INVALID_ID_MSG = "Not a valid id given: %1$s";
	private final static String INVALID_COMMANDTYPE_MSG = "Not a valid alias to known CommandType: %1$s";
	private final static String INVALID_OPTIONTYPE_MSG = "Not a valid alias of known OptionType: %1$s";
	
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
			LoggerCommandLine.severe("Command is not initialised!");
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
			case MARK:
				this.command = parseMark();
				break;
			case UNMARK:
				this.command = parseUnmark();
				break;
			case SHOW:
				this.command = parseShow();
				break;
			case EXIT:
				this.command = parseExit();
				break;
			default:
				return false;
		}

		return true;
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandAdd} object
	 * @return a parsed {@link CommandAdd} object
	 */
	private Command parseAdd() {
		ToDoItem commandItem = null;

		// Handling date
		boolean hasStart = options.hasOption(OptionType.START_TIME);
		boolean hasEnd = options.hasOption(OptionType.END_TIME);
		boolean hasBoth = hasStart && hasEnd;
		boolean hasOn = options.hasOption(OptionType.ON_TIME);
		
		MyDate startDate = null;
		MyDate endDate = null;

		if (hasStart) {
			startDate = Util.convertStringToMyDate(getFirstOptionValue(options,	OptionType.START_TIME));
		}

		if (hasEnd) {
			endDate = Util.convertStringToMyDate(getFirstOptionValue(options, OptionType.END_TIME));
		}

		if (hasOn) {
			MyDate onDate = Util.convertStringToMyDate(getFirstOptionValue(options, OptionType.ON_TIME));
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

		if (hasBoth || hasOn) {
			commandItem = new EventItem(this.param, startDate, endDate);
		} else if (hasEnd) {
			commandItem = new TaskItem(this.param, endDate);
		} else {
			commandItem = new ToDoItem(this.param);
		}
		
		// Handling Priority (if applicable)
		if (options.hasOption(OptionType.PRIORITY)) {
			int priority = Integer.parseInt(getFirstOptionValue(options, OptionType.PRIORITY));
			commandItem.setPriority(priority);
		}
		
		return new CommandAdd(commandItem);
		
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandDelete} object
	 * @return a parsed {@link CommandDelete} object
	 */
	private Command parseDelete() {
		int[] ids = Util.toIntArray(extractIdList(param));
		return new CommandDelete(ids);
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandList} object
	 * @return a parsed {@link CommandList} object
	 */
	private Command parseList() {
		// Parse Start and End Date
		List<MyDate> commandStartDateList = new ArrayList<MyDate>();
		List<MyDate> commandEndDateList = new ArrayList<MyDate>();
		List<MyDate> commandOnDateList = new ArrayList<MyDate>();
		
		List<Integer> commandPriorityList = new ArrayList<Integer>();
		List<OptionType> commandOrderList = new ArrayList<OptionType>();
		
		if (options.hasOption(OptionType.START_TIME)) {
			commandStartDateList = extractDateList(options, OptionType.START_TIME);
		}
		
		if (options.hasOption(OptionType.END_TIME)) {
			commandEndDateList = extractDateList(options, OptionType.END_TIME);
		}
		
		if (options.hasOption(OptionType.PRIORITY)) {
			commandPriorityList = extractPriorityList(options);
		}
		
		if (options.hasOption(OptionType.ORDER_BY)) {
			commandOrderList = extractOptionTypeList(options);
		}
		
		if (options.hasOption(OptionType.ON_TIME)) {
			commandOnDateList = extractDateList(options, OptionType.ON_TIME);
			if (!commandOnDateList.isEmpty()) {
				for (MyDate date: commandOnDateList) {
					commandStartDateList.add(date);
					commandEndDateList.add(date);
				}
			}
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

	/**
	 * Parsing CommandLine object into respective {@link CommandSearch} object
	 * @return a parsed {@link CommandSearch} object
	 */
	private Command parseSearch() {
		// Parse Start and End Date
		MyDate commandStartDate = null;
		MyDate commandEndDate = null;
		int commandPriority = OptionType.PRIORITY_UNCHANGED;
		
		List<OptionType> commandOrderList= new ArrayList<OptionType>();
		
		if (options.hasOption(OptionType.START_TIME)) {
			commandStartDate = Util.convertStringToMyDate(getFirstOptionValue(options, OptionType.START_TIME));
		}
		
		if (options.hasOption(OptionType.END_TIME)) {
			commandEndDate = Util.convertStringToMyDate(getFirstOptionValue(options, OptionType.END_TIME));
		}
		
		if (options.hasOption(OptionType.PRIORITY)) {
			commandPriority = Integer.parseInt(getFirstOptionValue(options, OptionType.PRIORITY));
		}
		
		if (options.hasOption(OptionType.ORDER_BY)) {
			commandOrderList = extractOptionTypeList(options);
		}
		
		return new CommandSearch(param, commandPriority, commandStartDate, commandEndDate,
									commandOrderList.toArray(new OptionType[commandOrderList.size()]));
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandUndo} object
	 * @return a parsed {@link CommandUndo} object
	 */
	private Command parseUndo() {
		return new CommandUndo();
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandRedo} object
	 * @return a parsed {@link CommandRedo} object
	 */
	private Command parseRedo() {
		return new CommandRedo();
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandUpdate} object
	 * @return a parsed {@link CommandUpdate} object
	 */
	private Command parseUpdate() {
		// check param. If have more than just item ID, update the description
		String itemID = Util.getFirstWord(this.param);
		int id;
		try {
			id = Integer.parseInt(itemID);
		} catch (NumberFormatException e) {
			LoggerCommandLine.warning(String.format(INVALID_ID_MSG, itemID));
			throw new IllegalArgumentException(String.format(INVALID_ID_MSG, itemID));
		}
		
		String extraDescription = Util.stripFirstWord(this.param);

		MyDate commandStartDate = null;
		MyDate commandEndDate = null;
		
		int commandPriority = OptionType.PRIORITY_UNCHANGED;

		if (options.hasOption(OptionType.START_TIME)) {
			commandStartDate = Util.convertStringToMyDate(getFirstOptionValue(options, OptionType.START_TIME));
		}
		
		if (options.hasOption(OptionType.END_TIME)) {
			commandEndDate = Util.convertStringToMyDate(getFirstOptionValue(options, OptionType.END_TIME));
		}
		
		if (options.hasOption(OptionType.PRIORITY)) {
			commandPriority = Integer.parseInt(getFirstOptionValue(options, OptionType.PRIORITY));
		}

		return new CommandUpdate(id, extraDescription, commandPriority,
				commandStartDate, commandEndDate);
	}

	/**
	 * Parsing CommandLine object into respective {@link CommandHelp} object
	 * @return a parsed {@link CommandHelp} object
	 */
	private Command parseHelp() {
		try {
			if (!param.isEmpty()) {
				CommandType type = CommandType.fromString(param);
				return new CommandHelp(type);
			} else {
				return new CommandHelp(null);
			}
		} catch (IllegalArgumentException e) {
			LoggerCommandLine.warning(String.format(INVALID_COMMANDTYPE_MSG, param));
			return new CommandHelp(null);
		}
	}
	
	/**
	 * Parsing CommandLine object into respective {@link CommandMark} object
	 * @return a parsed {@link CommandMark} object
	 */
	private Command parseMark() {
		int[] ids = Util.toIntArray(extractIdList(param));
		return new CommandMark(ids);
	}
	
	/**
	 * Parsing CommandLine object into respective {@link CommandUnmark} object
	 * @return a parsed {@link CommandUnmark} object
	 */
	private Command parseUnmark() {
		int[] ids = Util.toIntArray(extractIdList(param));
		return new CommandUnmark(ids);
	}
	
	/**
	 * Parsing CommandLine object into respective {@link CommandShow} object
	 * @return a parsed {@link CommandShow} object
	 */
	private Command parseShow() {
		try {
			int id = Integer.parseInt(param);
			return new CommandShow(id);
		} catch (NumberFormatException e) {
			LoggerCommandLine.warning(String.format(INVALID_ID_MSG, param));
			return new CommandShow(INVALID_ID);
		}
	}
	
	/**
	 * It is redundant to try and parse Exit command into an object
	 */
	private Command parseExit() {
		return null;
	}
	
	/**
	 * Retrieve the first valid value of the last {@link Option} present in the collection of the given 
	 * {@link Options}
	 * 
	 * @param commandOptions The collection of {@link Option}
	 * @param optionType The {@link OptionType} of the {@link Option} that is being retrieved
	 * @return the first valid value of the matching {@link Option} 
	 */
	private String getFirstOptionValue(Options commandOptions, OptionType optionType) {
		List<Option> optionList = commandOptions.getOptions(optionType);
		Option option = optionList.get(optionList.size() - 1);
		String optionStr = option.getValues().get(0);
			
		return optionStr;
	}
	
	/**
	 * Retrieve a list of {@link MyDate} object from the given list of value
	 * 
	 * @param options The collection of {@link Option} to extract from
	 * @param dateType The {@link OptionType} of the {@link Option} that is being retrieved
	 * @return a list of {@link MyDate} and/or {@link MyDateTime} objects extracted from the given collection.
	 */
	public List<MyDate> extractDateList(Options options, OptionType dateType) {
		List<String> values = options.getOptions(dateType).get(0).getValues();
		List<MyDate> dateList = new ArrayList<MyDate>();
		
		for (String value: values) {
			MyDate date = Util.convertStringToMyDate(value);
			if (date != null) {
				dateList.add(date);
			}
		}
		
		return dateList;
	}
	
	/**
	 * Retrieve a list of {@link OptionType.PRIORITY} integer values from the collection
	 * 
	 * @param options The collection of {@link Option} to extract from
	 * @return a list of {@link OptionType.PRIORITY} integer values extracted from the given collection.
	 */
	public List<Integer> extractPriorityList(Options options) {
		List<String> values = options.getOptions(OptionType.PRIORITY).get(0).getValues();
		List<Integer> priorityList = new ArrayList<Integer>();
		
		for (String value: values) {
			Integer priority = Integer.parseInt(value);
			priorityList.add(priority);
		}
		
		return priorityList;
	}
	
	/**
	 * Retrieve a list of {@link OptionType} values from the collection
	 * 
	 * @param options The collection of {@link Option} to extract from
	 * @return a list of matching {@link OptionType} values extracted from the given collection. 
	 */
	public List<OptionType> extractOptionTypeList(Options options) {
		List<String> values = options.getOptions(OptionType.ORDER_BY).get(0).getValues();
		List<OptionType> typeList = new ArrayList<OptionType>();
		
		for (String value: values) {
			try {
				OptionType type = OptionType.fromString(value);
				typeList.add(type);
			} catch (IllegalArgumentException e) {
				LoggerCommandLine.warning(String.format(INVALID_OPTIONTYPE_MSG, value));
				throw new IllegalArgumentException(String.format(INVALID_OPTIONTYPE_MSG, value));
			}
		}
		
		return typeList;
	}

	/** 
	 * Retrieve a List<Integer> from the given string
	 * 
	 * @param idStr The string in which the number list is extracted from
	 * @return a List<Integer> containing the values in the given string
	 */
	public List<Integer> extractIdList(String idStr) {
		String[] idArray = idStr.split(Option.DEFAULT_DELIMITER);
		List<String> idStrList = Util.removeEmptyStringsInArray(idArray);
		
		Set<Integer> idSet = new HashSet<Integer>();
		
		for (String id: idStrList) {
			if (Util.isInteger(id)) {
				idSet.add(Integer.parseInt(id));
			} else {
				List<Integer> idList = Util.getNumberListFromRange(id);
				if (idList != null && !idList.isEmpty()) {
					idSet.addAll(idList);
				} else {
					LoggerCommandLine.warning(String.format(INVALID_ID_MSG, id));
					throw new IllegalArgumentException(String.format(INVALID_ID_MSG, id));
				}
			}
		}
		
		return new ArrayList<Integer>(idSet);
	}
	
	/**
	 * Retrieve an Integer[] array from the given string
	 * 
	 * @param idStr The string in which the number array is extracted from
	 * @return an Integer[] containing the values in the given string.
	 */
	public Integer[] extractIdArray(String idStr) {
		List<Integer> idList = extractIdList(idStr);
		return idList.toArray(new Integer[idList.size()]);
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
