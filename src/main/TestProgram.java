package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dynamic.dynamiz.controller.Controller;
import edu.dynamic.dynamiz.structure.ErrorFeedback;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.HelpFeedback;
import edu.dynamic.dynamiz.structure.SuccessFeedback;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Test program to test the program as if it were real use without GUI.
 * Avoids the problem of random execution sequencing of the various methods in the JUnit test case.
 * However, ControllerTest JUnit test should still be used for automated testing for boundary test cases.
 * UI is not well-developed in this dummy program as it is mainly to test underlying functionalities.
 * 
 * @author zixian
 */
public class TestProgram {
    public static void main(String[] args){
	String input;
	Controller controller = new Controller();
	Feedback feedback;
	try{
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    while(!(input = reader.readLine()).equals("exit")){
		feedback = controller.executeCommand(input);
		if(feedback instanceof SuccessFeedback){
		    SuccessFeedback sFeedback = (SuccessFeedback)feedback;
		    if(sFeedback.getCommandType().equals("list") || sFeedback.getCommandType().equals("search")){
			ToDoItem[] list = sFeedback.getAffectedItems();
			if (list != null) {
			for(ToDoItem i: list){
			    System.out.println(i);
			}
			} else {
				System.out.println("-- No items --");
			}
			System.out.println(); 
		    } else {
			System.out.println("Successfully executed \""+input+"\".");
		    }
		} else if(feedback instanceof HelpFeedback){
		    System.out.println(((HelpFeedback)feedback).getHelpContent());
		} else{
		    System.out.println(((ErrorFeedback)feedback).getMessage());
		}
	    }
	    reader.close();
	} catch(IOException e){
	    
	}
    }
}
