package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.structure.MyDate;

/**
 * Test program to understand the output of parser.
 * @author zixian
 */

public class ParserTest {
    
    @Test
    public void test() {
		Parser parser = Parser.getInstance();
		CommandLine cmdLine = parser.parseCommandLine("add task");
		assertEquals("task", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("delete A1");
		assertEquals("A1", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("add A2 from 27/9/2014 18:00");
		assertEquals("A2", cmdLine.getParam());

		//cmdLine = parser.parseCommandLine("Search study from today");
		//assertEquals("study", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("list -s tomorrow today yesterday orderby -s to priority");
		assertEquals("", cmdLine.getParam());
		System.out.println(cmdLine);
		
		
		Options options = cmdLine.getOptions();
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
    	
    	String[] delCommands = {"delete A1", "DEL A1", "reMoVe A1"};

    	for (String c: delCommands) {
    		cmdLine = parser.parseCommandLine(c);
    		assertEquals(CommandType.DELETE, cmdLine.getCommandType());
    	}
    }
    
    @Test
    public void testParsingDate() {
    	Parser parser = Parser.getInstance();
    	
    	MyDate date = parser.parseDate("17.10.2014");
    	assertEquals("17/10/2014", date.toString());
    	
    	date = parser.parseDate("end of October 4pm");
    	assertEquals("31/10/2014 16:00", date.toString());
    	
    	date = parser.parseDate("next Thursday");
    	
    }
    
    @Test
    public void testListCommand(){
	Parser parser = Parser.getInstance();
	parser.parse("list");
	parser.parse("list orderby -s");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidCommand(){
	Parser parser = Parser.getInstance();
	parser.parse("ls");
    }
    
    @Test
    public void testSearch(){
	Parser parser = Parser.getInstance();
	parser.parse("search CS");
    }
}
