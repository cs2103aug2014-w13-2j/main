package edu.dynamic.dynamiz.storage;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class StorageTest {
    
    @Test
    public void test() {
	Storage storage = Storage.getInstance();
	
	ToDoItem[] list = storage.getList(null);	//Simulate list command
	assertEquals(3, list.length);
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	
	ToDoItem temp = storage.removeItem("A1");	//Simulate delete command
	assertEquals("A1", temp.getId());
	list = storage.getList(null);
	assertEquals(2, list.length);
	
	storage.addItem(new TaskItem("CS2105 Programming Assignment 2", 3, new MyDate(13, 10, 2014)));	//Simulate add command
	list = storage.getList(null);
	assertEquals(3, list.length);
    }
    
    @Test
    public void testCompleteItem(){
	Storage storage = Storage.getInstance();
	ToDoItem item = storage.completeItem("A2");
	System.out.println(item);
	item = storage.undoComplete();
	
	System.out.println(item);
	System.out.println();
    }
    
    @Ignore
    public void testSorting(){
	Storage storage = Storage.getInstance();
	OptionType[] optionsList = new OptionType[1];
	optionsList[0] = OptionType.PRIORITY;
	ToDoItem[] list = storage.getList(optionsList);
	for(ToDoItem i: list){
	    System.out.println(i);
	}
	System.out.println();
	
	optionsList = new OptionType[2];
	optionsList[0] = OptionType.START_TIME;
	optionsList[1] = OptionType.PRIORITY;
	list = storage.getList(optionsList);
	for(ToDoItem i: list){
	    System.out.println(i);
	}
	System.out.println();
    }
}
