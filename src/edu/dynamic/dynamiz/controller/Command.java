package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.ToDoItem;

public interface Command {
	void execute();
	void undo();
	String getCommandName();
	ToDoItem[] getAffectedItems();
}
