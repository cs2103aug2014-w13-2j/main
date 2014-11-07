package edu.dynamic.dynamiz.storage;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class for Storage.
 */
//@author A0110781N
public class StorageTest {
    
    @Test
    public void test() {
	Storage storage = Storage.getInstance();

	//Simulate add command
	ToDoItem temp = storage.addItem(new TaskItem("CS2105 Programming Assignment 2", 3, new MyDate(13, 10, 2014)));	
	ToDoItem[] list = storage.getList(null, null, null, null);
	for(ToDoItem i: list){
	    System.out.println(i);
	}
	System.out.println();
	
	//Simulates update command
	int id = temp.getId();
	list = storage.updateItem(id, null, 4, null, new MyDate(14, 10, 2014));	
	assertEquals(id, list[1].getId());
	assertEquals(4, list[1].getPriority());
	assertEquals(new MyDate(14, 10, 2014), ((TaskItem)list[1]).getDeadline());
	
	//Simulate delete command
	temp = storage.removeItem(id);	
	assertEquals(id, temp.getId());
	list = storage.getList(null, null, null, null);
	for(ToDoItem i: list){
	    System.out.println(i);
	}
	System.out.println();
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
    
    @Ignore
    //Tests the specified sorting of items.
    public void testSorting(){
	Storage storage = Storage.getInstance();
	OptionType[] optionsList = new OptionType[1];
	optionsList[0] = OptionType.PRIORITY;
	ToDoItem[] list = storage.getList(null, null, null, optionsList);
	for(ToDoItem i: list){
	    System.out.println(i);
	}
	System.out.println();
	
	optionsList = new OptionType[2];
	optionsList[0] = OptionType.START_TIME;
	optionsList[1] = OptionType.PRIORITY;
	list = storage.getList(null, null, null, optionsList);
	for(ToDoItem i: list){
	    System.out.println(i);
	}
	System.out.println();
    }
}
