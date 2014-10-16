package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class CommandUndoTest {
    
    @Test
    public void test() {
	CommandDo cmd = new CommandDo("A1");
	cmd.execute();
	Stack<Undoable> undoStack = new Stack<Undoable>();
	Stack<Undoable> redoStack = new Stack<Undoable>();
	undoStack.push((Undoable)cmd);
	System.out.println(cmd.getAffectedItems()[0]);
	CommandUndo undo = new CommandUndo();
	undo.setStacks(undoStack, redoStack);
	undo.execute();
	System.out.println(undo.getAffectedItems()[0]);
    }
    
}
