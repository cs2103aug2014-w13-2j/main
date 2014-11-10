package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandSearch;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Unit test for CommandSearch class.
 */
//@author A0110781N
public class CommandSearchTest {
    
    @Test
    public void test() {
	Command cmd = new CommandSearch("CS", -1, null, null, ToDoItem.STATUS_PENDING, null);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	assertTrue(list!=null);
	for(ToDoItem i: list){
	    assertEquals(ToDoItem.STATUS_PENDING, i.getStatus());
	}
    }
    
}
