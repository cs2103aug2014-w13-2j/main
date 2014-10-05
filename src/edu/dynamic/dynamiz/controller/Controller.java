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

public class Controller {
    //Defines command type for use in Feedback constructor
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_UNKNOWN = "unknown command";
    private static final String COMMAND_UPDATE = "update";
    
    //Defines the messages used for feedback.
    private static final String MSG_INVALIDCOMMAND = "Invalid command";
    
    //Main data members
    private Parser parser;
    private Storage storage;
    
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
     */
    public Feedback executeCommand(String input){
	CommandLine cmdLine = parser.parse(input);
	Command command;
	Feedback feedback;
	try{
	    switch(cmdLine.getCommandType()){
		case ADD: command = new CommandAdd(cmdLine.getOptions(), cmdLine.getParam(), storage);
		break;
		case DELETE: command = new CommandDelete(cmdLine.getParam(), storage);
		break;
		case UPDATE: command = new CommandUpdate();
		break;
		default: throw new Exception();
	    }
	    command.execute();
	    feedback = new SuccessFeedback(command.getCommandName(), input, command.getAffectedItems());
	} catch(Exception e){
	    feedback = new ErrorFeedback(COMMAND_UNKNOWN, input, MSG_INVALIDCOMMAND);
	}
	return feedback;
    }
    
    /*
    //Removes the item with the given id from storage list.
    private void delete(String id){
	ToDoItem temp = storage.removeItem(id);
	((DisplayStub)displayer).printFeedbackMessage(temp.getFeedbackString());
    }
    
    //Lists all the items in storage.
    //Stub implementation for now.
    private void display(){
	DisplayStub.displayTasks(storage.getList());
    }
    
    //Updates the item with the given ID using the given details.
    //Stub implementation for now.
    private void update(String id){
	ToDoItem temp = storage.updateItem(id);
	((DisplayStub)displayer).printFeedbackMessage(temp.getFeedbackString());
    }
    */
}
