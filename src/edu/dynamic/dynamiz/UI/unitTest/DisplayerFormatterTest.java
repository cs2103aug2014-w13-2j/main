// @author A0119397R
package edu.dynamic.dynamiz.UI.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.dynamic.dynamiz.UI.DisplayerFormatter;
import edu.dynamic.dynamiz.UI.StrIntPair;
import edu.dynamic.dynamiz.structure.*;

/**
 * The JUnit Test for DispayerFormatter
 */
public class DisplayerFormatterTest {
	DisplayerFormatter dp = new DisplayerFormatter();

	@Test
	public void testDisplayTask() {
		TaskItem task1 = new TaskItem("CS2106 homework", 4, new MyDateTime(26,
				9, 2014, 23, 59));
		assertEquals(task1.toString(), dp.displayTaskItem(task1));
		assertEquals(task1.getFeedbackString(), dp.displayTaskFeedback(task1));
		assertEquals(task1.toFileString(), dp.displayTaskFile(task1));
	}

	@Test
	public void testDisplayTaskList() {
		TaskItem task1 = new TaskItem("CS2106 homework", 4, new MyDateTime(26,
				9, 2014, 23, 59));
		TaskItem task2 = new TaskItem("CS2105 homework", 4, new MyDateTime(26,
				9, 2014, 23, 59));
		TaskItem task3 = new TaskItem("CS2102 homework", 4, new MyDateTime(26,
				9, 2014, 23, 59));

		ArrayList<TaskItem> testList = new ArrayList<TaskItem>();
		testList.add(task1);
		testList.add(task2);
		testList.add(task3);

		String s = new String();
		s += task1.toString();
		s += "\n";
		s += task2.toString();
		s += "\n";
		s += task3.toString();
		s += "\n";

		assertEquals(s, dp.displayTaskList(testList));

		TaskItem[] testArray = new TaskItem[3];
		testArray[0] = task1;
		testArray[1] = task2;
		testArray[2] = task3;

		assertEquals(s, dp.displayTaskList(testArray));
	}

	@Test
	public void testDisplayToDo() {
		ToDoItem item = new ToDoItem("Do CS2103T", 1);

		String itemToString = new String();
		itemToString = item.toString();
		String itemToFile = new String();
		itemToFile = item.toFileString();

		System.out.println(item.toString());
		assertEquals(itemToString, dp.displayToDoItem(item));
		assertEquals(itemToFile, dp.displayToDoFile(item));
		assertEquals(item.getFeedbackString(), dp.displayToDoFeedback(item));

	}

	@Test
	public void testTodoList() {
		ToDoItem item1 = new ToDoItem("Do CS2103T", 1);
		ToDoItem item2 = new ToDoItem("Do CS2103T", 2);
		ToDoItem item3 = new ToDoItem("Do CS2102", 1);

		ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
		todoList.add(item1);
		todoList.add(item2);
		todoList.add(item3);

		String s = new String();
		s += item1.toString();
		s += "\n";
		s += item2.toString();
		s += "\n";
		s += item3.toString();
		s += "\n";
		assertEquals(s, dp.displayToDoList(todoList));
		ToDoItem[] testArray = new ToDoItem[3];
		testArray[0] = item1;
		testArray[1] = item2;
		testArray[2] = item3;
		assertEquals(s, dp.displayToDoList(testArray));

	}

	@Test
	public void testDisplayEvent() {
		EventItem item = new EventItem("Nana's concert", 5, new MyDate(27, 9,
				2014));
		System.out.println(item.toString());
		assertEquals(item.toString(), dp.displayEventItem(item));
		assertEquals(item.toFileString(), dp.displayEventFile(item));
		assertEquals(item.getFeedbackString(), dp.displayEventFeedback(item));
	}

	@Test
	public void testEventList() {
		EventItem e1 = new EventItem("Nana's concert", 5, new MyDate(27, 9,
				2014));
		EventItem e2 = new EventItem("CS2105 midterms", new MyDateTime(4, 10,
				2014, 17, 0));
		EventItem e3 = new EventItem("Birthday", new MyDate(31, 10, 2014));

		ArrayList<EventItem> ls = new ArrayList<EventItem>();
		ls.add(e1);
		ls.add(e2);
		ls.add(e3);

		String s = new String();
		s += e1.toString();
		s += "\n";
		s += e2.toString();
		s += "\n";
		s += e3.toString();
		s += "\n";

		assertEquals(s, dp.displayEventList(ls));

		EventItem[] testArray = new EventItem[3];
		testArray[0] = e1;
		testArray[1] = e2;
		testArray[2] = e3;

		assertEquals(s, dp.displayEventList(testArray));

	}

	@Test
	public void testGetSucFeedbackContent() {
		ArrayList<StrIntPair> contentList = new ArrayList<StrIntPair>();
		ToDoItem[] itemList = new ToDoItem[1];
		itemList[0] = new ToDoItem("task1");

		SuccessFeedback sf = new SuccessFeedback("add", "task1", itemList);
		dp.getSucFeedbackContent(contentList, sf);
		assertEquals("add successfully!\n", contentList.get(0).getString());
		assertEquals(0, contentList.get(0).getInt());
		assertEquals(
				"------------------------------------------------------------------------------------------------\n",
				contentList.get(1).getString());
		assertEquals(0, contentList.get(1).getInt());
		assertEquals("ID: 11\nDes: task1\nPriority: ", contentList.get(2)
				.getString());
		assertEquals(0, contentList.get(2).getInt());
		assertEquals("None\n", contentList.get(3).getString());
		assertEquals(0, contentList.get(1).getInt());
		assertEquals("Status: ", contentList.get(4).getString());
		assertEquals(0, contentList.get(4).getInt());
	}

	@Test
	public void testGetErrorContent() {
		ArrayList<StrIntPair> contentList = new ArrayList<StrIntPair>();

		ErrorFeedback ef = new ErrorFeedback("add3", "add2",
				"invalid instruction");
		dp.getErrorFeedbackContent(contentList, ef);
		assertEquals("invalid instruction\n", contentList.get(0).getString());
		assertEquals(0, contentList.get(0).getInt());

	}

}
