package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

public abstract class Command {
    //Data members
    protected Storage storage;	//The storage object to operate on.
    
    //Method signatures
    public abstract void execute();
    public abstract void redo();
    public abstract void undo();
    public abstract String getCommandName();
    public abstract ToDoItem[] getAffectedItems();
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
}
