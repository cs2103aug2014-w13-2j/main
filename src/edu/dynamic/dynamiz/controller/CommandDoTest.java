package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test class for CommandDo.
 * @author zixian
 */
public class CommandDoTest {
    
    @Test
    public void test() {
	CommandDo cmd = new CommandDo(2);
	cmd.execute();
	System.out.println(cmd.getAffectedItems()[0]);
	
	cmd.undo();
	System.out.println(cmd.getAffectedItems()[0]);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalOperations(){
	Command cmd = new CommandDo(4);
	cmd.execute();
	assertTrue(cmd.getAffectedItems()==null);
    }
}
