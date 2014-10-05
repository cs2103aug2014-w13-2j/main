package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;

import edu.dynamic.dynamiz.UI.Displayer;
import edu.dynamic.dynamiz.UI.DisplayStub;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ErrorFeedback;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.SuccessFeedback;
import edu.dynamic.dynamiz.structure.ToDoItem;

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
    
    //Main data members
    private Parser parser;	//The Parser object to parse input commands
    private Storage storage;	//The storage that stores the data for this program.
    
    /**
     * Creates a new Controller object for the program.
     */
    public Controller(){
	parser = new Parser();
	storage = new Storage();
    }
    
    /**
     * Executes the given input command.
     * @param input The user's input command string.
     * @return A Feedback object describing the success or failure of executing the input command.
     * Note: Implementation to be updated.
     */
    public Feedback executeCommand(String input){
	Command command = null;
	Feedback feedback;
	try{
	    CommandLine cmdLine = parser.parse(input);

	    switch(cmdLine.getCommandType()){
		case ADD: command = new CommandAdd(cmdLine.getOptions(), cmdLine.getParam(), storage);
		break;
		case DELETE: command = new CommandDelete(cmdLine.getParam(), storage);
		break;
		case UPDATE: command = new CommandUpdate(cmdLine.getParam(), cmdLine.getOptions(), storage);
		break;
		default: throw new Exception();
	    }
	    command.execute();
	    feedback = new SuccessFeedback(command.getCommandName(), input, command.getAffectedItems());
	} catch(IllegalArgumentException e){
	    feedback = new ErrorFeedback(command.getCommandName(), input, e.getMessage());
	} catch(Exception e){
	    feedback = new ErrorFeedback(COMMAND_UNKNOWN, input, MSG_INVALIDCOMMAND);
	}
	return feedback;
    }
}
