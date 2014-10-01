package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;


import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/*Displayer:
 * Print Prompt
 * Help Page
 * Feed Back:
 * error
 * help
 * success
 * 
 * 
 */
public interface DisplayerInterface {
	
	public String displayFeedBack (Feedback commandFeedBack);
	
	public String displayWelcomeMessage();
	public String displaytString(String str);
	public String displayStringList(ArrayList<String> arr);
	
	public String displayTitleLine();
	public String displayDividingLine();
	
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
	
	
	public String displayPrompt();
	public String displayPrompt(PromptTag prompt);
	public String displayPrompt(String promtMessage);

	
	public String displayHelpPage();
	
	
}
