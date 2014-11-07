package edu.dynamic.dynamiz.controller;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command to mark an event/task as completed.
 * 
 * Constructor
 * CommandDo(int[] id)		//Creates a new instance of this command
 * 
 * Public Methods
 * ToDoItem[] getAffectedItems()	//Gets a list of item that is marked as completed
 * String getCommandName()	//Gets the string representation of this command's type
 * void execute()	//Executes this command
 * void redo()		//Redo this command
 * void undo()		//Undo this command
 */
//@author A0110781N
public class CommandMark extends Command implements Undoable{
    //String representation of this command's type
    private static final String COMMAND_TYPE = "do";
    
    //Error message.
    private static final String MSG_NOMATCHINGID = "No matching ID.";
    private static final String MSG_NOVALIDITEMS = "No valid items to be marked as completed.";
    
    //Main data members
    private int[] id;
    private ToDoItem[] feedbackItems;
    
    /**
     * Creates a new instance of this command.
     * @param id The list of ID of the items to mark as completed.
     */
    public CommandMark(int[] id){
	this.id = id;
    }
    
    @Override
    /**
     * Executes this command. The list will not contain items of invalid ID nor items that are marked
     * as completed prior to this command's execution.
     * @throws IllegalArgumentException if none of the specified items can be marked as completed,
     * 		either due to invalid ID or the item has been previously marked as completed.
     */
    public void execute() throws IllegalArgumentException {
	int size = id.length, errors = 0;
	ToDoItem item;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>(size);
	for(int i=0; i<size; i++){
	    try{
		item = storage.markItem(id[i]);
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
	feedbackItems = temp.toArray(new ToDoItem[temp.size()]);
    }
    
    @Override
    /**
     * Redo this command.
     * Should only be used after the undo() method.
     */
    public void redo(){
	assert feedbackItems!=null;
	for(ToDoItem i: feedbackItems){
	    storage.markItem(i);
	}
    }
    
    @Override
    /**
     * Undo this command.
     * Should only be used after the execute() method.
     */
    public void undo(){
	assert feedbackItems!=null;
	for(ToDoItem i: feedbackItems){
	    storage.unmarkItem(i);
	}
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
     * Gets the ToDoItem object(s) that is/are marked as completed.
     * Must only be called after calling this command's execute() method.
     * @return An array of ToDoItem object(s) that is/are marked as completed.
     */
    public ToDoItem[] getAffectedItems() {
	assert feedbackItems!=null;
	return feedbackItems;
    } 
}
