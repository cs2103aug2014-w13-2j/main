package edu.dynamic.dynamiz.controller;

/**
 * Interface for commands that implements undo and redo functionalities.
 * @author A0110781N
 */
public interface Undoable {
    void undo();
    void redo();
}
