package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * JUnit test class to test CommandList class.
 * @author zixian
 */
public class CommandListTest {
    
    @Test
    public void testList(){
	OptionType[] optList = new OptionType[2];
	optList[0] = OptionType.PRIORITY;
	optList[1] = OptionType.START_TIME;
	Command cmd = new CommandList(optList);
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	
	optList = new OptionType[1];
	optList[0] = OptionType.END_TIME;
	cmd = new CommandList(optList);
	cmd.execute();
	list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
    }
}
