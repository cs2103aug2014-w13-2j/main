package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Unit test for CommandSearch class.
 * @author zixian
 */
public class CommandSearchTest {
    
    @Test
    public void test() {
	CommandSearch cmd = new CommandSearch("CS", -1, null, new MyDate(29, 9, 2014));
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	System.out.println(list.length);
	for(ToDoItem item: list){
	    System.out.println(item);
	}
	System.out.println();
    }
    
}
