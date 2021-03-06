package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0110781N
/**
 * Defines the command to search for item with the given keyword in their description, 
 * filtered by the specified options, in the given storage.
 * 
 * Constructor
 * CommandSearch(String keyword, int priority, Date start, Date end)	//Creates an instance of this command object.
 * 
 * Public Methods
 * Options extractOptions(Options options)	//Extracts the options in this list that are applicable to this command.
 * void execute()	//Executes this command.
 * ToDoItem[] getAffectItems()	//Gets the list of items with the keyword in their description.
 * String getCommandName()	//Gets the string representation of this command's type.
 */
public class CommandSearch extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "search";
    
    //Main data members
    private String searchKey;
    private int priority;
    private MyDate start, end;
    private String status;
    private ToDoItem[] searchList = null;
    private OptionType[] optList;
    
    /**
     * Creates an instance of this search command.
     * @param keyword The keyword to search by, or null if search by keyword is not required.
     * @param priority The priority level of the item(s) to search, or -1 if not required.
     * @param start The start date of the item(s) to search, or null if not required.
     * @param end The end date of the item(s)to search, or null if not required.
     * @param status The status of the item to search for or null if not required.
     * @param optList The list of options in descending order of precedence, to sort the result by, or null if not required.
     */
    public CommandSearch(String searchKey, int priority, MyDate start, MyDate end, String status, OptionType[] optList){
	this.searchKey = searchKey;
	this.priority = priority;
	this.start = start;
	this.end = end;
	this.status = status;
	this.optList = optList;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() {
	   searchList = storage.searchItems(searchKey, priority, start, end, status, optList);
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
     * Gets the list of items obtained by this command.
     * Must be called only after calling the execute() method.
     * @return An array of ToDoItem objects resulting from this command's execution or
     * 		null if the list is empty.
     */
    public ToDoItem[] getAffectedItems() {
	return searchList;
    }
}
