package edu.dynamic.dynamiz.structure;

/**
 * Defines the feedback object returned after each operation.
 * @author zixian
 */
public abstract class Feedback {
    public static final String CLASSNAME = "Feedback"; 
    
    //Main data members
    private String commandType, originalCommand;
    
    //Constructor
    public Feedback(String commandType, String command){
	this.commandType = commandType;
	this.originalCommand = command;
    }
    
    /**
     * Gets the command type of the command this feedback is for.
     * @return The command type String.
     */
    public String getCommandType(){
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
