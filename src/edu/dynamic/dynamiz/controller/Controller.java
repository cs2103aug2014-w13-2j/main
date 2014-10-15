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
    private static final String MSG_EMPTYSTACK = "No command to undo/redo";
    
    //Main data members
    private Parser parser;	//The Parser object to parse input commands
    private Stack<Command> undoStack, redoStack;
    private Stack<String> cmdHistory, undoneCommands;
    
    /**
     * Creates a new Controller object for the program.
     */
    public Controller(){
	parser = Parser.getInstance();
	undoStack = new Stack<Command>();	//Keeps track of past commands
	redoStack = new Stack<Command>();	//Keeps track of undone commands.
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
	String commandString;
	//Feedback feedback;
	try{
	    /* To be scrapped after changing Parser.parse() return type to Command */
	    CommandLine cmdLine = parser.parse(input);
	    commandType = cmdLine.getCommandType();
	    
	    /* Proposed changes */
	    command = parser.parse(input).getCommand();
	    if(command instanceof CommandUndo){
	    	((CommandUndo)command).setStacks(undoStack, redoStack);
	    } else if(command instanceof CommandRedo){
		((CommandRedo)command).setStacks(undoStack, redoStack);
	    }
	    command.execute();
	    if(!(command instanceof CommandList) && !(command instanceof CommandSearch) &&
	    	!(command instanceof CommandUndo) && !(command instanceof CommandRedo)){
	    		undoStack.push(command);
	    		cmdHistory.push(input);
	    		redoStack.clear();
	    		undoneCommands.clear();
	    	} else if(command instanceof CommandUndo){
	    		input = cmdHistory.pop();
	    		undoneCommands.push(input);
		} else if(command instanceof CommandRedo){
	    		input = undoneCommands.pop();
	    		cmdHistory.push(input);
		}
	    return new SuccessFeedback(command.getCommandName(), input, command.getAffectedItems());

	    //To be scrapped after changing Parser.parse() to return Command object
//	    switch(commandType){
//		case ADD: command = new CommandAdd(cmdLine.getParam(), cmdLine.getOptions());
//			break;
//		case DELETE: command = new CommandDelete(cmdLine.getParam());
//			break;
//		case UPDATE: command = new CommandUpdate(cmdLine.getParam(), cmdLine.getOptions());
//			break;
//		case LIST: command = new CommandList();
//			break;
//		case SEARCH: command = new CommandSearch(cmdLine.getParam(), cmdLine.getOptions());
//			break;
//		default: throw new Exception();
//	    }
//	    if(commandType!=CommandType.UNDO && commandType!=CommandType.REDO){
//		command.execute();
//		if(commandType!=CommandType.LIST && commandType!=CommandType.SEARCH){
//		    undoStack.push(command);
//		    cmdHistory.push(input);
//		    redoStack.clear();
//		    undoneCommands.clear();
//		}
//		return new SuccessFeedback(command.getCommandName(), input, command.getAffectedItems());
//	    } else if(commandType==CommandType.UNDO){
//		command = undoStack.pop();
//		commandString = cmdHistory.pop();
//		command.undo();
//		redoStack.push(command);
//		undoneCommands.push(commandString);
//		return new SuccessFeedback("undo", "undo "+commandString, command.getAffectedItems());
//	    } else{	//commandType==COMMANDTYPE.REDO
//		command = redoStack.pop();
//		commandString = undoneCommands.pop();
//		command.redo();
//		undoStack.push(command);
//		cmdHistory.push(commandString);
//		return new SuccessFeedback("redo", "redo "+commandString, command.getAffectedItems());
//	    }
	    
	} catch(EmptyStackException e){	//Only thrown by attempts to undo/redo
	    return new ErrorFeedback(command.getCommandName(), input, MSG_EMPTYSTACK);
	} catch(IllegalArgumentException e){
	    return new ErrorFeedback(command.getCommandName(), input, e.getMessage());
	} catch(Exception e){
	    return new ErrorFeedback(COMMAND_UNKNOWN, input, MSG_INVALIDCOMMAND);
	}
    }
}
