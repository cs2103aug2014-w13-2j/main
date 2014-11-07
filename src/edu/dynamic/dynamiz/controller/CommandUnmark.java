package edu.dynamic.dynamiz.controller;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command that unmarks an item that was marked as completed in previous program executions
 * from completion.
 * 
 * Constructor
 * CommandUnmark(int[] id)	//Creates a new instance of this command.
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * ToDoItem[] getAffectedItem()	//Gets the ToDoItem that is unmarked by this command's execution.
 * String getCommandName()	//Gets the String representation of this command's type.
 * void redo()		//Re-executes this command.
 * void undo()		//Undo this command.
 */
//@author A0110781N
public class CommandUnmark extends Command implements Undoable {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "unmark";
    
  //Error message.
    private static final String MSG_NOMATCHINGID = "No matching ID.";
    private static final String MSG_NOVALIDITEMS = "No valid items to be unmarked from completion.";
    
    //Main data members
    private int[] id;
    private ToDoItem[] affectedItems;
    
    /**
     * Creates a new instance of this command.
     * @param id The list of ID of the ToDoItem objects to unmark from completion.
     */
    public CommandUnmark(int[] id){
	this.id = id;
    }
    
    @Override
    /**
     * Executes this command.
     * @throws IllegalArgumentException if there is no item to be unmarked, either due to
     * 		invalid ID or the item was marked as incomplete prior to this command's execution.
     */
    public void execute() throws IllegalArgumentException {
	int size = id.length, errors = 0;
	ToDoItem item;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>(size);
	for(int i=0; i<size; i++){
	    try{
		item = storage.unmarkItem(id[i]);
		if(item!=null){
		    temp.add(item);
		}
	    } catch(IllegalArgumentException e){
		errors++;
	    }
	}
	if(temp.isEmpty()){
	    if(errors==size){
		throw new IllegalArgumentException(MSG_NOMATCHINGID);
	    }
	    throw new IllegalArgumentException(MSG_NOVALIDITEMS);
	}
	affectedItems = temp.toArray(new ToDoItem[temp.size()]);
    }
    
    @Override
    /**
     * Undo this command.
     * Should only be executed after execute() method.
     */
    public void undo(){
	assert affectedItems!=null;
	for(ToDoItem i: affectedItems){
	    storage.markItem(i);
	}
    }
    
    @Override
    /**
     * Re-executes this command.
     * Should only be executed after undo() method.
     */
    public void redo(){
	assert affectedItems!=null;
	for(ToDoItem i: affectedItems){
	    storage.unmarkItem(i);
	}
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
     * Gets the ToDoItem that is affected by this command's execution.
     * Should only be used after the execute() method.
     * @return An array of the ToDoItem affected by this command's execution.
     */
    public ToDoItem[] getAffectedItems() {
	assert affectedItems!=null;
	return affectedItems;
    }
}
