package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandDoTest {
    
    @Test
    public void test() {
	CommandDo cmd = new CommandDo("A1");
	cmd.execute();
	System.out.println(cmd.getAffectedItems()[0]);
	
	cmd = new CommandDo("A4");
	cmd.execute();
	assertTrue(cmd.getAffectedItems()==null);
    }
    
}
