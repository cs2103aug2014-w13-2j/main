package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command that unmarks an item that was marked as completed in previous program executions
 * from completion.
 * 
 * Constructor
 * CommandUnmark(int id)	//Creates a new instance of this command.
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * ToDoItem[] getAffectedItem()	//Gets the ToDoItem that is unmarked by this command's execution.
 * String getCommandName()	//Gets the String representation of this command's type.
 * void redo()		//Re-executes this command.
 * void undo()		//Undo this command.
 * 
 * @author zixian
 */
public class CommandUnmark extends Command implements Undoable {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "unmark";
    
    //Main data members
    private int id;
    private ToDoItem affectedItem;
    
    /**
     * Creates a new instance of this command.
     * @param id The ID of the ToDoItem to unmark from completion.
     */
    public CommandUnmark(int id){
	this.id = id;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() throws IllegalArgumentException {
	affectedItem = storage.unmarkItem(id);
    }
    
    @Override
    /**
     * Undo this command.
     */
    public void undo(){
	storage.markItem(affectedItem);
    }
    
    @Override
    /**
     * Re-executes this command.
     */
    public void redo(){
	storage.unmarkItem(affectedItem);
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
     * @return An array of the ToDoItem affected by this command's execution or null if the list is empty..
     */
    public ToDoItem[] getAffectedItems() {
	ToDoItem[] arr = new ToDoItem[1];
	arr[0] = affectedItem;
	return arr;
    }
    
}
