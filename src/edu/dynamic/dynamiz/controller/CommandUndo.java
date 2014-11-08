package edu.dynamic.dynamiz.controller;

import java.util.EmptyStackException;
import java.util.Stack;

import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0110781N
/**
 * Defines the command class that undoes the previously executed command.
 * 
 * Constructor
 * CommandUndo()	//Creates a new instance of this command.
 * 
 * Public Methods
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItems affected by this command.
 * String getCommandName()		//Gets the string representation of this command's type
 * void execute()	//Executes this command
 * void setStacks(Stack<Command> undoStack, Stack<Command> redoStack)	//Assigns the appropriate stacks to this command for execution
 */
public class CommandUndo extends Command {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "undo";
    
    //Main data members
    private Stack<Undoable> undoStack, redoStack;
    private Undoable command;	//The command to be undone
    
    /**
     * Creates a new instance of this undo command
     */
    public CommandUndo(){
	
    }
    
    /**
     * Passes the past and undone command history to this command.
     * @param undoStack The stack of commands to retrieve the command from.
     * @param redoStack The stack of commands to place the undone command into.
     */
    public void setStacks(Stack<Undoable> undoStack, Stack<Undoable> redoStack){
	assert undoStack!=null && redoStack!=null;
	this.undoStack = undoStack;
	this.redoStack = redoStack;
    }
    
    @Override
    /**
     * Executes this command.
     * @throws EmptyStackException if the undo stack is empty.
     */
    public void execute() throws EmptyStackException {
	assert undoStack!=null && redoStack!=null;
	command = undoStack.pop();
	redoStack.push(command);
	command.undo();
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
     * Gets the list of ToDoItem objects that are affected by this command.
     * Must be called after this command's execute() method.
     * @return An array of ToDoItem objects that are affected by this command.
     */
    public ToDoItem[] getAffectedItems() {
	assert command!=null;
	return ((Command)command).getAffectedItems();
    }
    
}
