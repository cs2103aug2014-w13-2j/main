package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class for CommandDo.
 * @author A0110781N
 */
public class CommandMarkTest {
    
    @Test
    //Tests normal case where all ID are valid.
    public void test() {
	int[] arr = new int[2];
	arr[0] = 6;
	arr[1] = 7;
	CommandMark cmd = new CommandMark(arr);
	cmd.execute();
	assertEquals(6, cmd.getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, cmd.getAffectedItems()[0].getStatus());
	assertEquals(7, cmd.getAffectedItems()[1].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, cmd.getAffectedItems()[1].getStatus());
	
	cmd.undo();
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[1].getStatus());
    }
    
    @Test
    //Tests cases where some ID are invalid.
    public void testSomeInvalidAndCompleted(){
	int[] arr = new int[3];
	arr[0] = -2;
	arr[1] = 4;
	arr[2] = 6;
	Command cmd = new CommandMark(arr);
	cmd.execute();
	assertEquals(1, cmd.getAffectedItems().length);
	((Undoable)cmd).undo();
	
    }
    
    @Test(expected=IllegalArgumentException.class)
    //Tests case when no item is marked as completed.
    public void testIllegalOperations(){
	//Invalid ID
	int[] arr = new int[2];
	arr[0] = -1;
	arr[1] = 25;
	Command cmd = new CommandMark(arr);
	cmd.execute();
	
	//Both items are already completed.
	arr[0] = 1;
	arr[1] = 3;
	cmd = new CommandMark(arr);
	cmd.execute();
	
	//Mixture of both cases.
	arr[1] = 25;
	cmd = new CommandMark(arr);
	cmd.execute();
    }
}
