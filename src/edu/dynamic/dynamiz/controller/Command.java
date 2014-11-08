package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0110781N
/**
 * Defines the Command superclass.
 */
public abstract class Command {
    //Data members
    protected Storage storage = Storage.getInstance();	//The storage object to operate on.
    
    //Method signatures
    public abstract void execute();
    public abstract String getCommandName();
    public abstract ToDoItem[] getAffectedItems();
}
