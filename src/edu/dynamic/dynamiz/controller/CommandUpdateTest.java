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
 * @author A0110781N
 */
public class CommandUpdateTest {
    
    @Test
    public void test() {
	Parser parser = Parser.getInstance();
	CommandUpdate cmd = (CommandUpdate) parser.parse("update 3 Midterm for CS2105 from 4/10/2014 16:00");
	cmd.execute();
	
	ToDoItem[] list = cmd.getAffectedItems();
	assertEquals(2, list.length);
	System.out.println(list[1]);
	cmd.undo();
	//Add more tests to check if undo() works.
    }
    
}
