package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;


import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/*Displayer:
 * Print Prompt
 * Help Page
 * Feed Back
 * Exception
 * 
 */
public interface DisplayerInterface {
	public String printFeedBack (Feedback commandFeedBack);
	
	public String printWelcomeMessage();
	public String printString(String str);
	public String printStringList(ArrayList<String> arr);
	
	public String displayTaskList(ArrayList<TaskItem> taskList);
	public String displayTaskList(TaskItem[] taskList);
	public String displayTaskItem(TaskItem task);
	public String displayTaskFeedBack(TaskItem task);
	public String displayTaskFile(TaskItem task);
	
	public String displayEventList(ArrayList<EventItem> eventList);
	public String displayEventList(EventItem[] eventList);
	public String displayEventItem(EventItem event);
	public String displayEventFeedBack(EventItem event);
	public String displayEventFile(EventItem event);
	
	
	public String displayToDoList(ArrayList<ToDoItem> todoList);
	public String displayToDoList(ToDoItem[] todoList);
	public String displayToDoItem(ToDoItem todoItem);
	public String displayToDoFeedBack(ToDoItem todoItem);
	public String displayToDoFile(ToDoItem todoItem);
	
	public String printPrompt(String promtMessage);

	public String printPrompt(int promptTag);
	
	public String displayHelpPage();
	
	
}
