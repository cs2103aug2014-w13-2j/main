package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandMark;
import edu.dynamic.dynamiz.controller.Undoable;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class for CommandDo.
 */
//@author A0110781N
public class CommandMarkTest {
    
    @Test
    //Tests normal case where all ID are valid.
    //Change the ID based on the contents of dynamiz/todo.txt.
    //Items represented by the ID must be "Pending" as this test case is for all valid ID.
    public void test() {
	int[] arr = new int[2];
	arr[0] = 2;
	arr[1] = 3;
	CommandMark cmd = new CommandMark(arr);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems()[0].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, cmd.getAffectedItems()[0].getStatus());
	assertEquals(3, cmd.getAffectedItems()[1].getId());
	assertEquals(ToDoItem.STATUS_COMPLETED, cmd.getAffectedItems()[1].getStatus());
	
	cmd.undo();
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[0].getStatus());
	assertEquals(ToDoItem.STATUS_PENDING, cmd.getAffectedItems()[1].getStatus());
    }
    
    @Test
    //Tests cases where some ID are invalid.
    //For the ID, make sure that 2 of them can be marked as completed.
    //Else the test file will screw up, causing subsequent runs to fail.
    public void testSomeInvalidAndCompleted(){
	int[] arr = new int[3];
	arr[0] = -2;
	arr[1] = 4;
	arr[2] = 6;
	Command cmd = new CommandMark(arr);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems().length);
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
