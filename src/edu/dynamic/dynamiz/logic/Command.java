package edu.dynamic.dynamiz.logic;

public interface Command {
	void execute();
	void undo();
	void redo();
	void save();
	String getCommandName();
}
