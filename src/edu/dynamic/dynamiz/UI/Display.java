package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.awt.color.*;

import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.Feedback;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * @author Hu Wenyan
 * Implement Display functions for tasks, events and todoItems
 */
public class Display implements DisplayerInterface {
	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";

	
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
		return "--------------------------------------------------------------------------------------------------------";
	}

	
	@Override
	public String displaytString(String str) {	
		return str;
	}

	@Override
	public String displayStringList(ArrayList<String> arr) {	
		if(arr == null) return "null";

		StringBuilder s = new StringBuilder();

		for(int i=0;i<arr.size();i++){
			if(!arr.get(i).isEmpty())
			s.append(arr.get(i).trim()).append("\n");
		}

		return s.toString();	
	}
	

	@Override
	public String displayTaskItem(TaskItem task) {
		if(task == null) return "null task";
		else return task.toString();
	}

	@Override
	public String displayTaskFile(TaskItem task) {
		if(task == null) return "null task";
		else return task.toFileString();
	}
	
	@Override
	public String displayTaskFeedBack(TaskItem task) {
		if(task == null) return "null task";
		else return task.getFeedbackString();
	}

	

	@Override
	public String displayTaskList(ArrayList<TaskItem> taskList) {
		if(taskList == null ) return "null";
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.size(); i++){
			s.append(taskList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayTaskList(TaskItem[] taskList) {
		if(taskList == null ) return "null";
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.length; i++){
			s.append(taskList[i].toString()).append("\n");
		}
		return s.toString();

	}

	
	@Override
	public String displayEventFeedBack(EventItem event) {
		if(event == null) return "null event";
		else return event.getFeedbackString();
		
	}

	@Override
	public String displayEventFile(EventItem event) {
		if(event == null) return "null event";
		else return event.toFileString();
	}
	@Override
	public String displayEventItem(EventItem event) {
		if(event == null) return "null event";
		else return event.toString();
	}
	
	@Override
	public String displayEventList(ArrayList<EventItem> eventList) {
		if(eventList == null ) return "null";
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.size(); i++){
			s.append(eventList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayEventList(EventItem[] eventList) {
		if(eventList == null ) return "null";
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.length; i++){
			s.append(eventList[i].toString()).append("\n");
		}
		return s.toString();
	}

	


	@Override
	public String displayToDoItem(ToDoItem todoItem) {
		if(todoItem == null) return "null todo Item";
		else return todoItem.toString();
	}

	@Override
	public String displayToDoFeedBack(ToDoItem todoItem) {
		if(todoItem == null) return "null todo Item";
		else return todoItem.getFeedbackString();
	}

	@Override
	public String displayToDoFile(ToDoItem todoItem) {
		if(todoItem == null) return "null todo Item";
		else return todoItem.toFileString();
	}
	
	@Override
	public String displayToDoList(ArrayList<ToDoItem> todoList) {
		if(todoList == null ) return "null";
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.size(); i++){
			s.append(todoList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayToDoList(ToDoItem[] todoList) {
		if(todoList == null ) return "null";
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.length; i++){
			s.append(todoList[i].toString()).append("\n");
		}
		return s.toString();
	}
	

	@Override
	public String displayPrompt() {
		return "Please Enter Command:";
	}

	@Override
	public String displayPrompt(PromptTag prompt) {
		String tag = new String();
		switch(prompt){
		case EnterCommand:
			tag = "Please Enter Command:";
		case EnterTodoItem:
			tag = "Please Enter Task:";
		case EnterTaskIndex:
			tag = "Please Enter Task Index:";
		case InvalidCommand:
			tag = "Please Enter Valid Command:";
		}	
		
		tag+="\n";
		
		return tag;
	}

	@Override
	public String displayPrompt(String promptMessage) {
		return promptMessage;
	}

	
	@Override
	public String displayFeedBack(Feedback commandFeedBack) {
		String s = new String();
		
		return s;		
	}

	
	@Override
	public String displayHelpPage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
