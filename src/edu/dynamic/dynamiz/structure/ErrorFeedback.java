package edu.dynamic.dynamiz.structure;

public class ErrorFeedback extends Feedback {
    public static final String CLASSNAME = "ErrorFeedback";
    
    //Main data member
    private String message;
    
    //Constructor
    public ErrorFeedback(String commandType, String command, String message){
	super(commandType, command);
	setMessage(message);
    }
    
    /**
<<<<<<< HEAD
     * Gets the error message associated with he operation this feedback is for.
=======
     * Gets the error message associated with the operation this feedback is for.
>>>>>>> FETCH_HEAD
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
    public String getClassName(){
	return CLASSNAME;
    }
}
