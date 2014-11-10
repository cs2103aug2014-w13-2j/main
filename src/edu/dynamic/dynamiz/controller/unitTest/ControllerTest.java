package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Controller;
import edu.dynamic.dynamiz.structure.ErrorFeedback;
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
    //Make a backup copy of todo.txt to put into dynamiz folder before running this unit test.
    
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
	feedback = controller.executeCommand("add CS2103T Tutorial from 8/10/2014 13:00 to 8/10/2014 2pm");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("add", feedback.getCommandType());
	assertEquals("add CS2103T Tutorial from 8/10/2014 13:00 to 8/10/2014 2pm", feedback.getOriginalCommand());
	assertEquals("CS2103T Tutorial", ((SuccessFeedback)feedback).getAffectedItems()[0].getDescription());
	assertEquals(new MyDateTime(8, 10, 2014, 13, 0), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getStartDate());
	assertEquals(new MyDateTime(8, 10, 2014, 14, 0), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getEndDate());
	controller.executeCommand("undo");
	
	//Adds a deadline task, tests more for the deadline.
	feedback = controller.executeCommand("add D by 25 October 2014");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("add", feedback.getCommandType());
	assertEquals("add D by 25 October 2014", feedback.getOriginalCommand());
	assertEquals("D", ((SuccessFeedback)feedback).getAffectedItems()[0].getDescription());
	assertEquals(new MyDate(25, 10, 2014), ((TaskItem)((SuccessFeedback)feedback).getAffectedItems()[0]).getDeadline());
	controller.executeCommand("undo");
    }
    
    @Test
    public void testDelete(){
	//Deletes an item
	Controller controller = new Controller();
	Feedback feedback = controller.executeCommand("delete 2");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("delete", feedback.getCommandType());
	assertEquals(2, ((SuccessFeedback)feedback).getAffectedItems()[0].getId());
	controller.executeCommand("undo");
	
	//Deletes multiple items by ID range
	feedback = controller.executeCommand("delete 2-4");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("delete", feedback.getCommandType());
	assertEquals(2, ((SuccessFeedback)feedback).getAffectedItems()[0].getId());
	assertEquals(3, ((SuccessFeedback)feedback).getAffectedItems()[1].getId());
	assertEquals(4, ((SuccessFeedback)feedback).getAffectedItems()[2].getId());
	controller.executeCommand("undo");
	
	//Deletes multiple items by ID
	feedback = controller.executeCommand("delete 2, 5");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals(2, ((SuccessFeedback)feedback).getAffectedItems()[0].getId());
	assertEquals(5, ((SuccessFeedback)feedback).getAffectedItems()[1].getId());
	controller.executeCommand("undo");
	
	//Case where some ID are invalid/non-existent
	feedback = controller.executeCommand("delete -1, 4");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals(1, ((SuccessFeedback)feedback).getAffectedItems().length);
	assertEquals(4, ((SuccessFeedback)feedback).getAffectedItems()[0].getId());
	controller.executeCommand("undo");
	
	//Case when all ID are invalid.
	feedback = controller.executeCommand("delete -1, 100");
	assertTrue(feedback instanceof ErrorFeedback);
    }
    
    @Test
    public void testUpdate(){
	Controller controller = new Controller();
	//Changes both start and end dates
	Feedback feedback = controller.executeCommand("update 1 on 27/9/2014 17:30");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("update", feedback.getCommandType());
	assertEquals(1, ((SuccessFeedback)feedback).getAffectedItems()[1].getId());
	assertEquals(new MyDateTime(27, 9, 2014, 17, 30), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[1]).getStartDate());
	assertEquals(new MyDateTime(27, 9, 2014, 17, 30), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[1]).getEndDate());
	controller.executeCommand("undo");
	
	//Changes only end date
	feedback = controller.executeCommand("update 1 to 1 january 2015");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals("update", feedback.getCommandType());
	if(((SuccessFeedback)feedback).getAffectedItems()[1] instanceof EventItem){
	    assertEquals(new MyDate(1, 1, 2015), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[1]).getEndDate());
	} else{
	    assertEquals(new MyDate(1, 1, 2015), ((TaskItem)((SuccessFeedback)feedback).getAffectedItems()[1]).getDeadline());
	}
	controller.executeCommand("undo");
	
	//Multiple fields update
	feedback = controller.executeCommand("update 4 Go shopping by 6/10/2014");
	assertTrue(feedback instanceof SuccessFeedback);
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals(4, ((SuccessFeedback)feedback).getAffectedItems()[1].getId());
	assertEquals("Go shopping", ((SuccessFeedback)feedback).getAffectedItems()[1].getDescription());
	if(((SuccessFeedback)feedback).getAffectedItems()[1] instanceof EventItem){
	    assertEquals(new MyDate(6, 10, 2014), ((EventItem)((SuccessFeedback)feedback).getAffectedItems()[1]).getEndDate());
	} else{
	    assertEquals(new MyDate(6, 10, 2014), ((TaskItem)((SuccessFeedback)feedback).getAffectedItems()[1]).getDeadline());
	}
	controller.executeCommand("undo");
    }
    
    @Test
    public void testSearch(){
	Controller controller = new Controller();
	//Search by keyword
	Feedback feedback = controller.executeCommand("search cs210");
	assertTrue(feedback instanceof SuccessFeedback);
	ToDoItem[] list = ((SuccessFeedback)feedback).getAffectedItems();
	for(ToDoItem item: list){
	    assertTrue(item.getDescription().toLowerCase().contains("cs210"));
	}
	
	//Search by multiple values
	feedback = controller.executeCommand("search cs priority high orderby starttime");
	assertTrue(feedback instanceof SuccessFeedback);
	list = ((SuccessFeedback)feedback).getAffectedItems();
	for(ToDoItem i: list){
	    assertTrue(i.getDescription().toLowerCase().contains("cs"));
	    assertEquals(ToDoItem.PRIORITY_HIGH, i.getPriority());
	}
    }
    
    @Test
    public void testDo(){
	Controller controller = new Controller();
	//Marks 1 item as completed.
	//Choose ID of an item whose status is not "Completed"
	Feedback feedback = controller.executeCommand("do 2");
	assertTrue(feedback instanceof SuccessFeedback);
	assertEquals(2, ((SuccessFeedback)feedback).getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, ((SuccessFeedback)feedback).getAffectedItems()[0].getStatus());
	controller.executeCommand("undo");
	
	//Marks multiple items by ID.
	//Choose IDs of items that are not yet marked as "completed".
	feedback = controller.executeCommand("mark 2, 3");
	assertTrue(feedback instanceof SuccessFeedback);
	ToDoItem[] list = ((SuccessFeedback)feedback).getAffectedItems();
	assertEquals(2, list.length);
	assertEquals(2, list[0].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, list[0].getStatus());
	assertEquals(3, list[1].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, list[1].getStatus());
	controller.executeCommand("undo");
    }
    
}
