package edu.dynamic.dynamiz.storage;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class StorageTest {
    
    @Test
    public void test() {
	Storage storage = Storage.getInstance();
	ToDoItem[] list = storage.getList();
	assertEquals(3, list.length);
	for(int i=0; i<3; i++)
	    System.out.println(list[i]);
	System.out.println();
	ToDoItem temp = storage.removeItem("A1");
	assertEquals("A1", temp.getId());
	list = storage.getList();
	assertEquals(2, list.length);
	storage.addItem(new TaskItem("CS2105 Programming Assignment 2", new Date(13, 10, 2014)));
	list = storage.getList();
	assertEquals(3, list.length);
	assertEquals("A4", list[2].getId());
    }
    
    @Test
    public void testCompleteItem(){
	Storage storage = Storage.getInstance();
	ToDoItem item = storage.completeItem("A2");
	System.out.println(item);
	ToDoItem item2 = storage.undoComplete();
	
	System.out.println(item2);
	System.out.println();
    }
    
    @Test
    public void testSorting(){
	Storage storage = Storage.getInstance();
	ToDoItem[] list = storage.getListSortedByEndDate();
	/*for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();*/
	
	System.out.println("Sort by start date");
	list = storage.getListSortedByStartDate();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	
	System.out.println("Sort by priority");
	list = storage.getListSortedByPriority();
	for(ToDoItem i: list)
	    System.out.println(i.getPriority());
	System.out.println();
    }
}
