package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.CommandLine;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test case for Command Add.
 * @author A0110781N
 */
public class CommandAddTest {
    
    @Test
    public void test() {
	
	Command cmd = new CommandAdd(new ToDoItem("Learn C++"));
	cmd.execute();
	assertEquals(1, cmd.getAffectedItems().length);
	assertEquals("Learn C++", cmd.getAffectedItems()[0].getDescription());
	//((Undoable)cmd).undo();
	
	cmd = new CommandAdd(new EventItem("Learn C++", new MyDateTime(31, 10, 2014, 12, 0), new MyDateTime(11, 11, 2014, 12, 0)));
	cmd.execute();
	assertEquals(new MyDateTime(31, 10, 2014, 12, 0), ((EventItem)cmd.getAffectedItems()[0]).getStartDate());
	assertEquals(new MyDateTime(11, 11, 2014, 12, 0), ((EventItem)cmd.getAffectedItems()[0]).getEndDate());
	//((Undoable)cmd).undo();
    }
    
    // TODO: Implement Exception for CommandAdd
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalActions(){
	Parser parser = Parser.getInstance();
    }
}
