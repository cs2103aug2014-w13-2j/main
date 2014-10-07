package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;


/**
 * @author Hu Wenyan
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
	
	static final String ENTER_COMMAND_STR = "Please Enter Command.";
	static final String ENTER_VALID_COMMAND_STR = "Please Enter Valid Command.";
	static final String ENTER_TIME_PERIOD_STR = "Please Enter Time Period: ";
	static final String ENTER_TASK_INDEX_STR = "Please Enter Index: ";
	
	public String displayFeedback (Feedback commandFeedBack);

	public String displayWelcomeMessage();
	public String displayString(String str);
	public String displayStringList(ArrayList<String> arr);
	
	public String displayTitleLine();
	public String displayDividingLine();
	
	/**
	 * @param ArrayList<taskList> or TaskItem[]
	 * @return String of formatted taskList
	 */
	public String displayTaskList(ArrayList<TaskItem> taskList);
	public String displayTaskList(TaskItem[] taskList);
	public String displayTaskItem(TaskItem task);
	public String displayTaskFeedback(TaskItem task);
	public String displayTaskFile(TaskItem task);
	
	/**
	 * @param ArrayList<EventItem> or EventItem[]
	 * @return String of formatted eventList
	 */
	public String displayEventList(ArrayList<EventItem> eventList);
	public String displayEventList(EventItem[] eventList);
	public String displayEventItem(EventItem event);
	public String displayEventFeedback(EventItem event);
	public String displayEventFile(EventItem event);
	
	/**
	 * @param ArrayList<ToDoItem> or ToDoItem[]
	 * @return String of formatted todoList
	 */
	public String displayToDoList(ArrayList<ToDoItem> todoList);
	public String displayToDoList(ToDoItem[] todoList);
	public String displayToDoItem(ToDoItem todoItem);
	public String displayToDoFeedback(ToDoItem todoItem);
	public String displayToDoFile(ToDoItem todoItem);
	

	
	/**
	 * @return
	 * No @param return "Enter command:" prompt
	 * @param promptTag, corresbonding integer tag please refer to the final int above
	 * @param specific String promptMessage 
	 */
	public String displayPrompt();
	public String displayPrompt(int promptTag);
	public String displayPrompt(String promptMessage);

	
	/**
	 * @return Help page in a formatted String
	 */
	public String displayHelpPage();
	
	
}
