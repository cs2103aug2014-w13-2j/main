package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandAddTest {
    
    @Test
    public void test() {
	Storage storage = new Storage();
	Parser parser = new Parser();
	
	CommandLine cmdLine = parser.parse("Add Learn C++");
	CommandAdd  cmd = new CommandAdd(cmdLine.getOptions(), cmdLine.getParam(), storage);
	cmd.execute();
	ToDoItem[] item = cmd.getAffectedItems();
	assertEquals("item has 1 object", 1, item.length);
	cmd.undo();
	
	cmdLine = parser.parse("add Learn C++ from 31/10/2014");
	System.out.println(cmdLine);
	cmd = new CommandAdd(cmdLine.getOptions(), cmdLine.getParam(), storage);
	cmd.execute();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalActions(){
	Storage storage = new Storage();
	Parser parser = new Parser();
	CommandLine cmdLine = parser.parse("add");
	CommandAdd cmd = new CommandAdd(cmdLine.getOptions(), cmdLine.getParam(), storage);
    }
}
