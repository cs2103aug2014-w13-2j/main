package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.structure.DateTime;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Test unit for CommandUpdate class.
 * @author zixian
 */
public class CommandUpdateTest {
    
    @Test
    public void test() {
	Parser parser = Parser.getInstance();
	parser.parse("update A3 Midterm for CS2105 from 4/10/2014 16:00");
	CommandUpdate cmd = new CommandUpdate("A3", "Midterm for CS2105", -1, new DateTime(4, 10, 2014, 16, 0), null);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	assertEquals(2, list.length);
	System.out.println(list[1]);
	cmd.undo();
	//Add more tests to check if undo() works.
    }
    
}
