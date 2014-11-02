package edu.dynamic.dynamiz.controller;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the Command object that deletes a ToDoItem from Storage object.
 * 
 * Constructor
 * CommandDelete(int[] id)	//Creates a CommandDelete object that will delete the ToDoItem
 * 						//with the given list of id from the storage.
 * 
 * Methods(public)
 * void execute()	//Executes this command.
 * void undo()		//Undoes this command's execute method.
 * void redo()		//Redo this command.
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItem objects removed from storage by
 * 					//this command's execute method.
 * String getCommandName()	//Gets the string representation of this command's type.
 * 
 * @author zixian
 */
public class CommandDelete extends Command implements Undoable {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "delete";
    
    //Error message.
    private static final String MSG_NOMATCHINGID = "No matching ID found.";
    
    //Main data members
    private int[] id;	//The id of the ToDoItem to remove.
    private ToDoItem[] deletedItems;	//The ToDoItem that is deleted by this command.
    
    /**
     * Creates a new Command object to remove the object with the given id from the storage.
     * @param id The list of ID of the ToDoItem objects to remove.
     * @param storage The storage object from which the ToDoItem is to be removed.
     */
    public CommandDelete(int[] id){
	assert id!=null;
	this.id = id;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() throws IllegalArgumentException {
	int size = id.length;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>(size);
	for(int i=0; i<size; i++){
	    try{
		temp.add(storage.removeItem(id[i]));
	    } catch(IllegalArgumentException e){
		
	    }
	}
	if(temp.isEmpty()){
	    throw new IllegalArgumentException(MSG_NOMATCHINGID);
	}
	deletedItems = temp.toArray(new ToDoItem[temp.size()]);
    }
    
    @Override
    /**
     * Undoes this command's execute method.
     * Can only be called after calling this command's execute method.
     */
    public void undo() {
	assert deletedItems!=null;
	for(ToDoItem i: deletedItems){
	    storage.addItem(i);
	}
    }
    
    @Override
    /**
     * Re-executes this command.
     * Must only be called after calling this command's undo() method.
     */
    public void redo() throws IllegalArgumentException {
	execute();
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
     * Note that not all specified items are in the list as some of them may be non-existent.
     * @return A list of ToDoItems removed by this command.
     */
    public ToDoItem[] getAffectedItems() {
	assert deletedItems!=null;
	
	return deletedItems;
    }

}
