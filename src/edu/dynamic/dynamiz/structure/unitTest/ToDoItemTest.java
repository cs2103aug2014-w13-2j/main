package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test case for ToDoItem class.
 */
//@author A0110781N
public class ToDoItemTest {
    
    @Test
    public void testToDoItem() {
	ToDoItem item = new ToDoItem("Do CS2103T");
	assertEquals("Do CS2103T", item.getDescription());
	assertEquals(1, item.getId());
	item = new ToDoItem("New task");
	assertEquals(2, item.getId());
	assertEquals("Pending", item.getStatus());
	assertEquals("Priority is 0", 0, item.getPriority());
	System.out.println(item.getFeedbackString());
	
	//Tests copy constructor
	ToDoItem item2 = new ToDoItem(item);
	assertFalse(item==item2);
	assertTrue(item.equals(item2));
	item2.setDescription("Do nothing");
	assertFalse(item.getDescription().equals(item2.getDescription()));
	assertEquals(item.getId(), item2.getId());
    }
    
    @Test
    public void testToFileString(){
	ToDoItem item = new ToDoItem("Do CS2103T", 2);
	assertEquals("Do CS2103T; 2; Pending; --/--/---- --:--; --/--/---- --:--", item.toFileString());
    }
    
    @Test
    //Tests comparison.
    public void testCompareTo(){
	ToDoItem item1 = new ToDoItem("A", 4);
	ToDoItem item2 = new ToDoItem("B", 4);
	item1.setStatus(ToDoItem.STATUS_COMPLETED);
	assertEquals(1, item1.compareTo(item2));
	item2 = new EventItem("B", 3, new MyDate(1, 11, 2014), new MyDateTime(2, 1, 2014, 15, 0));
	assertEquals(1, item1.compareTo(item2));
	item2 = new TaskItem("B", 3, new MyDateTime(3, 11, 2014));
	assertEquals(1, item1.compareTo(item2));
    }
}
