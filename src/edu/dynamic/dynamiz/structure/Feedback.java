package edu.dynamic.dynamiz.structure;

//@author A0110781N
/**
 * Defines the feedback object returned after each operation.
 * 
 * Constructor
 * Feedback(String commandType, String command)	//Creates a new instance of this feedback.
 * 
 * Public Methods
 * String getCommandType()	//Gets the command type of the command that this feedback is used for.
 * String getOriginalComamnd()	//Gets the input command that this feedback is used for.
 * String getClassName()	//gets the String representation of this feedback's class.
 */
public abstract class Feedback {
    //The String representation of this feedback's class.
    public static final String CLASSNAME = "Feedback"; 
    
    //Main data members
    private String commandType, originalCommand;
    
    /**
     * Creates a new instance of this feedback.
     * @param commandType The type of command this feedback is used for.
     * @param command the input command that this feedback is used for.
     */
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
     * @return The String representation of this feedback's class.
     */
    public String getClassName(){
	return CLASSNAME;
    }
}
