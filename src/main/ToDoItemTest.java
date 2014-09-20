package main;

import static org.junit.Assert.*;

import org.junit.Test;

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
