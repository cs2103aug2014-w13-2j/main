package edu.dynamic.dynamiz.structure;

/**
 * Defines the feedback class that is used when errors occurred during execution.
 * 
 * Constructor
 * ErrorFeedback(String commandType, String command, String message)	//Creates a new instance of this feedback.
 * 
 * Public Methods
 * String getMessage()	//Gets the error message of this feedback.
 * String getClassName()	//Gets the String representation of this feedback's class.
 * 
 * @author zixian
 */
public class ErrorFeedback extends Feedback {
    //The String representation of this feedback's class.
    public static final String CLASSNAME = "ErrorFeedback";
    
    //Main data member
    private String message;
    
    /**
     * Creates a new instance of this feedback.
     * @param commandType The type of command that caused an error.
     * @param command The input command string that caused the error.
     * @param message The error message to be shown to user.
     */
    public ErrorFeedback(String commandType, String command, String message){
	super(commandType, command);
	setMessage(message);
    }
    
    /**
     * Gets the error message associated with the operation this feedback is for.
     * @return The error message String.
     */
    public String getMessage(){
	return message;
    }
    
    //Sets the error message
    private void setMessage(String message){
	this.message = message;
    }
    
    @Override
    /**
     * gets the String representation of this feedback's class.
     * @return The String representation of this feedback's class.
     */
    public String getClassName(){
	return CLASSNAME;
    }
}
