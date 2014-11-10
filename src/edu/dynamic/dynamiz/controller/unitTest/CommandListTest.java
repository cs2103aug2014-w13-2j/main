package edu.dynamic.dynamiz.controller.unitTest;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.Command;
import edu.dynamic.dynamiz.controller.CommandList;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class to test CommandList class.
 */
//@author A0110781N
public class CommandListTest {
    
    @Test
    public void testList(){
	OptionType[] optList = new OptionType[2];
	optList[0] = OptionType.PRIORITY;
	optList[1] = OptionType.START_TIME;
	Command cmd = new CommandList(null, null, null, optList);	//Sort by priority, then by start time.
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	
	optList = new OptionType[1];
	optList[0] = OptionType.END_TIME;
	cmd = new CommandList(null, null, null, optList);	//Sort in ascending order of end time.
	cmd.execute();
	list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
    }
    
    @Test
    public void testFilter(){
	int[] pri = new int[2];
	pri[0] = 2;
	pri[1] = 4;
	MyDate[] end = new MyDate[2];
	end[0] = new MyDate(29, 10, 2014);
	end[1] = new MyDate(1, 11, 2014);
	Command cmd = new CommandList(pri, null, end, null);	//filter by priority and end date.
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
    }
}
