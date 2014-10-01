package edu.dynamic.dynamiz.UI;

import edu.dynamic.dynamiz.structure.ToDoItem;

public class DisplayStub extends Display {
    @Override
    public void printWelcomeMessage(){
	super.printWelcomeMessage();
	System.out.println("Enter \"add [new task]\", replacing \"[new task]\" with" +
		"your desired task description, to begin.");
    }
    
    public void printFeedbackMessage(String message){
	System.out.println(message);
	System.out.println();
    }
    
    public void printCommandPrompt(){
	System.out.print("Command: ");
    }
    
    public static void displayTasks(ToDoItem[] list){
	int size = list.length;
	for(int i=0; i<size; i++)
	    System.out.println(list[i]);
	System.out.println();
    }
}
