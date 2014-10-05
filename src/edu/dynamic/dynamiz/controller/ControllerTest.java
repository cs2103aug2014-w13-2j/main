package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.Feedback;

public class ControllerTest {
    
    @Test
    public void testExecuteCommand() throws Exception{
	Feedback feedback;
	Controller controller = new Controller();
	
	feedback = controller.executeCommand("add Buy newspaper");
	assertEquals("add", feedback.getCommandType());
	assertEquals("add Buy newspaper", feedback.getOriginalCommand());
	
	feedback = controller.executeCommand("delete A2");
	assertEquals("delete", feedback.getCommandType());
	assertEquals("delete A2", feedback.getOriginalCommand());
	
	feedback = controller.executeCommand("update A1 from 27/9/2014 17:30");
	assertEquals("update", feedback.getCommandType());
	assertEquals("update A1 from 27/9/2014 17:30", feedback.getOriginalCommand());
	
	feedback = controller.executeCommand("update A1 to 27/9/2014 20:00");
	assertEquals("update", feedback.getCommandType());
	assertEquals("update A1 to 27/9/2014 20:00", feedback.getOriginalCommand());
    }
    
}
