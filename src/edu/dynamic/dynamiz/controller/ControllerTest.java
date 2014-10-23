package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.structure.ErrorFeedback;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.SuccessFeedback;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class ControllerTest {
    
    @Test
    public void testAdd() throws Exception{
	Feedback feedback;
	Controller controller = new Controller();
	
	//Adds a ToDoItem
	feedback = controller.executeCommand("add Buy newspaper");
	assertEquals("add", feedback.getCommandType());
	assertEquals("add Buy newspaper", feedback.getOriginalCommand());
	
	//Adds an event
	feedback = controller.executeCommand("add Meeting priority 2 from 7/10/2014 to 8/10/2014");
	feedback = controller.executeCommand("add CS2103T Tutorial from 8/10/2014 13:00 to 8/10/2014 14:00");
	feedback = controller.executeCommand("add D by 25/10/2014");
	
	//Erroneous test case. To be dealt with in later stages.
	//feedback = controller.executeCommand("add");
    }
    
    @Ignore
    public void testDelete(){
	//Deletes and item
	//feedback = controller.executeCommand("delete A2");
	//assertEquals("delete", feedback.getCommandType());
	//assertEquals("delete A2", feedback.getOriginalCommand());
    }
    
    @Test
    public void testUpdate(){
	Controller controller = new Controller();
	Feedback feedback = controller.executeCommand("update A1 on 27/9/2014 17:30");
	assertEquals("update", feedback.getCommandType());
	
	feedback = controller.executeCommand("update A1 to 27/9/2014 20:00");
	assertEquals("update", feedback.getCommandType());
	assertEquals("update A1 to 27/9/2014 20:00", feedback.getOriginalCommand());
	
	//Adds a deadline to ToDoItem.
	feedback = controller.executeCommand("update A4 by 6/10/2014");
	assertTrue(feedback instanceof SuccessFeedback);
	
	feedback= controller.executeCommand("update A4 Go shopping");
	assertTrue(feedback instanceof SuccessFeedback);
	
	//Tests program's handling of invalid options.
	feedback = controller.executeCommand("update A4 from to");
	assertFalse(feedback instanceof SuccessFeedback);
    }
	
    @Test
    public void testList(){
	Controller controller = new Controller();
	Feedback feedback = controller.executeCommand("list");
	assertEquals("list", feedback.getCommandType());
	ToDoItem[] list = ((SuccessFeedback)feedback).getAffectedItems();
	for(ToDoItem item: list){
	    System.out.println(item);
	}
	System.out.println();
	
	//Output is wrong. Parsing of the following command not yet supported.
	feedback = controller.executeCommand("list -s");
	assertTrue(feedback instanceof SuccessFeedback);
	list = ((SuccessFeedback)feedback).getAffectedItems();
	for(ToDoItem item: list){
	    System.out.println(item);
	}
	System.out.println();
    }
    
    @Ignore
    public void testSearch(){
	Controller controller = new Controller();
	Feedback feedback = controller.executeCommand("search B");
	ToDoItem[] list = ((SuccessFeedback)feedback).getAffectedItems();
	for(ToDoItem item: list){
	    System.out.println(item);
	}
	System.out.println();
    }
    
    @Test
    public void testDo(){
	Controller controller = new Controller();
	Feedback feedback = controller.executeCommand("do A1");
	assertTrue(feedback instanceof SuccessFeedback);
	System.out.println(((SuccessFeedback)feedback).getAffectedItems()[0]);
	System.out.println();
    }
    
}
