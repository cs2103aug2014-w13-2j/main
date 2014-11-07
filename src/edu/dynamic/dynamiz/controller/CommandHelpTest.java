package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test class for CommandHelp.
 */
//@author A0110781N
public class CommandHelpTest {
    
    @Test
    public void test() {
	CommandHelp cmd = new CommandHelp();
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.ADD);
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.DELETE);
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.LIST);
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.REDO);
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.SEARCH);
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.UNDO);
	cmd.execute();
	System.out.println(cmd.getContent());
	
	cmd = new CommandHelp(CommandType.UPDATE);
	cmd.execute();
	System.out.println(cmd.getContent());
    }
    
}
