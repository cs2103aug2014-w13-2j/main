package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Parser;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class CommandListTest {
    
    @Ignore
    public void test() {
	Parser parser = Parser.getInstance();
	Command cmd = parser.parse("add A priority 2");
	cmd.execute();
	
	cmd = parser.parse("update A1 on 7/10/2014");
	cmd.execute();
	
	cmd = parser.parse("add B priority 3");
	cmd.execute();
	
	cmd = parser.parse("update A2 from 8/10/2014 12:00 to 9/10/2014 00:00");
	cmd.execute();
	
	cmd = parser.parse("add C priority 3");
	cmd.execute();
	
	cmd = parser.parse("update A3 by 6/10/2014");
	cmd.execute();
	
	cmd = parser.parse("list priority from");
	cmd.execute();
	ToDoItem[] list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
	
	cmd = parser.parse("list priority to");
	cmd.execute();
	list = cmd.getAffectedItems();
	for(ToDoItem i: list)
	    System.out.println(i);
	System.out.println();
    }
    
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
