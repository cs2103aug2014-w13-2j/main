package edu.dynamic.dynamiz.displayer;

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
}
