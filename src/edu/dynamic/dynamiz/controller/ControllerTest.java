package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ErrorFeedback;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.SuccessFeedback;

public class ControllerTest {
    
    @Test
    public void testExecuteCommand() throws Exception{
	Feedback feedback;
	Controller controller = new Controller();
	
	feedback = controller.executeCommand("add Buy newspaper");
	assertEquals("add", feedback.getCommandType());
	assertEquals("add Buy newspaper", feedback.getOriginalCommand());
	
	feedback = controller.executeCommand("add Meeting priority 2 from 7/10/2014");
	
	feedback = controller.executeCommand("add CS2103T Tutorial from 8/10/2014 13:00 to 8/10/2014 14:00");
	
	feedback = controller.executeCommand("delete A2");
	assertEquals("delete", feedback.getCommandType());
	assertEquals("delete A2", feedback.getOriginalCommand());
	
	feedback = controller.executeCommand("update A1 from 27/9/2014 17:30");
	assertEquals("update", feedback.getCommandType());
	assertEquals("update A1 from 27/9/2014 17:30", feedback.getOriginalCommand());
	
	feedback = controller.executeCommand("update A1 to 27/9/2014 20:00");
	assertEquals("update", feedback.getCommandType());
	assertEquals("update A1 to 27/9/2014 20:00", feedback.getOriginalCommand());
	
	//Changes the deadline.
	feedback = controller.executeCommand("update A4 by 6/10/2014");
	assertTrue(feedback instanceof SuccessFeedback);
	
	//Item does not get changed due to error.
	feedback= controller.executeCommand("update A4 Go shopping");
	assertTrue(feedback instanceof SuccessFeedback);
	
	feedback = controller.executeCommand("update A4 from to");
	assertTrue(feedback instanceof SuccessFeedback);
	System.out.println(((SuccessFeedback)feedback).getAffectedItems()[0]);
	System.out.println(((SuccessFeedback)feedback).getAffectedItems()[1]);
    }
    
}
