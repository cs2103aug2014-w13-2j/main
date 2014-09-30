package edu.dynamic.dynamiz.displayer;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public interface DisplayerInterface {
	public void printWelcomeMessage();
	public void printString(String str);
	public void printStringList(ArrayList<String> arr);
	
	public void displayTaskList(ArrayList<TaskItem> taskList);
	public void displayTaskList(TaskItem[] taskList);
	public void displayTaskItem(TaskItem task);
	public void displayTaskFeedBack(TaskItem task);
	public void displayTaskFile(TaskItem task);
	
	public void displayEventList(ArrayList<EventItem> eventList);
	public void displayEventList(EventItem[] eventList);
	public void displayEventItem(EventItem event);
	public void displayEventFeedBack(EventItem event);
	public void displayEventFile(EventItem event);
	
	
	public void displayToDoList(ArrayList<ToDoItem> todoList);
	public void displayToDoList(ToDoItem[] todoList);
	public void displayToDoItem(ToDoItem todoItem);
	public void displayToDoFeedBack(ToDoItem todoItem);
	public void displayToDoFile(ToDoItem todoItem);
	
	
	public void printPrompt(String promtMessage);

	public void displayHelpPage();
	
	
}
