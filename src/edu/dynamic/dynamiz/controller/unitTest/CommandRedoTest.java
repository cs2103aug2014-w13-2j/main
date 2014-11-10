package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.CommandRedo;
import edu.dynamic.dynamiz.controller.CommandUndo;
import edu.dynamic.dynamiz.controller.Undoable;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test case for CommandRedo.
 */
//@author A0110781N
public class CommandRedoTest {
    
    @Test
    public void test() {
	Command cmd = new CommandAdd(new ToDoItem("Learn C++"));
	Stack<Undoable> undoStack = new Stack<Undoable>();
	Stack<Undoable> redoStack = new Stack<Undoable>();
	cmd.execute();
	((Undoable)cmd).undo();
	redoStack.push((Undoable)cmd);
	cmd = new CommandRedo();
	((CommandRedo)cmd).setStacks(undoStack, redoStack);
	cmd.execute();
	assertEquals("Learn C++", cmd.getAffectedItems()[0].getDescription());
	cmd = new CommandUndo();
	((CommandUndo)cmd).setStacks(undoStack, redoStack);	//To maintain integrity of dynamiz/todo.txt
	cmd.execute();
    }
    
}
