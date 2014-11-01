package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * The JUnit Test Case for COmmandShow class.
 * @author zixian
 */
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
