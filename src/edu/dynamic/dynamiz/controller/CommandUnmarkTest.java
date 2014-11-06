package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test Case to test correctness of CommandUnmark.
 * For all values used in the arrays, please check through the todo.txt to be used for testing to
 * ensure that the IDs used really represent the types of items to be used for testing.
 * Else, false errors will appear.
 * @author A0110781N
 */
public class CommandUnmarkTest {
    @Test
    public void test() {
	//Tests normal use case.
	int[] arr = new int[3];
	arr[0] = 1;
	arr[1] = 3;
	arr[2] = 4;
	Command cmd = new CommandUnmark(arr);
	cmd.execute();
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(3, cmd.getAffectedItems()[1].getId());
	assertEquals(4, cmd.getAffectedItems()[2].getId());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[1].getStatus());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[2].getStatus());
	((Undoable)cmd).undo();
	assertTrue(cmd.getAffectedItems()[0].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	assertTrue(cmd.getAffectedItems()[1].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	assertTrue(cmd.getAffectedItems()[2].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	
	//Test case where some of the items are already not marked as completed.
	arr[1] = 2;
	cmd = new CommandUnmark(arr);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems().length);
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(4, cmd.getAffectedItems()[1].getId());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[1].getStatus());
	((Undoable)cmd).undo();
	assertTrue(cmd.getAffectedItems()[0].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	assertTrue(cmd.getAffectedItems()[1].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	
	//Test case where some of the ID are invalid.
	arr[1] = -1;
	cmd = new CommandUnmark(arr);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems().length);
	assertEquals(1, cmd.getAffectedItems()[0].getId());
	assertEquals(4, cmd.getAffectedItems()[1].getId());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[1].getStatus());
	((Undoable)cmd).undo();
	assertTrue(cmd.getAffectedItems()[0].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	assertTrue(cmd.getAffectedItems()[1].getStatus().equals(ToDoItem.STATUS_COMPLETED));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidCases(){
	//Test case where all are invlaid ID.
	int[] arr = new int[3];
	arr[0] = -1;
	arr[1] = 20;
	arr[2] = 100;
	Command cmd = new CommandUnmark(arr);
	cmd.execute();
	
	//Test case where all items are already marked as not 'completed'.
	arr[0] = 2;
	arr[1] = 6;
	arr[2] = 8;
	cmd = new CommandUnmark(arr);
	cmd.execute();
	
	//Test case where all ID are either of the 1st error type or the 2nd error type.
	arr[1] = -1;
	cmd = new CommandUnmark(arr);
	cmd.execute();
    }
}
