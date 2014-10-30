package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test Case to test correctness of CommandUnmark.
 * @author zixian
 */
public class CommandUnmarkTest {
    
    @Test
    public void test() {
	Command cmd = new CommandMark(1);
	cmd.execute();
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, cmd.getAffectedItems()[0].getStatus());
	
	cmd = new CommandUnmark(1);
	cmd.execute();
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
	
	((Undoable)cmd).undo();
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, cmd.getAffectedItems()[0].getStatus());
	
	((Undoable)cmd).redo();
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
    }
    
}
