package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dynamic.dynamiz.logic.Controller;
import edu.dynamic.dynamiz.UI.Display;
import edu.dynamic.dynamiz.UI.DisplayStub;

/**
 * The main program.
 * @author zixian
 *
 */

/*
 * Defines the object class of the main program.
 */
public class Dynamiz {
    private static final String COMMAND_EXIT = "exit";
    
    private static Controller controller = new Controller();
    private static Display displayer = new DisplayStub();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) {
	
	displayer.printWelcomeMessage();
	((DisplayStub)displayer).printCommandPrompt();
	
	String input = getInput();
	
	while(!input.equals(COMMAND_EXIT)){
		try {
		    controller.executeCommand(input);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		((DisplayStub)displayer).printCommandPrompt();
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
	    return COMMAND_EXIT;
	}
    }
}
