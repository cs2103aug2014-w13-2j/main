package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandDoTest {
    
    @Test
    public void test() {
	CommandDo cmd = new CommandDo("A2");
	cmd.execute();
	System.out.println(cmd.getAffectedItems()[0]);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalOperations(){
	Command cmd = new CommandDo("A4");
	cmd.execute();
	assertTrue(cmd.getAffectedItems()==null);
    }
    
}
