package edu.dynamic.dynamiz.controller.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandAdd;
import edu.dynamic.dynamiz.controller.Undoable;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test case for Command Add.
 * @author A0110781N
 */
//@author A0110781N
public class CommandAddTest {
    
    @Test
    public void test() {
	Storage.getInstance();
	Command cmd = new CommandAdd(new ToDoItem("Learn C++"));
	cmd.execute();
	assertEquals(1, cmd.getAffectedItems().length);
	assertEquals("Learn C++", cmd.getAffectedItems()[0].getDescription());
	((Undoable)cmd).undo();	//To clean up the todo.txt
	
	cmd = new CommandAdd(new EventItem("Learn C++", new MyDateTime(31, 10, 2014, 12, 0), new MyDateTime(11, 11, 2014, 12, 0)));
	cmd.execute();
	assertEquals(new MyDateTime(31, 10, 2014, 12, 0), ((EventItem)cmd.getAffectedItems()[0]).getStartDate());
	assertEquals(new MyDateTime(11, 11, 2014, 12, 0), ((EventItem)cmd.getAffectedItems()[0]).getEndDate());
	((Undoable)cmd).undo();	//To clean up todo.txt
	
	cmd = new CommandAdd(new TaskItem("PS7R", new MyDate(13, 11, 2014)));
	cmd.execute();
	assertEquals(new MyDate(13, 11, 2014), ((TaskItem)cmd.getAffectedItems()[0]).getDeadline());
	((Undoable)cmd).undo();
    }
}
