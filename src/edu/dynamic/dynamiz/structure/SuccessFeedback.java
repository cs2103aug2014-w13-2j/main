package edu.dynamic.dynamiz.structure;

public class SuccessFeedback extends Feedback {
    //Main data members
    private ToDoItem[] list;
    
    //Constructor
    public SuccessFeedback(String commandType, String command, ToDoItem[] list){
	super(commandType, command);
	setList(list);
    }
    
    /**
     * Gets the list of ToDoItem objects affected by the operation
     * specified by the original command.
     * @return An array of ToDoItem objects.
     */
    public ToDoItem[] getAffectedItems(){
	return list;
    }
    
    private void setList(ToDoItem[] list){
	this.list = list;
    }
}
