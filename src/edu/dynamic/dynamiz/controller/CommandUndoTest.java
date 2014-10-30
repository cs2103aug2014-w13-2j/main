package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandUndoTest {
    
    @Test
    public void test() {
	Command cmd = new CommandMark(2);
	cmd.execute();
	Stack<Undoable> undoStack = new Stack<Undoable>();
	Stack<Undoable> redoStack = new Stack<Undoable>();
	undoStack.push((Undoable)cmd);
	System.out.println(cmd.getAffectedItems()[0]);
	CommandUndo undo = new CommandUndo();
	undo.setStacks(undoStack, redoStack);
	undo.execute();
	System.out.println(undo.getAffectedItems()[0]);
	System.out.println();
	
	cmd = new CommandUpdate(2, null, 4, null, null);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	undoStack.push((Undoable)cmd);
	undo.execute();
	list = undo.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	
    }
    
}
