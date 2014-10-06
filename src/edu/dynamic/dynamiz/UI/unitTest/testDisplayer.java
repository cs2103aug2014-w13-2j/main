package edu.dynamic.dynamiz.UI.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.dynamic.dynamiz.UI.Displayer;
import edu.dynamic.dynamiz.structure.*;

public class testDisplayer {
	Displayer dp = new Displayer();
	
	@Test
	public void testWelcome(){
		assertEquals(dp.displayWelcomeMessage(),"Welcome to Dynamiz!");
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
	
	@Test
	public void testDisplayTask(){
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
		
		TaskItem[] testArray = new TaskItem[3];
		testArray[0]=task1;
		testArray[1]=task2;
		testArray[2]=task3;
		
		assertEquals(dp.displayTaskList(testArray), s);
		
	}
	
	@Test
	public void testDisplayToDo(){
		ToDoItem item = new ToDoItem("Do CS2103T", 1);
		
		String itemToString = new String();
		itemToString = item.toString();
		String itemToFile = new String();
		itemToFile = item.toFileString();
		
		System.out.println(item.toString());
		assertEquals(dp.displayToDoItem(item),itemToString);
		assertEquals(dp.displayToDoFile(item),itemToFile);
		assertEquals(dp.displayToDoFeedBack(item),item.getFeedbackString());
			
	}
	
	@Test 
	public void testTodoList(){
		ToDoItem item1 = new ToDoItem("Do CS2103T", 1);
		ToDoItem item2 = new ToDoItem("Do CS2103T", 2);
		ToDoItem item3 = new ToDoItem("Do CS2102", 1);
		
		ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
		todoList.add(item1);
		todoList.add(item2);
		todoList.add(item3);
		
		String s = new String();
		s+=item1.toString();
		s+="\n";
		s+=item2.toString();
		s+="\n";
		s+=item3.toString();
		s+="\n";
		
		
		assertEquals(dp.displayToDoList(todoList), s);
		
		ToDoItem[] testArray = new ToDoItem[3];
		testArray[0]=item1;
		testArray[1]=item2;
		testArray[2]=item3;
		
		assertEquals(dp.displayToDoList(testArray), s);
		
	}
	
	@Test
	public void testDisplayEvent(){
		EventItem item = new EventItem("Nana's concert", 5, new Date(27, 9, 2014));
		
		System.out.println(item.toString());
		assertEquals(dp.displayEventItem(item),item.toString());
		assertEquals(dp.displayEventFile(item),item.toFileString());
		assertEquals(dp.displayEventFeedBack(item),item.getFeedbackString());
			
	}
	
	@Test 
	public void testEventList(){
		EventItem e1 = new EventItem("Nana's concert", 5, new Date(27, 9, 2014));
		EventItem e2 = new EventItem("CS2105 midterms", new DateTime(4, 10, 2014, 17, 0));
		EventItem e3=  new EventItem("Birthday", new Date(31, 10, 2014));
		
		ArrayList<EventItem> ls = new ArrayList<EventItem>();
		ls.add(e1);
		ls.add(e2);
		ls.add(e3);
		
		String s = new String();
		s+=e1.toString();
		s+="\n";
		s+=e2.toString();
		s+="\n";
		s+=e3.toString();
		s+="\n";
		
		
		assertEquals(dp.displayEventList(ls), s);
		
		EventItem[] testArray = new EventItem[3];
		testArray[0]=e1;
		testArray[1]=e2;
		testArray[2]=e3;
		
		assertEquals(dp.displayEventList(testArray), s);
		
	}


}
