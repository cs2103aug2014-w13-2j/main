package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command that lists all the items in the storage.
 * 
 * Constructor
 * CommandList(int[] priority, MyDate[] start, MyDate[] end, OptionType[] options)
 * 								//Creates a new instance of this list command.
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * String getCommandName()	//Gets the string representation of this command's type.
 * ToDoItem[] getAffectedItems()	//Gets the list retrieved by execute() method.
 * 
 * @author zixian
 */
public class CommandList extends Command {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "list";
    
    //Main data members
    private ToDoItem[] itemList = null;
    private int[] priority;
    private MyDate[] start, end;
    private OptionType[] optionsList = null; 
    
    /**
     * Creates a new instance of this command with the list of field values to use
     * for filtering and the list of options to use for sorting the filtered list.
     * @param keywords The list of keywords used to filter items or null if unused.
     * @param priority The priority levels used to filter items or null if unused.
     * @param start The list of start date values used to filter the items or null if unused.
     * @param end The list of end date values used to filter the items or null if unused.
     * @param options The list of options(in decreasing precedence) used to sort the filtered list
     * 		or null if unused.
     */
    public CommandList(int[] priority, MyDate[] start, MyDate[] end, OptionType[] options){
	this.priority = priority;
	this.start = start;
	this.end = end;
	this.optionsList = options;
    }
    
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() {
	itemList = storage.getList(priority, start, end, optionsList);
    }
    
    @Override
    /**
     * Gets the string representation of this command's type.
     * @return The string representation of this command's type.
     */
    public String getCommandName() {
	return COMMAND_TYPE;
    }
    
    @Override
    /**
     * Gets the list of ToDoItem to be displayed.
     * Must be called after execute() method.
     * @return An array of ToDoItem or null if the list is empty.
     */
    public ToDoItem[] getAffectedItems() {
	return itemList;
    }
    
}
