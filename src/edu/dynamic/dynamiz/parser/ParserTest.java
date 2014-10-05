package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test program to understand the output of parser.
 * @author zixian
 */

public class ParserTest {
    
    @Test
    public void test() {
	Parser parser = new Parser();
	CommandLine cmdLine = parser.parse("add task");
	assertEquals(" task", cmdLine.getParam());
	
	cmdLine = parser.parse("delete A1");
	assertEquals(" A1", cmdLine.getParam());
    }
    
}
