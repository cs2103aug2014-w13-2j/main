package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

public class ToDoItemTest {
    
    @Test
    public void testToDoItem() {
	ToDoItem item = new ToDoItem("Do CS2103T");
	assertEquals("Do CS2103T", item.getDescription());
	assertEquals("A1", item.getId());
	item = new ToDoItem("New task");
	assertEquals("A2", item.getId());
	assertEquals("pending", item.getStatus());
	assertEquals("Priority is 0", 0, item.getPriority());
	System.out.println(item.getFeedbackString());
	ToDoItem item2 = new ToDoItem(item);
	assertFalse(item==item2);
	assertTrue(item.equals(item2));
	item2.setDescription("Do nothing");
	assertFalse(item.getDescription().equals(item2.getDescription()));
	assertEquals(item.getId(), item2.getId());
    }
    
    @Test
    public void testToString(){
	ToDoItem item = new ToDoItem("Do CS2103T", 1);
	//System.out.println(item.getId());	"A3"
	assertEquals("A3 Do CS2103T 1 pending --/--/---- --:-- --/--/---- --:--", item.toString());
    }
    
    @Test
    public void testToFileString(){
	ToDoItem item = new ToDoItem("Do CS2103T", 2);
	assertEquals("Do CS2103T; 2; pending; --/--/---- --:--; --/--/---- --:--", item.toFileString());
    }
}
