package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Test unit for CommandUpdate class.
 * @author zixian
 */
public class CommandUpdateTest {
    
    @Test
    public void test() {
	Parser parser = new Parser();
	Storage storage = new Storage();
	CommandLine cmdLine = parser.parse("update A2 from 38h40");
	CommandUpdate cmd = new CommandUpdate(cmdLine.getParam(), new Options(), storage);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	assertEquals(2, list.length);
	System.out.println(list[1]);
	cmd.undo();
	//Add more tests to check if undo() works.
    }
    
}
