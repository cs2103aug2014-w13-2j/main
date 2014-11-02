package edu.dynamic.dynamiz.controller;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command to mark an event/task as completed.
 * 
 * Constructor
 * CommandDo(String id)		//Creates a new instance of this command
 * 
 * Public Methods
 * ToDoItem[] getAffectedItems()	//Gets a list of item that is marked as completed
 * String getCommandName()	//Gets the string representation of this command's type
 * void execute()	//Executes this command
 * void redo()		//Redo this command
 * void undo()		//Undo this command
 * 
 * @author zixian
 */
public class CommandMark extends Command implements Undoable{
    //String representation of this command's type
    private static final String COMMAND_TYPE = "do";
    
    //Error message.
    private static final String MSG_NOMATCHINGID = "No matching ID.";
    
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
     * Executes this command.
     * @throws IllegalArgumentException if none of the ID in the given array exists in the storage.
     */
    public void execute() throws IllegalArgumentException {
	int size = id.length;
	ToDoItem item;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>(size);
	for(int i=0; i<size; i++){
	    try{
		item = storage.markItem(id[i]);
		if(item!=null){
		    temp.add(item);
		}
	    } catch(IllegalArgumentException e){
		
	    }
	}
	if(temp.isEmpty()){
	    throw new IllegalArgumentException(MSG_NOMATCHINGID);
	}
	feedbackItems = temp.toArray(new ToDoItem[temp.size()]);
    }
    
    @Override
    /**
     * Redo this command.
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
     * Gets the ToDoItem that is marked as completed.
     * Must only be called after calling this command's execute() method.
     * @return An array of 1 ToDoItem object that is marked as completed.
     */
    public ToDoItem[] getAffectedItems() {
	assert feedbackItems!=null;
	return feedbackItems;
    } 
}
