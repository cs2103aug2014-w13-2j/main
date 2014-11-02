package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class for CommandDo.
 * @author zixian
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
    public void testSomeInvalid(){
	
    }
    
    @Test
    //Tests cases where some ID are already completed.
    public void testSomeCompleted(){
	
    }
    
    @Ignore
    //Test cases where all ID are already completed.
    public void testAllCompleted(){
	int[] arr = new int[2];
	arr[0] = 1;
	arr[1] = 3;
	Command cmd = new CommandMark(arr);
	cmd.execute();
	assertTrue(null==cmd.getAffectedItems());
    }
    
    @Test(expected=IllegalArgumentException.class)
    //Tests case when all ID are invalid/non-existent.
    public void testIllegalOperations(){
	int[] arr = new int[2];
	arr[0] = -1;
	arr[1] = 25;
	Command cmd = new CommandMark(arr);
	cmd.execute();
    }
}
