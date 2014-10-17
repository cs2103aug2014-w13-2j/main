package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandAddTest {
    
    @Test
    public void test() {
	Parser parser = Parser.getInstance();
	
	CommandAdd cmd = (CommandAdd) parser.parse("Add Learn C++");
	// CommandAdd cmd = new CommandAdd(new ToDoItem("Learn C++"));
	cmd.execute();
	ToDoItem[] item = cmd.getAffectedItems();
	assertEquals("item has 1 object", 1, item.length);
	cmd.undo();
	
	cmd = (CommandAdd) parser.parse("add Learn C++ from 31/10/2014 12:00");
	// cmd = new CommandAdd(new EventItem("Learn C++", new DateTime(31, 10, 2014, 12, 0)));
	cmd.execute();
    }
    
    // TODO: Implement Exception for CommandAdd
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalActions(){
	Parser parser = Parser.getInstance();
    }
}
