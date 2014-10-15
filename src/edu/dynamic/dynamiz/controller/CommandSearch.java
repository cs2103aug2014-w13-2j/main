package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command to search for item with the given keyword in their description, 
 * filtered by the specified options, in the given storage.
 * 
 * Constructor
 * CommandSearch(String keyword, Options options)	//Creates an instance of this command object.
 * 
 * Public Methods
 * Options extractOptions(Options options)	//Extracts the options in this list that are applicable to this command.
 * void execute()	//Executes this command.
 * ToDoItem[] getAffectItems()	//Gets the list of items with the keyword in their description.
 * String getCommandName()	//Gets the string representation of this command's type.
 * 
 * @author zixian
 */
public class CommandSearch extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "search";
    
    //Error message
    private static final String MSG_EMPTYSEARCHSTRING = "Empty search string";
    
    //Main data members
    private String searchKey;
    private int priority;
    private Date start, end;
    private ToDoItem[] searchList = null;
    
    /**
     * Creates an instance of this search command.
     * @param keyword The keyword to search by, or null if search by keyword is not required.
     * @param priority The priority level of the item(s) to search, or -1 if not required.
     * @param start The start date of the item(s) to search, or null if not required.
     * @param end The end date of the item(s)to search, or null if not required.
     */
    public CommandSearch(String searchKey, int priority, Date start, Date end){
	this.searchKey = searchKey;
	this.priority = priority;
	this.start = start;
	this.end = end;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() {
	searchList = storage.getItems(searchKey, priority, start, end);
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
