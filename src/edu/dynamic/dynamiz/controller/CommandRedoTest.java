package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandRedoTest {
    
    @Test
    public void test() {
	CommandAdd cmd = new CommandAdd(new ToDoItem("Learn C++"));
	Stack<Undoable> undoStack = new Stack<Undoable>();
	Stack<Undoable> redoStack = new Stack<Undoable>();
	cmd.execute();
	cmd.undo();
	redoStack.push((Undoable)cmd);
	CommandRedo redo = new CommandRedo();
	redo.setStacks(undoStack, redoStack);
	redo.execute();
    }
    
}
