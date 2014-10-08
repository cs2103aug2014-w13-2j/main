package edu.dynamic.dynamiz.structure;

import edu.dynamic.dynamiz.controller.CommandType;

/**
 * Defines the feedback object returned after each operation.
 * @author zixian
 */
public abstract class Feedback {
    public static final String CLASSNAME = "Feedback"; 
    
    //Main data members
    private String originalCommand;
    private CommandType commandType;
    //Constructor
    public Feedback(String commandType, String command){
	this.commandType = CommandType.fromString(commandType);
	this.originalCommand = command;
    }
    
    /**
     * Gets the command type of the command this feedback is for.
     * @return The command type String.
     */
    public CommandType getCommandType(){
	return commandType;
    }
    
    /**
     * Gets the original command that this feedback is for.
     * @return The original command String.
     */
    public String getOriginalCommand(){
	return originalCommand;
    }
    
    /**
     * Returns the name of this object's class.
     */
    public String getClassName(){
	return CLASSNAME;
    }
}
