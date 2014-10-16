package edu.dynamic.dynamiz.controller;

/**
 * Interface for commands that implements undo and redo functionalities.
 * @author zixian
 */
public interface Undoable {
    void undo();
    void redo();
}
