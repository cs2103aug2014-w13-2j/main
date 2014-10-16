package edu.dynamic.dynamiz.controller;

import java.util.EmptyStackException;
import java.util.Stack;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;

import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ErrorFeedback;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.SuccessFeedback;

/**
 * Defines the component in Dynamiz that coordinates the front-end user interface and the back end operations.
 * Currently, list and search operations are not supported as the parser has yet to implement parsing for
 * these 2 command types.
 * 
 * Constructor
 * Controller()	//Creates a new instance of this Controller.
 * 
 * Public Methods
 * Feedback executeCommand(String input)	//Executes the command represented by the input string.
 * 
 * @author zixian
 */
public class Controller {
    //Defines command type of unsupported commands for use in Feedback constructor
    private static final String COMMAND_UNKNOWN = "unknown command";
    
    //Defines the messages used for feedback.
    private static final String MSG_INVALIDCOMMAND = "Invalid command";
    private static final String MSG_EMPTYSTACK = "No command to $1%s";
    
    //Main data members
    private Parser parser;	//The Parser object to parse input commands
    private Stack<Undoable> undoStack, redoStack;	//Tracks commands being executed/undone
    private Stack<String> cmdHistory, undoneCommands;	//Tracks command strings being executed/undone
    
    /**
     * Creates a new Controller object for the program.
     */
    public Controller(){
	parser = Parser.getInstance();
	undoStack = new Stack<Undoable>();	//Keeps track of past commands
	redoStack = new Stack<Undoable>();	//Keeps track of undone commands.
						//Clears when executing new commands while this stack is not empty.
	cmdHistory = new Stack<String>();	//Keeps track of past command strings
	undoneCommands = new Stack<String>();	//Keeps track of undone command strings. Same mechanism as redoStack.
	Storage.getInstance();
    }
    
    /**
     * Executes the given input command.
     * @param input The user's input command string.
     * @return A Feedback object describing the success or failure of executing the input command.
     * Note: Implementation to be updated.
     */
    public Feedback executeCommand(String input){
	Command command = null;
	
	try{
	    command = parser.parse(input);	//this method throws IllegalArgumentException
	    if(command instanceof CommandUndo){
	    	((CommandUndo)command).setStacks(undoStack, redoStack);
	    } else if(command instanceof CommandRedo){
		((CommandRedo)command).setStacks(undoStack, redoStack);
	    }
	    
	    command.execute();	//If exceptions occur during execution, the command object does not go into undo stack
	    if(command instanceof Undoable){
		undoStack.push((Undoable) command);
		cmdHistory.push(input);
    		redoStack.clear();
    		undoneCommands.clear();
	    } else if(command instanceof CommandUndo){
    		undoneCommands.push(cmdHistory.pop());
	    } else if(command instanceof CommandRedo){
    		cmdHistory.push(undoneCommands.pop());
	    }
	    
	    return new SuccessFeedback(command.getCommandName(), input, command.getAffectedItems());
	    
	} catch(EmptyStackException e){	//Only thrown by attempts to undo/redo
	    return new ErrorFeedback(command.getCommandName(), input, String.format(MSG_EMPTYSTACK, command.getCommandName()));
	} catch(IllegalArgumentException e){	//Thrown by parser and storage operations
	    return new ErrorFeedback(command.getCommandName(), input, e.getMessage());
	} catch(Exception e){
	    return new ErrorFeedback(COMMAND_UNKNOWN, input, MSG_INVALIDCOMMAND);
	}
    }
}
