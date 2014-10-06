package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command that lists all the items in the storage.
 * 
 * Constructor
 * CommandList(Storage storage)	//Creates a new instance of this list command.
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * void undo()		//An empty implementation of its superclass method.
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
    
    /**
     * Creates an instance of this list command.
     * @param storage The Storage object from which to retrieve the list.
     */
    public CommandList(Storage storage){
	assert storage!=null;
	this.storage = storage;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() {
	itemList = storage.getList();
    }
    
    @Override
    /**
     * Does nothing. List operation cannot be undone.
     */
    public void undo() {
	
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