package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Test unit for CommandUpdate class.
 */
//@author A0110781N
public class CommandUpdateTest {
    
    @Test
    public void test() {
	//Make a backup copy of dynamiz/todo.txt elsewhere as re-initialization of file
	//is needed to maintain integrity of file.
	Command cmd = new CommandUpdate(3, "Midterm for CS2105", -1, new MyDateTime(4, 10, 2014, 16, 0), new MyDateTime(4, 10, 2014, 17, 0));
	cmd.execute();
	
	ToDoItem[] list = cmd.getAffectedItems();
	assertEquals(2, list.length);
	assertEquals(3, cmd.getAffectedItems()[1].getId());
	assertEquals("Midterm for CS2105", cmd.getAffectedItems()[1].getDescription());
	assertEquals(cmd.getAffectedItems()[0].getPriority(), cmd.getAffectedItems()[1].getPriority());
	assertEquals(new MyDateTime(4, 10, 2014, 16, 0), ((EventItem)cmd.getAffectedItems()[1]).getStartDate());
	assertEquals(new MyDateTime(4, 10, 2014, 17, 0), ((EventItem)cmd.getAffectedItems()[1]).getEndDate());
	((Undoable)cmd).undo();	//To maintain integrity of dynamiz/todo.txt
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalDateUpdate(){
	//Any ID will do.
	Command cmd = new CommandUpdate(1, null, -1, new MyDate(10, 11, 2014), new MyDate(9, 11, 2014));
	cmd.execute();
	cmd = new CommandUpdate(8, null, -1, null, new MyDate(9, 9, 2014));
	cmd.execute();
	
	//Use and ID of an EventItem
	cmd = new CommandUpdate(8, null, -1, new MyDate(31, 12, 2014), null);
	cmd.execute();
    }
}
