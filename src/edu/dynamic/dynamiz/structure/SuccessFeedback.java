package edu.dynamic.dynamiz.structure;

/**
 * Defines the feedback object type for successful operations, excluding the help page command.
 * 
 * Constructor
 * SuccessFeedback(String commandType, String command, ToDoItem[] list)	//Creates a new instance of this feedback.
 * 
 * Public Methods
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItem objects affected by the command this feedback is used for.
 * String getClassName()	//Gets the String representation of this feedback's class.
 */
//@author A0110781N
public class SuccessFeedback extends Feedback {
    public static final String CLASSNAME = "SuccessFeedback";
    
    //Main data members
    private ToDoItem[] list;
    
    /**
     * Creates a new instance of this feedback.
     * @param commandType The command type this feedback is used for.
     * @param command The input command this feedback is used for.
     * @param list The list of ToDoItem objects affected by the command.
     */
    public SuccessFeedback(String commandType, String command, ToDoItem[] list){
	super(commandType, command);
	this.list = list;
    }
    
    /**
     * Gets the list of ToDoItem objects affected by the operation
     * specified by the original command.
     * @return An array of ToDoItem objects or null if the list is empty.
     */
    public ToDoItem[] getAffectedItems(){
	return list;
    }
    
    @Override
    /**
     * Gets the String representation of this feedback's class.
     * @return the String representation of this feedback's class.
     */
    public String getClassName(){
	return CLASSNAME;
    }
}
