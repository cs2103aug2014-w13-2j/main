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
	CommandType commandType = null;

	try{
	    /* To be scrapped after changing Parser.parse() return type to Command */
	    CommandLine cmdLine = parser.parse(input);
	    commandType = cmdLine.getCommandType();
	    
	    /* Proposed changes */
	    //command = parser.parse(input);
	    //if(command instanceof CommandUndo){
	    //	((CommandUndo)command).setStacks(undoStack, redoStack);
	    //} else if(command instanceof CommandRedo){
		//((CommandRedo)command).setStacks(undoStack, redoStack);
	    //}

	    //To be scrapped after changing Parser.parse() to return Command object
	    switch(commandType){
		case ADD: command = new CommandAdd(cmdLine.getParam(), cmdLine.getOptions());
			break;
		case DELETE: command = new CommandDelete(cmdLine.getParam());
			break;
		case UPDATE: command = new CommandUpdate(cmdLine.getParam(), cmdLine.getOptions());
			break;
		case LIST: command = new CommandList();
			break;
		case SEARCH: command = new CommandSearch(cmdLine.getParam(), cmdLine.getOptions());
			break;
		case UNDO: command = new CommandUndo();
			((CommandUndo)command).setStacks(undoStack, redoStack);
			break;
		case REDO: command = new CommandRedo();
			break;
		default: throw new Exception();
	    }
	    
	    command.execute();
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
	} catch(IllegalArgumentException e){
	    return new ErrorFeedback(command.getCommandName(), input, e.getMessage());
	} catch(Exception e){
	    return new ErrorFeedback(COMMAND_UNKNOWN, input, MSG_INVALIDCOMMAND);
	}
    }
}
