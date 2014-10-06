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
	
	static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";

	static final int FEEDBACK_TAG = 1;
	static final int ERROR_FEEDBACK_TAG = 2;
	static final int SUCCESS_FEEDBACK_TAG = 3;
	static final int HELP_FEEDBACK_TAG = 4;
	
	static final int ENTER_COMMAND_PROMPT = 1;
	static final int ENTER_ITEM_PROMPT = 2;
	static final int ENTER_TASK_INDEX_PROMPT = 3;
	static final int ENTER_TIME_PROMPT = 4;
	static final int INVALID_COMMAND_PROMPT = 5;
	
	
	public String displayFeedBack (Feedback commandFeedBack);

	public String displayWelcomeMessage();
	public String displayString(String str);
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
	

	
	/**
	 * @return
	 * @param
	 */
	public String displayPrompt();
	public String displayPrompt(int promptTag);
	public String displayPrompt(String promtMessage);

	
	public String displayHelpPage();
	
	
}
