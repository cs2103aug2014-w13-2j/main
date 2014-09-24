package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dynamic.dynamiz.storage.Controller;


/**
 * The main program.
 * @author zixian
 *
 */

/*
 * Defines the object class of the main program.
 */
public class Dynamiz {
    private static Controller controller = new Controller();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) {
	//Load to-do list from file.
	//controller.setup();
	
	//Display welcome message, with some tips to help users get started.
	//Display.printWelcomeMessage();
	System.out.println("Welcome to Dynamiz. Enter \"add [new task]\", replacing \"[new task]\" with" +
			"your desired task description, to begin.");
	System.out.print("Command: ");
	String input = getInput();
	
	//Stub. To be refactored.
	while(!input.equals("exit")){
		controller.executeCommand(input);
		System.out.print("Command: ");
		input = getInput();
    	}
	try{
	    reader.close();
	} catch(IOException ioe){
	    
	}
    }

    private static String getInput(){
	try{
	    return reader.readLine();
	} catch(IOException ioe){
	    return "exit";
	}
    }
}
