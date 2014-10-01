package edu.dynamic.dynamiz.logic;

import edu.dynamic.dynamiz.UI.Display;
import edu.dynamic.dynamiz.UI.DisplayStub;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class Controller {
    private DisplayStub displayer = new DisplayStub();
    private Storage storage = new Storage();
    
    /**
     * Executes the given input command.
     * @throws Exception if input is invalid or if the operation fails.
     * ToDo: Decide on the exception to be thrown on failure.
     * Notes: This is just a stub implementation. To be updated.
     */
    public Feedback executeCommand(String input){
	return null;
    }
    

    
    //Adds the given item to storage list.
    //Stub implementation for now.
    private void add(ToDoItem item){
	ToDoItem temp = storage.addItem(item);
	((DisplayStub)displayer).printFeedbackMessage(temp.getFeedbackString());
    }
    
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
}
