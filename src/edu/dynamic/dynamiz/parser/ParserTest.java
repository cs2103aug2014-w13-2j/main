package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import edu.dynamic.dynamiz.controller.CommandType;
import edu.dynamic.dynamiz.structure.MyDate;

//@author A0113855E
/**
 * Test program to understand the output of parser.
 * @author nhan
 */

public class ParserTest {
	private static Parser parser = Parser.getInstance();
	
    @Test
    public void test() {
		CommandLine cmdLine = parser.parseCommandLine("add task");
		assertEquals("task", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("delete 1");
		assertEquals("1", cmdLine.getParam());

		cmdLine = parser.parseCommandLine("add 2 on 27/9/2014 from yesterday 18:00 to 23:00");
		assertEquals("2", cmdLine.getParam());
		
		List<String> values = new ArrayList<String>();
		values.add(parser.parseMyDate("27/9/2014").toString());
		Option opt = new Option(OptionType.ON_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		values = new ArrayList<String>();
		values.add(parser.parseMyDate("yesterday 18:00").toString());
		opt = new Option(OptionType.START_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		values = new ArrayList<String>();
		values.add(parser.parseMyDate("23:00").toString());
		opt = new Option(OptionType.END_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		

		cmdLine = parser.parseCommandLine("list -s tomorrow,today,yesterday orderby -s,to,priority");
		assertEquals("", cmdLine.getParam());
		
		values = new ArrayList<String>();
		values.add(parser.parseMyDate("tomorrow").toString());
		values.add(parser.parseMyDate("today").toString());
		values.add(parser.parseMyDate("yesterday").toString());
		opt = new Option(OptionType.START_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		values = new ArrayList<String>();
		values.add(OptionType.fromString("-s").toString());
		values.add(OptionType.fromString("to").toString());
		values.add(OptionType.fromString("priority").toString());			
		opt = new Option(OptionType.ORDER_BY, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		cmdLine = parser.parseCommandLine("update 3 priority h on tomorrow from 18:00 to 23:00");
		assertEquals("3", cmdLine.getParam());
		
		values = new ArrayList<String>();
		values.add(OptionType.getPriority("h") + "");
		opt = new Option(OptionType.PRIORITY, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		values = new ArrayList<String>();
		values.add(parser.parseMyDate("tomorrow").toString());
		opt = new Option(OptionType.ON_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		values = new ArrayList<String>();
		values.add(parser.parseMyDate("18:00").toString());
		opt = new Option(OptionType.START_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		values = new ArrayList<String>();
		values.add(parser.parseMyDate("23:00").toString());
		opt = new Option(OptionType.END_TIME, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
		
		cmdLine = parser.parseCommandLine("search status p");
		assertEquals("", cmdLine.getParam());
		
		values = new ArrayList<String>();
		values.add(OptionType.getStatus("p").toString());
		opt = new Option(OptionType.STATUS, values);
		assertTrue(cmdLine.getOptions().hasOption(opt));
    }
    
    @Test
    public void testMultipleOption() {
    	CommandLine cmdLine;
    	Option opt = null;
    	List<String> values = new ArrayList<String>();
    	
    	cmdLine = parser.parseCommandLine("add a task from today from yesterday from tomorrow");
    	MyDate date = parser.parseMyDate("tomorrow");
    	values.add(date.toString());
    	opt = new Option(OptionType.START_TIME, values);
    	
    	assertEquals("a task from today from yesterday", cmdLine.getParam());
    	assertTrue(cmdLine.getOptions().hasOption(opt));
    	
    	cmdLine = parser.parseCommandLine("add a task from today from yesterday to tomorrow");
    	values = new ArrayList<String>();
    	date = parser.parseMyDate("yesterday");
    	values.add(date.toString());
    	opt = new Option(OptionType.START_TIME, values);
    	
    	assertEquals("a task from today", cmdLine.getParam());
    	assertTrue(cmdLine.getOptions().hasOption(opt));
    	
    	values = new ArrayList<String>();
    	date = parser.parseMyDate("tomorrow");
    	values.add(date.toString());
    	opt = new Option(OptionType.END_TIME, values);
    	
    	assertTrue(cmdLine.getOptions().hasOption(opt));
    }
    
    @Test
    public void testEscapingInputNormal() {
    	CommandLine cmdLine;
    	
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
    public void testAliasesNormal() {
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
    
    @Test(expected = NullPointerException.class)
    public void testAliasesErroneous() {
    	CommandLine cmdLine = parser.parseCommandLine("sadfasdf");
    	
    	fail("It should throw an Exception");
    }
    
    /**
     * Test on natty parsing capacity
     */
    @Test
    public void testParsingDate() {
    	
    	// Explicit date expressions
    	MyDate date = parser.parseMyDate("17/10/2014");
    	assertEquals("17/10/2014", date.toString());
    	
    	date = parser.parseMyDate("end of October 4pm");
    	assertEquals("31/10/2014 16:00", date.toString());
    	
    	// Relative date expressions
    	MutableDateTime today = new MutableDateTime();
    	today.setDayOfMonth(today.getDayOfMonth()-1);
    	today.setHourOfDay(18);
    	today.setMinuteOfHour(0);
    	
    	DateTimeFormatter formatter = DateTimeFormat.forPattern("d/MM/yyyy HH:mm");
    	String expected = formatter.print(today);
    	date = parser.parseMyDate("yesterday 18:00");
    	assertEquals(expected, date.toString());
    }

    @Test
    public void testParseDateListFromRangeNormal() {
    	DateTime today = new DateTime();
    	int yy = today.getYear();
    	
    	// Common case
    	String dateRange = "12 Nov > 15 Nov";
    	
    	List<String> expected = new ArrayList<String>();
    	expected.add(new MyDate(12,11,yy).toString());
    	expected.add(new MyDate(13,11,yy).toString());
    	expected.add(new MyDate(14,11,yy).toString());
    	expected.add(new MyDate(15,11,yy).toString());
    	
    	assertEquals(expected, parser.parseDateListFromRange(dateRange));
    	
    	// Corner case: Month crossing
    	dateRange = "29 Nov> 3 Dec";
    	
    	expected.clear();
    	expected.add(new MyDate(29, 11, yy).toString());
    	expected.add(new MyDate(30, 11, yy).toString());
    	expected.add(new MyDate(1,12,yy).toString());
    	expected.add(new MyDate(2,12,yy).toString());
    	expected.add(new MyDate(3,12,yy).toString());
    	
    	assertEquals(expected, parser.parseDateListFromRange(dateRange));
    	
    	// Corner case: Year crossing
    	dateRange = "30 Dec 2014 >2 January 2015";
    	
    	expected.clear();
    	
    	yy = 2014;
    	expected.add(new MyDate(30, 12, yy).toString());
    	expected.add(new MyDate(31, 12, yy).toString());
    	expected.add(new MyDate(1, 1, yy + 1).toString());
    	expected.add(new MyDate(2, 1, yy + 1).toString());
   
    	assertEquals(expected, parser.parseDateListFromRange(dateRange));	
    }
   
    /*======================================================================
     * Parse Exception throwing
     * =====================================================================
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParsingDateRangeValidExpressionButInvalidRange() {
    	String dateRange = "30 Dec > 3 Jan";
    	parser.parseDateListFromRange(dateRange);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParsingDateRangeInvalidExpression() {
    	String dateRange = ">30 Dec";
    	parser.parseDateListFromRange(dateRange);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParsingDateRangeInvalidSyntax() {
    	String dateRange = "tomorrow > today > yesterday";
    	parser.parseDateListFromRange(dateRange);
    }
}
