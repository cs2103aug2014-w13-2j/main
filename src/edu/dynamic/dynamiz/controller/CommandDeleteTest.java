package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;

public class CommandDeleteTest {
    
    @Test
    public void test() {
	Parser parser = Parser.getInstance();
	//CommandDelete cmd = parser.parse("delete A1");
	CommandDelete cmd = new CommandDelete("A1");
	cmd.execute();
	assertEquals("A1", cmd.getAffectedItems()[0].getId());
	cmd.undo();
    }
    
}
