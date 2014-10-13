package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;

public class CommandDeleteTest {
    
    @Test
    public void test() {
	Parser parser = new Parser();
	CommandLine cmdLine = parser.parse("delete A1");
	CommandDelete cmd = new CommandDelete(cmdLine.getParam());
	cmd.execute();
	assertEquals("A1", cmd.getAffectedItems()[0].getId());
	cmd.undo();
    }
    
}
