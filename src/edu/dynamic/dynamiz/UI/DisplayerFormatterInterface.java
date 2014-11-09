package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0119397R
/**
 * Interface class for Displayer
 */
public interface DisplayerFormatterInterface {
	
	static final String WELCOME_MESSAGE= "Welcome to Dynamiz!\nType 'help' in the space below for more information!";
	
	/**
	 * Integer Tag for different Feedback type
	 */
	static final int FEEDBACK_TAG = 1;
	static final int ERROR_FEEDBACK_TAG = 2;
	static final int SUCCESS_FEEDBACK_TAG = 3;
	static final int HELP_FEEDBACK_TAG = 4;
	
	/**
	 * Integer Tag for different types of prompt
	 */
	static final int ENTER_COMMAND_PROMPT = 1;
	static final int ENTER_ITEM_PROMPT = 2;
	static final int ENTER_TASK_INDEX_PROMPT = 3;
	static final int ENTER_TIME_PROMPT = 4;
	static final int INVALID_COMMAND_PROMPT = 5;
	
	/**
	 * String content for different prompt
	 */
	static final String ENTER_COMMAND_STR = "Please enter command in the space below:";
	static final String ENTER_VALID_COMMAND_STR = "Please enter valid command:";
	static final String ENTER_TIME_PERIOD_STR = "Please enter time period: ";
	static final String ENTER_TASK_INDEX_STR = "Please enter index: ";
	
	/**
	 * Integer Tag for two different types of States
	 */
	static final int STATU_PEND_TAG = 11;
	static final int STATU_COMPLETE_TAG = 10;
	/**
	 * String content for States
	 */
	static final String STATU_PEND ="pending";
	static final String STATU_COMPLETE ="completed";
	
	

	/**
	 * String content for different command
	 */
	static final String UPDATE_COMMAND = "update";
	static final String SHOW_COMMAND = "show";
	static final String ADD_COMMAND = "add";
	
	
	/**
	 * Format for feedback item
	 */
	static final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
			"Start: %4$s\n"+"End: %5$s\n"+"Status: %7$s ";
	
	/**
	 * Receive @param commandFeedBack 
	 * Fetch feedback information and format it in a readable form
	 * compact information and corresponding color tag into An ArrayList of StrIntPair
	 * @return ArrayList<StrIntPair>
	 */
	public ArrayList<StrIntPair> displayFeedback (Feedback commandFeedBack);
	

	/**
	 * @return Welcome Message
	 */
	public String displayWelcomeMessage();
	
	
	/**
	 * @param ArrayList<String>
	 * format it into a single string
	 * @return String
	 */
	public String displayStringList(ArrayList<String> arr);
	
	
	/**
	 * @param ArrayList<taskList> or TaskItem[]
	 * @return String of formatted taskList
	 */
	public String displayTaskList(ArrayList<TaskItem> taskList);
	
	
	/**
	 * @param taskList
	 * @return Formatted String of the content of taskList
	 */
	public String displayTaskList(TaskItem[] taskList);
	
	
	/**
	 * @param task
	 * @return Formatted String of the content of task
	 */
	public String displayTaskItem(TaskItem task);
	
	
	/**
	 * @param task
	 * @return Feedback String of the task
	 */
	public String displayTaskFeedback(TaskItem task);
	
	
	/**
	 * @param task
	 * @return TaskFilestring of this task
	 */
	public String displayTaskFile(TaskItem task);
	
	/**
	 * @param ArrayList<EventItem> or EventItem[]
	 * @return String of formatted eventList
	 */
	public String displayEventList(ArrayList<EventItem> eventList);
	
	/**
	 * @param eventList
	 * @return A formatted string of the content of this eventList
	 */
	public String displayEventList(EventItem[] eventList);
	
	/**
	 * @param event
	 * @return A formatted String of the content of this event item
	 */
	public String displayEventItem(EventItem event);
	
	/**
	 * @param event
	 * @return A formatted String of the Feedback string of this event item
	 */
	public String displayEventFeedback(EventItem event);
	
	
	/**
	 * @param event
	 * @return A formatted String of the FileString of this event item
	 */
	public String displayEventFile(EventItem event);
	
	/**
	 * @param ArrayList<ToDoItem> or ToDoItem[]
	 * @return String of formatted todoList
	 */
	public String displayToDoList(ArrayList<ToDoItem> todoList);
	
	/**
	 * @param todoList
	 * @return A formatted String of the content of this todoList
	 */
	public String displayToDoList(ToDoItem[] todoList);
	
	/**
	 * @param todoItem
	 * @return A formatted String of the content of this todoItem
	 */
	public String displayToDoItem(ToDoItem todoItem);
	/**
	 * @param todoItem
	 * @return Formatted Feedback String of todoItem
	 */
	public String displayToDoFeedback(ToDoItem todoItem);
	
	/**
	 * @param todoItem
	 * @return A formatted String of the FileString of this todoItem 
	 */
	public String displayToDoFile(ToDoItem todoItem);
	
	/**
	 * @return
	 * No @param return "Enter command:" prompt
	 * @param promptTag, corresponding integer tag please refer to the integer constant above 
	 * @see {@link #FEEDBACK_TAG}
	 * @param specific String promptMessage 
	 */
	public String displayPrompt();
	public String displayPrompt(int promptTag);
	public String displayPrompt(String promptMessage);

	
	/**
	 * @return Help page in a formatted String
	 */
	public ArrayList<StrIntPair> displayHelpPage();
	
	
}
