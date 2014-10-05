package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandAddTest {
    
    @Test
    public void test() {
	Storage storage = new Storage();
	CommandAdd  cmd = new CommandAdd(new Options(), "new task", storage);
	cmd.execute();
	ToDoItem[] item = cmd.getAffectedItems();
	assertEquals("item has 1 object", 1, item.length);
    }
    
}
