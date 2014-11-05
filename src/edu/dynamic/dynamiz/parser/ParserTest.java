package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
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
		
		cmdLine = parser.parseCommandLine("update 3 priority h on tomorrow from 18:00 to 23:00");
		
		cmdLine = parser.parseCommandLine("search status p");
		System.out.println(cmdLine);
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
    	
    	MyDate date = parser.parseMyDate("17/10/2014");
    	assertEquals("17/10/2014", date.toString());
    	
    	date = parser.parseMyDate("end of October 4pm");
    	assertEquals("31/10/2014 16:00", date.toString());
    	
    	date = parser.parseMyDate("yesterday 18:00");
    }
    
    @Test
    public void testParseAndExtractOption() {
    	
    }
    
    @Test
    public void testParseDateListFromRange() {
    	Parser parser = Parser.getInstance();
    	DateTime today = new DateTime();
    	int yy = today.getYear();
    	
    	// Common case
    	String dateRange = "12 Nov - 15 Nov";
    	
    	List<MyDate> expected = new ArrayList<MyDate>();
    	expected.add(new MyDate(12,11,yy));
    	expected.add(new MyDate(13,11,yy));
    	expected.add(new MyDate(14,11,yy));
    	expected.add(new MyDate(15,11,yy));
    	
    	assertEquals(expected, parser.parseDateListFromRange(dateRange));
    	
    	// Corner case: Month crossing
    	dateRange = "29 Nov - 3 Dec";
    	
    	expected.clear();
    	expected.add(new MyDate(29, 11, yy));
    	expected.add(new MyDate(30, 11, yy));
    	expected.add(new MyDate(1,12,yy));
    	expected.add(new MyDate(2,12,yy));
    	expected.add(new MyDate(3,12,yy));
    	
    	assertEquals(expected, parser.parseDateListFromRange(dateRange));
    	
    	// Corner case: Year crossing
    	dateRange = "30 Dec 2014- 2 January 2015";
    	
    	expected.clear();
    	
    	yy = 2014;
    	expected.add(new MyDate(30, 12, yy));
    	expected.add(new MyDate(31, 12, yy));
    	expected.add(new MyDate(1, 1, yy + 1));
    	expected.add(new MyDate(2, 1, yy + 1));
   
    	assertEquals(expected, parser.parseDateListFromRange(dateRange));
    }
}
