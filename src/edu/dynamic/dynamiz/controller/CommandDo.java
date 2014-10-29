package edu.dynamic.dynamiz.controller;

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
public class CommandDo extends Command implements Undoable{
    //String representation of this command's type
    private static final String COMMAND_TYPE = "do";
    
    //Main data members
    private int id;
    private ToDoItem feedbackItem;
    
    /**
     * Creates a new instance of this command.
     * @param id The ID of the item to mark as completed.
     */
    public CommandDo(int id){
	this.id = id;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() throws IllegalArgumentException {
	feedbackItem = storage.markItem(id);
    }
    
    @Override
    /**
     * Redo this command.
     */
    public void redo(){
	storage.markItem(feedbackItem);
    }
    
    @Override
    /**
     * Undo this command.
     */
    public void undo(){
	storage.unmarkItem(feedbackItem);
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
     * @return An array of 1 ToDoItem object that is marked as completed and null if the ToDoItem object
     * 		with the given id does not exist in storage.
     */
    public ToDoItem[] getAffectedItems() {
	if(feedbackItem==null){
	    return null;
	}
	ToDoItem[] list = new ToDoItem[1];
	list[0] = feedbackItem;
	return list;
    }
    
}
