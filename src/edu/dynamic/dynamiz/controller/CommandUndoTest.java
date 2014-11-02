package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the JUnit Test Case for CommandUndo.
 * The todo file must be re-initialized before every run here.
 * Change the value 8 to another ID in the file with status that is not
 * 'completed' as not doing so results in exceptions during testing.
 * @author zixian
 */
public class CommandUndoTest {
    
    @Test
    public void test() {
	int[] arr = new int[1];
	arr[0] = 8;
	Command cmd = new CommandMark(arr);
	cmd.execute();
	Stack<Undoable> undoStack = new Stack<Undoable>();
	Stack<Undoable> redoStack = new Stack<Undoable>();
	undoStack.push((Undoable)cmd);
	assertEquals(8, cmd.getAffectedItems()[0].getId());
	assertTrue(cmd.getAffectedItems()[0].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	CommandUndo undo = new CommandUndo();
	undo.setStacks(undoStack, redoStack);
	undo.execute();
	assertEquals(8, cmd.getAffectedItems()[0].getId());
	assertTrue(cmd.getAffectedItems()[0].getStatus().equals(ToDoItem.STATUS_PENDING));
	
	cmd = new CommandUpdate(2, null, 2, null, null);
	cmd.execute();
	assertEquals(2, cmd.getAffectedItems()[0].getId());
	assertEquals(2, cmd.getAffectedItems()[1].getPriority());
	undoStack.push((Undoable)cmd);
	undo.execute();
	assertEquals(4, cmd.getAffectedItems()[1].getPriority());
	
    }
    
}
