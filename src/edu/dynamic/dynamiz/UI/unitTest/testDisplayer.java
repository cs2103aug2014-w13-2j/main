package edu.dynamic.dynamiz.UI.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.dynamic.dynamiz.UI.Displayer;
import edu.dynamic.dynamiz.structure.*;

public class testDisplayer {
	Displayer dp = new Displayer();
	public void setUp(){
		
		
	}
	@Test
	public void testDisplayString() {
		String t1 = "test String";
		dp.displayString(t1);
		assertEquals(dp.displayString(t1),t1);
	}
	
	@Test
	public void testDisplayPrompy(){
		String t1 = "Enter condition:";
		int promptTag = 2;
		assertEquals(dp.displayPrompt(),"Please Enter Command:");
		
		assertEquals(dp.displayPrompt(t1),"Enter condition:");
		
		assertEquals(dp.displayPrompt(promptTag),"Please Enter Task:");
		
	}
	
	@Test public void testDisplayTask(){
		TaskItem task1 = new TaskItem("CS2106 homework", 4, new DateTime(26, 9, 2014, 23, 59));
		TaskItem task2 = new TaskItem("CS2105 homework", 4, new DateTime(26, 9, 2014, 23, 59));
		TaskItem task3 = new TaskItem("CS2102 homework", 4, new DateTime(26, 9, 2014, 23, 59));
		assertEquals(dp.displayTaskItem(task1), task1.toString());
		assertEquals(dp.displayTaskFeedBack(task1), task1.getFeedbackString());
		assertEquals(dp.displayTaskFile(task1), task1.toFileString());
		
	}
	
	@Test 
	public void testDisplayTaskList(){
		TaskItem task1 = new TaskItem("CS2106 homework", 4, new DateTime(26, 9, 2014, 23, 59));
		TaskItem task2 = new TaskItem("CS2105 homework", 4, new DateTime(26, 9, 2014, 23, 59));
		TaskItem task3 = new TaskItem("CS2102 homework", 4, new DateTime(26, 9, 2014, 23, 59));
		
		ArrayList<TaskItem> testList = new ArrayList<TaskItem>();
		testList.add(task1);
		testList.add(task2);
		testList.add(task3);
		
		String s = new String();
		s+=task1.toString();
		s+="\n";
		s+=task2.toString();
		s+="\n";
		s+=task3.toString();
		s+="\n";
		
		assertEquals(dp.displayTaskList(testList), s);
		
		
	}

}
