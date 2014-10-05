package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.CommandType;

/**
 * Test program to understand the output of parser.
 * @author zixian
 */

public class ParserTest {
    
    @Test
    public void test() {
	Parser parser = new Parser();
	CommandLine cmdLine = parser.parse("add task");
	assertEquals("task", cmdLine.getParam());
	
	cmdLine = parser.parse("delete A1");
	assertEquals("A1", cmdLine.getParam());
	
	cmdLine = parser.parse("update A2 from 27/9/2014");
	assertEquals("A2", cmdLine.getParam());
	
	cmdLine = parser.parse("Search study from today");
	assertEquals("study", cmdLine.getParam());
	
	cmdLine = parser.parse("Show");
	assertEquals("", cmdLine.getParam());
	System.out.println(cmdLine);
	
	Options options = cmdLine.getOptions();
    }
    
    @Test
    public void testAliases() {
    	Parser parser = new Parser();
    	
    	CommandLine cmdLine = null;
    	String[] addCommands = {"add sth", "AdD sth", "PUT sth", "put sth", "pUt sth"};
    	
    	for (String c: addCommands) {
    		cmdLine = parser.parse(c);
    		assertEquals(CommandType.ADD, cmdLine.getCommandType());
    	}
    	
    	String[] delCommands = {"delete A1", "DEL A1", "reMoVe A1"};

    	for (String c: delCommands) {
    		cmdLine = parser.parse(c);
    		assertEquals(CommandType.DELETE, cmdLine.getCommandType());
    	}
    }
}
