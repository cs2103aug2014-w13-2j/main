package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the Command object that deletes a ToDoItem from Storage object.
 * 
 * Constructor
 * CommandDelete(String id, Storage storage)	//Creates a CommandDelete object that will delete the ToDoItem
 * 						//with this id from the given storage.
 * 
 * Methods(public)
 * void execute()	//Executes this command
 * void undo()		//Undoes this command's execute method.
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItem objects removed from storage by
 * 					//this command's execute method.
 * String getCommandName()	//Gets the string representation of this command's type.
 * 
 * @author zixian
 */
public class CommandDelete extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "delete";
    
    //Main data members
    private String id;	//The id of the ToDoItem to remove.
    private ToDoItem deletedItem;	//The ToDoItem that is deleted by this command.
    
    /**
     * Creates a new Command object to remove the object with the given id from the storage.
     * @param id The ID of the ToDoItem to remove.
     * @param storage The storage object from which the ToDoItem is to be removed.
     */
    public CommandDelete(String id, Storage storage){
	assert id!=null && !id.isEmpty() && storage!=null;
	this.id = id.trim();
	this.storage = storage;
    }
    
    @Override
    /**
     * Executes this command based on the parameters passed in the constructor.
     */
    public void execute() {
	deletedItem = storage.removeItem(id);
    }
    
    @Override
    /**
     * Undoes this command's execute method.
     * Can only be called after calling this command's execute method.
     */
    public void undo() {
	assert deletedItem!=null;
	
	storage.addItem(deletedItem);
    }
    
    @Override
    /**
     * Gets the string representation of this command's type.
     * @return The String representation of this command's type.
     */
    public String getCommandName() {
	return COMMAND_TYPE;
    }

    @Override
    /**
     * Gets the list of ToDoItems affected by this command's execution.
     * Can only be called after calling this command's execute method.
     * @return A list of ToDoItems removed by this command.
     */
    public ToDoItem[] getAffectedItems() {
	assert deletedItem!=null;
	
	ToDoItem[] list = new ToDoItem[1];
	list[0] = deletedItem;
	return list;
    }

}
