package edu.dynamic.dynamiz.storage;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class for Storage.
 * Testing of listing and sorting is highly dependent on the data available in todo.txt and is hence unautomatable.
 */
//@author A0110781N
public class StorageTest {
    
    @Test
    public void test() {
	//Tests create, update and delete.
	Storage storage = Storage.getInstance();

	//Simulate add command
	ToDoItem temp = new TaskItem("CS2105 Programming Assignment 2", 3, new MyDate(13, 10, 2014));
	storage.addItem(temp);	
	assertTrue(storage.searchItems(temp.getId())!=null);
	//storage.removeItem(temp.getId());
	
	//Simulates update command
	int id = temp.getId();
	ToDoItem[] list = storage.updateItem(id, null, 4, null, new MyDate(14, 10, 2014));	
	assertEquals(id, list[1].getId());
	assertEquals(4, list[1].getPriority());
	assertEquals(new MyDate(14, 10, 2014), ((TaskItem)list[1]).getDeadline());
	assertEquals(list[0].getId(), list[1].getId());
	storage.removeItem(id);
	storage.addItem(list[0]);
	assertEquals(list[0], storage.searchItems(id)[0]);
	storage.removeItem(id);
    }
    
    @Test
    //Tests marking and unmarking of items
    public void testCompleteItem(){
	Storage storage = Storage.getInstance();
	ToDoItem item = storage.markItem(2);
	assertEquals(2, item.getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, item.getStatus());
	item = storage.unmarkItem(2);
	assertEquals(2, item.getId());
	assertEquals(ToDoItem.STATUS_PENDING, item.getStatus());
    }
    
    @Test
    //Tests search by ID.
    public void testSearch(){
	Storage storage = Storage.getInstance();
	ToDoItem[] item = storage.searchItems(5);
	assertEquals(5, item[0].getId());
    }
}
