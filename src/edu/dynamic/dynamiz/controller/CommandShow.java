package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command that retrieves an item by ID for full display.
 * 
 * Constructor
 * CommandShow(int id)	//Creates a new instance of this command.
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * ToDoItem[] getAffectedItems()	//Gets the list of the ToDoItem with the ID specified in the constructor.
 * String getCommandName()	//Gets the String representation of this command's type.
 */
//@author A0110781N
public class CommandShow extends Command {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "show";
    
    //Main data members
    private int id;
    private ToDoItem[] itemList;
    
    /**
     * Creates a new instance of this command.
     * @param id The ID of the ToDoItem to search.
     */
    public CommandShow(int id){
	this.id = id;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() throws IllegalArgumentException {
	itemList = storage.searchItems(id);
    }
    
    @Override
    /**
     * Gets the String representation of this command's type.
     * @return The String representation of this command's type.
     */
    public String getCommandName() {
	return COMMAND_TYPE;
    }
    
    @Override
    /**
     * Gets the list of ToDoItem with the ID specified in the constructor.
     * Should only be called after execute() method.
     * @return An array of the ToDoItem with the given ID.
     */
    public ToDoItem[] getAffectedItems() {
	assert itemList!=null;
	return itemList;
    }
    
}
