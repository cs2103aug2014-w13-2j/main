package edu.dynamic.dynamiz.storage;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class StorageTest {
    
    @Test
    public void test() {
	Storage storage = Storage.getInstance();
	ToDoItem[] list = storage.getList(null);
	assertEquals(3, list.length);
	for(int i=0; i<3; i++)
	    System.out.println(list[i]);
	System.out.println();
	ToDoItem temp = storage.removeItem("A1");
	assertEquals("A1", temp.getId());
	list = storage.getList(null);
	assertEquals(2, list.length);
	storage.addItem(new TaskItem("CS2105 Programming Assignment 2", 3, new MyDate(13, 10, 2014)));
	list = storage.getList(null);
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
