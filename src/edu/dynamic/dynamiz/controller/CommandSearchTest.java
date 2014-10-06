package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Unit test for CommandSearch class.
 * @author zixian
 */
public class CommandSearchTest {
    
    @Test
    public void test() {
	Storage storage = new Storage();
	Parser parser = new Parser();
	CommandLine cmdLine = parser.parse("search CS");
	CommandSearch cmd = new CommandSearch(cmdLine.getParam(), cmdLine.getOptions(), storage);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	for(ToDoItem item: list){
	    System.out.println(item);
	}
	System.out.println();
    }
    
}
