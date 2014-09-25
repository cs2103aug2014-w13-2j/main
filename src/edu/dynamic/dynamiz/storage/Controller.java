package edu.dynamic.dynamiz.storage;

import edu.dynamic.dynamiz.displayer.Display;
import edu.dynamic.dynamiz.displayer.DisplayStub;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class Controller {
    private DisplayStub displayer = new DisplayStub();
    private Storage storage = new StorageStub();
    
    public void executeCommand(String input){
	//Get list of command parameters from parser
	if(input.equals("list")){
	    display();
	} else{
	    displayer.printFeedbackMessage("OK");
	}
    }
    
    public void setup(){
	((StorageStub)storage).initialise();
    }
    
    //params is {description, priority, start, end/deadline}
    private void add(){
	//Make decision on what class to use based on input parameters.
	//((StorageStub)storage).addItem(...);
    }
    
    private void delete(String id){
	//((StorageStub)storage).removeItem(id);
    }
    
    private void display(){
	DisplayStub.displayTask(((StorageStub)storage).getList());
    }
}
