package edu.dynamic.dynamiz.displayer;

import java.util.ArrayList;

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
    }
    
    public void printCommandPrompt(){
	System.out.print("Command: ");
    }
    
    public static void displayTask(ArrayList<ToDoItem> list){
	int size = list.size();
	for(int i=0; i<size; i++)
	    System.out.println(list.get(i));
	System.out.println();
    }
}
