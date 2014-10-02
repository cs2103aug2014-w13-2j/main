package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dynamic.dynamiz.logic.Controller;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.UI.Displayer;
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
    private static Displayer displayer = new DisplayStub();
    
    //Delete when integrating GUI component
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) {
	
	//Displays welcome message and command prompt.
	//Modify as required.
	displayer.printWelcomeMessage();
	((DisplayStub)displayer).printCommandPrompt();
	
	//Get input from GUI. Please modify as required.
	String input = getInput();
	
	while(!input.equals(COMMAND_EXIT)){
	    Feedback feedback = controller.executeCommand(input);
	    //Display feedback message
	    //Display command prompt. Modify as required.
	    ((DisplayStub)displayer).printCommandPrompt();
	    
	    //Get input from GUI.
	    input = getInput();
    	}
	
	//Delete when integrating GUI component
	try{
	    reader.close();
	} catch(IOException ioe){
	    
	}
    }

  //Delete when integrating GUI component
    private static String getInput(){
	try{
	    return reader.readLine();
	} catch(IOException ioe){
	    return COMMAND_EXIT;
	}
    }
}
