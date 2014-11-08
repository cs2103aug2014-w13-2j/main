package edu.dynamic.dynamiz.controller;

import java.util.EmptyStackException;
import java.util.Stack;

import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0110781N
/**
 * Defines the redo command.
 * 
 * Constructor
 * CommandRedo()	//Creates a new instance of this command.
 * 
 * Public Methods
 * void execute()	//Executes this command
 * ToDoItem[] getAffectedItems()	//Gets a list of ToDoItem objects affected by the redone command
 * String getCommandName()	//Gets the string representation of this command's type
 * void setStacks(Stack<Command> undoStack, Stack<Command> redoStack)	//Passes the undo and redo stacks for this command's execution
 */
public class CommandRedo extends Command {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "redo";
    
    //Main data members
    private Stack<Undoable> undoStack, redoStack;
    private Undoable command;	//The command to be redone
    
    /**
     * Creates a new instance of this command.
     */
    public CommandRedo(){
	
    }
    
    /**
     * Passes in the command history stack and the stack of undone commands to this
     * command for execution.
     * @param undoStack The command history stack.
     * @param redoStack The stack of undone commands.
     */
    public void setStacks(Stack<Undoable> undoStack, Stack<Undoable> redoStack){
	assert undoStack!=null && redoStack!=null;
	this.undoStack = undoStack;
	this.redoStack = redoStack;
    }
    
    @Override
    /**
     * Executes this command.
     * @throws EmptyStackException if the redo stack is empty.
     */
    public void execute() throws EmptyStackException {
	assert undoStack!=null && redoStack!=null;
	command = redoStack.pop();
	command.redo();
	undoStack.push(command);
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
     * Gets the list of ToDoItem objects affected by the redone command.
     * @return An array of ToDoItem objects affected by the redone command.
     */
    public ToDoItem[] getAffectedItems() {
	assert command!=null;
	return ((Command)command).getAffectedItems();
    }
    
}
