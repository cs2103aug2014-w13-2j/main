package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;

/**
 * Defines JUnit Test Case for CommandDelete.
 * @author zixian
 */
public class CommandDeleteTest {
    
    @Test
    //Tests normal case
    public void test() {
	int[] arr = new int[3];
	arr[0] = 2;
	arr[1] = 4;
	arr[2] = 5;
	Command cmd = new CommandDelete(arr);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems()[0].getId());
	assertEquals(4, cmd.getAffectedItems()[1].getId());
	assertEquals(5, cmd.getAffectedItems()[2].getId());
	((Undoable)cmd).undo();
	cmd = new CommandShow(2);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems()[0].getId());
	cmd = new CommandShow(4);
	cmd.execute();
	assertEquals(4, cmd.getAffectedItems()[0].getId());
	cmd = new CommandShow(5);
	cmd.execute();
	assertEquals(5, cmd.getAffectedItems()[0].getId());
    }
    
    @Test
    //Tests the case where some of the ID is/are invalid or non-existent.
    public void testSomeInvalid(){
	int[] arr = new int[3];
	arr[0] = 2;
	arr[1] = 20;
	arr[2] = 5;
	Command cmd = new CommandDelete(arr);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems().length);
	assertEquals(2, cmd.getAffectedItems()[0].getId());
	assertEquals(5, cmd.getAffectedItems()[1].getId());
	((Undoable)cmd).undo();
    }
    
    @Test(expected=IllegalArgumentException.class)
    //Tests the case when all ID are invalid/non-existent.
    public void testAllInvalid(){
	int[] arr = new int[3];
	arr[0] = -1;
	arr[1] = 20;
	arr[2] = 35;
	Command cmd = new CommandDelete(arr);
	cmd.execute();
    }
}
