package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.awt.color.*;

import edu.dynamic.dynamiz.structure.*;

/**
 * @author Hu Wenyan
 * Implement Display functions for tasks, events and todoItems
 */
public class Displayer implements DisplayerInterface {
//	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";
//	
//	private static final int FEEDBACK_TAG = 1;
//	private static final int ERROR_FEEDBACK_TAG = 2;
//	private static final int SUCCESS_FEEDBACK_TAG = 3;
//	private static final int HELP_FEEDBACK_TAG = 4;

	
	public String dateFormatter(Calendar c){
		String s = String.format("%1$tm,%1$te",c);
		return s;	
	}
	public String dateFormatter(Date d){
		
		String s = String.format("%tm,%td,%ty", d);
		return s;
	}
	public String timeFormatter(Date d){
		String s = String.format("%tH:%tM", d);
		return s;
	}
	

	public String displayWelcomeMessage(){
		return WELCOME_MESSAGE;
	}
	
	
	
	public String displayTitleLine() {
		String s=String.format("%-20s%15s   %12s  %8s  %8s   ", "Task","Status","Priority","Start Time","End Time");	
		return s;
	}

	@Override
	public String displayDividingLine() {
		String s = new String("--------------------------------------------------------------------------------------------------------");
		return s;
	}

	
	@Override
	public String displayString(String str) {	
		return str;
	}

	@Override
	public String displayStringList(ArrayList<String> arr) {	
		if(arr == null) return new String("null");

		StringBuilder s = new StringBuilder();

		for(int i=0;i<arr.size();i++){
			if(!arr.get(i).isEmpty())
			s.append(arr.get(i).trim()).append("\n");
		}

		return s.toString();	
	}
	

	@Override
	public String displayTaskItem(TaskItem task) {
		if(task == null) return new String("null task");
		else return task.toString();
	}

	@Override
	public String displayTaskFile(TaskItem task) {
		if(task == null) return new String("null task");
		else return task.toFileString();
	}
	
	@Override
	public String displayTaskFeedback(TaskItem task) {
		if(task == null) return new String("null task");
		else return task.getFeedbackString();
	}

	

	@Override
	public String displayTaskList(ArrayList<TaskItem> taskList) {
		if(taskList == null ) return new String("null");
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.size(); i++){
			s.append(taskList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayTaskList(TaskItem[] taskList) {
		if(taskList == null ) return new String("null");
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.length; i++){
			s.append(taskList[i].toString()).append("\n");
		}
		return s.toString();

	}

	
	@Override
	public String displayEventFeedback(EventItem event) {
		if(event == null) return new String("null event");
		else return event.getFeedbackString();
		
	}

	@Override
	public String displayEventFile(EventItem event) {
		String s = new String("null event");
		if(event == null) return s;
		else return event.toFileString();
	}
	@Override
	public String displayEventItem(EventItem event) {
		String s = new String("null event");
		if(event == null) return s;
		else return event.toString();
	}
	
	@Override
	public String displayEventList(ArrayList<EventItem> eventList) {
		String str = new String("null event");
		if(eventList == null) return str;
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.size(); i++){
			s.append(eventList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayEventList(EventItem[] eventList) {
		String str = new String("null event");
		if(eventList == null) return str;
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.length; i++){
			s.append(eventList[i].toString()).append("\n");
		}
		return s.toString();
	}

	

	@Override
	public String displayToDoItem(ToDoItem todoItem) {
		String str = new String("null todoItem");
		if(todoItem == null) return str;
		else return todoItem.toString();
	}

	@Override
	public String displayToDoFeedback(ToDoItem todoItem) {
		String str = new String("null todoItem");
		if(todoItem == null) return str;
		else return todoItem.getFeedbackString();
	}

	@Override
	public String displayToDoFile(ToDoItem todoItem) {
		String str = new String("null todoItem");
		if(todoItem == null) return str;
		else return todoItem.toFileString();
	}
	
	@Override
	public String displayToDoList(ArrayList<ToDoItem> todoList) {
		String str = new String("null todoItem");
		if(todoList == null) return str;
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.size(); i++){
			s.append(todoList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayToDoList(ToDoItem[] todoList) {
		String str = new String("null todoItem");
		if(todoList == null) return str;
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.length; i++){
			s.append(todoList[i].toString()).append("\n");
		}
		return s.toString();
	}
	


	public String displayPrompt() {
		String s = new String ("Command: ");
		return s;
	}
//	public String displayEnterCommandPrompt() {
//		String s = new String ("Please Enter Command:");
//		return s;
//	}


	public String displayPrompt(int promptTag) {
		String tag = new String();
		switch(promptTag){
		case ENTER_COMMAND_PROMPT:
			tag = "Please Enter Command: ";
			break;
		case ENTER_ITEM_PROMPT:
			tag = "Please Enter Task: ";
			break;
		case ENTER_TASK_INDEX_PROMPT:
			tag = "Please Enter Task Index: ";
			break;
			
		case ENTER_TIME_PROMPT:
			tag = "Please Enter Time Period: ";
			break;
			
		case INVALID_COMMAND_PROMPT:
			tag = "Please Enter Valid Command: ";
			break;
		default:
			tag = "Please Enter Valid Command: ";
		}	
		
		//tag+="\n";
		
		return tag;
	}

	public String displayPrompt(String promptMessage) {
		return promptMessage;
	}

	
	public String displayFeedback(Feedback commandFeedback) {
		if(commandFeedback == null ) return "null";
		String s = new String(); 
		int t = getFeedbackTag(commandFeedback);
		switch(t){
		case HELP_FEEDBACK_TAG:
			HelpFeedback hf = (HelpFeedback)commandFeedback; 
			s = hf.getHelpContent();
			break;
			
		case ERROR_FEEDBACK_TAG:
			ErrorFeedback ef = (ErrorFeedback)commandFeedback; 
			s =ef.getCommandType();
			s+=" "+ef.getMessage();
			break;
			
		case SUCCESS_FEEDBACK_TAG:
			StringBuilder a = new StringBuilder();
			
			SuccessFeedback sf = (SuccessFeedback) commandFeedback;
			
			a.append(sf.getCommandType()).append(" successfully!").append("\n");
			
			getFeedbackContent(a,sf);
			
			s = a.toString();
			
			break;
		default:
			//s = commandFeedback.getCommandType();
			s = commandFeedback.getOriginalCommand();
			
		}
		return s;		
	}
	
	private void getFeedbackContent(StringBuilder a, SuccessFeedback sf){
		ToDoItem[] list = sf.getAffectedItems();
		if(list == null ) return;
		else{ for( int i = 0 ; i< list.length; i++){
			a.append(list[i].toFileString()).append("\n");
		}			
		}
		
	}
	 int getFeedbackTag(Feedback f){
		String t =f.getClassName();
		
		if(t.equals("SuccessFeedback")) return SUCCESS_FEEDBACK_TAG;
		if(t.equals("ErrorFeedback")) return ERROR_FEEDBACK_TAG;	
		if(t.equals("HelpFeedback")) return HELP_FEEDBACK_TAG;
		
		return FEEDBACK_TAG;		
		
	}

	
	@Override
	public String displayHelpPage() {
		StringBuilder sb = new StringBuilder();
		String title = StringUtils.center("Help Page", 9);
		sb.append(title).append("\n");
		return null;
	}

	
	
	
}

class StringUtils {

    public static String center(String s, int size) {
        return center(s, size, " ");
    }

    public static String center(String s, int size, String pad) {
        if (pad == null)
            throw new NullPointerException("pad cannot be null");
        if (pad.length() <= 0)
            throw new IllegalArgumentException("pad cannot be empty");
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
}
