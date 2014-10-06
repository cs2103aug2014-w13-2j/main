package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandListTest {
    
    @Test
    public void test() {
	CommandList cmd = new CommandList(new Storage());
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	assertEquals(3, list.length);
	for(ToDoItem item: list){
	    System.out.println(item);
	}
    }
    
}
