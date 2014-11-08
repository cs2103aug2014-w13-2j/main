package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.SuccessFeedback;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test case for Controller.
 */
//@author A0110781N
public class ControllerTest {
    
    @Test
    public void testAdd() throws Exception{
	Feedback feedback;
	Controller controller = new Controller();
	
	//Adds a ToDoItem
	feedback = controller.executeCommand("add Buy newspaper");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("add", feedback.getCommandType());
	assertEquals("add Buy newspaper", feedback.getOriginalCommand());
	assertEquals("Buy newspaper", ((SuccessFeedback)feedback).getAffectedItems()[0].getDescription());
	controller.executeCommand("undo");
	
	//Adds an event
	feedback = controller.executeCommand("add Meeting priority medium from 7/10/2014 to 8/10/2014");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("add", feedback.getCommandType());
	assertEquals("add Meeting priority medium from 7/10/2014 to 8/10/2014", feedback.getOriginalCommand());
	assertEquals("Meeting", ((SuccessFeedback)feedback).getAffectedItems()[0].getDescription());
	assertEquals(new MyDate(7, 10, 2014), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getStartDate());
	assertEquals(new MyDate(8, 10, 2014), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getEndDate());
	assertEquals(ToDoItem.PRIORITY_NORMAL, ((SuccessFeedback)feedback).getAffectedItems()[0].getPriority());
	controller.executeCommand("undo");
	
	//Adds another event, more on testing that the time element is added
	feedback = controller.executeCommand("add CS2103T Tutorial from 8/10/2014 13:00 to 8/10/2014 14:00");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("add", feedback.getCommandType());
	assertEquals("add CS2103T Tutorial from 8/10/2014 13:00 to 8/10/2014 14:00", feedback.getOriginalCommand());
	assertEquals("CS2103T Tutorial", ((SuccessFeedback)feedback).getAffectedItems()[0].getDescription());
	assertEquals(new MyDateTime(8, 10, 2014, 13, 0), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getStartDate());
	assertEquals(new MyDateTime(8, 10, 2014, 14, 0), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getEndDate());
	controller.executeCommand("undo");
	
	//Adds a deadline task, tests more for the deadline.
	feedback = controller.executeCommand("add D by 25/10/2014");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("add", feedback.getCommandType());
	assertEquals("add D by 25/10/2014", feedback.getOriginalCommand());
	assertEquals("D", ((SuccessFeedback)feedback).getAffectedItems()[0].getDescription());
	assertEquals(new MyDate(25, 10, 2014), ((TaskItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getDeadline());
	controller.executeCommand("undo");
	
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
    
    @Ignore
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
	
    @Ignore
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
    
    @Ignore
    public void testDo(){
	Controller controller = new Controller();
	Feedback feedback = controller.executeCommand("do A1");
	assertTrue(feedback instanceof SuccessFeedback);
	System.out.println(((SuccessFeedback)feedback).getAffectedItems()[0]);
	System.out.println();
    }
    
}
