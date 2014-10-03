package edu.dynamic.dynamiz.controller;

public interface Command {
	void execute();
	void undo();
	void redo();
	void save();
	String getCommandName();
}
