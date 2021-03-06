package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandMark;
import edu.dynamic.dynamiz.controller.CommandUndo;
import edu.dynamic.dynamiz.controller.CommandUpdate;
import edu.dynamic.dynamiz.controller.Undoable;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the JUnit Test Case for CommandUndo.
 * The todo file must be re-initialized before every run here.
 * Change the value 8 to another ID in the file with status that is not
 * 'completed' as not doing so results in exceptions during testing.
 */
//@author A0110781N
public class CommandUndoTest {
    
    @Test
    public void test() {
	//For mark command, use an ID that can be successfully marked as "Completed" in dynamiz/todo.txt,
	//or the test case will not work.
	int[] arr = new int[1];
	arr[0] = 2;
	Command cmd = new CommandMark(arr);
	cmd.execute();
	Stack<Undoable> undoStack = new Stack<Undoable>();
	Stack<Undoable> redoStack = new Stack<Undoable>();
	undoStack.push((Undoable)cmd);
	assertEquals(2, cmd.getAffectedItems()[0].getId());
	assertTrue(cmd.getAffectedItems()[0].getStatus().equals(ToDoItem.STATUS_COMPLETED));
	CommandUndo undo = new CommandUndo();
	undo.setStacks(undoStack, redoStack);
	undo.execute();
	assertEquals(2, cmd.getAffectedItems()[0].getId());
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
