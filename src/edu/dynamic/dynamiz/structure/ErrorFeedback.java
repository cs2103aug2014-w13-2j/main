package edu.dynamic.dynamiz.structure;

public class ErrorFeedback extends Feedback {
    //Main data member
    private String message;
    
    //Constructor
    public ErrorFeedback(String commandType, String command, String message){
	super(commandType, command);
	setMessage(message);
    }
    
    /**
     * Gets the error message associated with he operation this feedback is for.
     * @return The error message String.
     */
    public String getMessage(){
	return message;
    }
    
    //Sets the error message
    private void setMessage(String message){
	this.message = message;
    }
}
