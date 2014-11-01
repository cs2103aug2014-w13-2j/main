package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.structure.MyDate;

/**
 * Test program to understand the output of parser.
 * @author zixian
 * @author nhan
 */

public class ParserTest {
    
    @Test
    public void test() {
		Parser parser = Parser.getInstance();
		CommandLine cmdLine = parser.parseCommandLine("add task");
		assertEquals("task", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("delete 1");
		assertEquals("1", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("add 2 on 27/9/2014 from yesterday 18:00 to 23:00");
		assertEquals("2", cmdLine.getParam());

		//cmdLine = parser.parseCommandLine("Search study from today");
		//assertEquals("study", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("list -s tomorrow,today,yesterday orderby -s,to,priority");
		assertEquals("", cmdLine.getParam());
		System.out.println(cmdLine);
		
		cmdLine = parser.parseCommandLine("update 3 priority h on tomorrow from 18:00 to 23:00");
    }
    
    @Test
    public void testMultipleOption() {
    	Parser parser = Parser.getInstance();
    	CommandLine cmdLine;
    	
    	cmdLine = parser.parseCommandLine("add a task from today from yesterday from tomorrow");
    	assertEquals("a task from today from yesterday", cmdLine.getParam());
    	
    	cmdLine = parser.parseCommandLine("add a task from today from yesterday to tomorrow");
    	assertEquals("a task from today", cmdLine.getParam());
    }
    
    @Test
    public void testEscapingInput() {
    	Parser parser = Parser.getInstance();
    	CommandLine cmdLine;
    	
    	// TODO: Add assertEquals for Option parsed as well
    	// With normal OptionType
    	cmdLine = parser.parseCommandLine("add ;from Fri ;to Sat ;on 12 Dec from yesterday to today");
    	assertEquals("from Fri to Sat on 12 Dec", cmdLine.getParam());
    	
    	// With ORDER_BY OptionType
    	cmdLine = parser.parseCommandLine("search ;orderby -s from yesterday orderby -s");
    	assertEquals("orderby -s", cmdLine.getParam());
    	
    	// With both normal OptionType and ORDER_BY
    	cmdLine = parser.parseCommandLine("s ;orderby -s ;from today 2am ;priority high from tomorrow -p u orderby -s");
    	assertEquals("orderby -s from today 2am priority high", cmdLine.getParam());
    	
    	// Escape aliases with preceding '-' character
    	cmdLine = parser.parseCommandLine("s ;-s tomorrow -s today");
    	assertEquals("-s tomorrow", cmdLine.getParam());
    }
    
    @Test
    public void testAliases() {
    	Parser parser = Parser.getInstance();
    	
    	CommandLine cmdLine = null;
    	String[] addCommands = {"add sth", "AdD sth", "PUT sth", "put sth", "pUt sth"};
    	
    	for (String c: addCommands) {
    		cmdLine = parser.parseCommandLine(c);
    		assertEquals(CommandType.ADD, cmdLine.getCommandType());
    	}
    	
    	String[] delCommands = {"delete 1", "DEL 1", "reMoVe 1"};

    	for (String c: delCommands) {
    		cmdLine = parser.parseCommandLine(c);
    		assertEquals(CommandType.DELETE, cmdLine.getCommandType());
    	}
    }
    
    @Test
    public void testParsingDate() {
    	Parser parser = Parser.getInstance();
    	
    	MyDate date = parser.parseDate("17/10/2014");
    	assertEquals("17/10/2014", date.toString());
    	
    	date = parser.parseDate("end of October 4pm");
    	assertEquals("31/10/2014 16:00", date.toString());
    	
    	date = parser.parseDate("yesterday 18:00");
    }
    
    @Test
    public void testParseAndExtractOption() {
    	
    }
}
