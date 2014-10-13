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
	ToDoItem temp = storage.removeItem("A1");
	assertEquals("A1", temp.getId());
	list = storage.getList();
	assertEquals(2, list.length);
	storage.addItem(new TaskItem("CS2105 Programming Assignment 2", new Date(13, 10, 2014)));
	list = storage.getList();
	assertEquals(3, list.length);
	assertEquals("A4", list[2].getId());
    }
    
}
