package edu.dynamic.dynamiz.UI;
//unused
import edu.dynamic.dynamiz.structure.ToDoItem;

public class DisplayStub extends DisplayerFormatter {
   // @Override
    public void WelcomeMessage(){
	System.out.println(super.displayWelcomeMessage());
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
