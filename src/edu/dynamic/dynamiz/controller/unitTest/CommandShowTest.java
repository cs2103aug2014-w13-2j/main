package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandShow;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * The JUnit Test Case for COmmandShow class.
 */
//@author A0110781N
public class CommandShowTest {
    
    @Test
    public void test() {
	Command cmd = new CommandShow(1);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	assertEquals(1, list.length);
	assertEquals(1, list[0].getId());
    }
    
}
