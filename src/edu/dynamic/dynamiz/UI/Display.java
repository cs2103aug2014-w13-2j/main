package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.awt.color.*;

import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * @author Hu Wenyan
 * Implement Display functions for tasks, events and todoItems
 */
public class Display implements DisplayerInterface {
	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";

	public void printWelcomeMessage(){
		System.out.println(WELCOME_MESSAGE);
	}
	
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
	

	private static String taskListTitle(){
		String s=String.format("%-20s%15s   %12s  %8s  %8s   ", "Task","Status","Priority","Start Time","End Time");
		
		return s;
	}
	
	private static String dividingLine(){
		return "--------------------------------------------------------------------------------------------------------";
	}
	
	public void printString(String string) {
		System.out.println(string);
		
	}
	public void printStringList(ArrayList<String> arr) {
		for(int i=0;i<arr.size();i++)
			printString(arr.get(i));
		
	}
	
	public void displayTaskList(ArrayList<TaskItem> taskList){
		if(taskList==null) return;
		else{
			System.out.println(taskListTitle());
			System.out.println(dividingLine());
		for(int i=0;i<taskList.size();i++){
			TaskItem thisTask= taskList.get(i);
			System.out.println(thisTask.toString());
		}
		}
	}
	
	@Override
	public void displayTaskList(TaskItem[] taskList) {
		if(taskList==null) return;
		else{
			System.out.println(taskListTitle());
			System.out.println(dividingLine());
		for(int i=0;i<taskList.length;i++){
			TaskItem thisTask= taskList[i];
			System.out.println(thisTask.toString());
		}
		}
		
	}
	public void displayTaskItem(TaskItem task) {
		System.out.println(task.toString());
		
	}
	
	
	public void displayEventList(ArrayList<EventItem> eventList) {
		if(eventList==null) return;
		else{
			System.out.println(taskListTitle());
			System.out.println(dividingLine());
		for(int i=0;i<eventList.size();i++){
			EventItem thisEvent= eventList.get(i);
			System.out.println(thisEvent.toString());
		}
		}
		
	}
	@Override
	public void displayEventList(EventItem[] eventList) {
		if(eventList==null) return;
		else{
			System.out.println(taskListTitle());
			System.out.println(dividingLine());
		for(int i=0;i<eventList.length;i++){
			EventItem thisEvent= eventList[i];
			System.out.println(thisEvent.toString());
		}
		}
		
	}
	@Override
	public void displayEventItem(EventItem event) {
		System.out.println(event.toString());
		
	}
	@Override
	public void displayToDoList(ArrayList<ToDoItem> todoList) {
		if(todoList==null) return;
		else{
			
		for(int i=0;i<todoList.size();i++){
			ToDoItem thisToDo= todoList.get(i);
			System.out.println(thisToDo.toString());
		}
		}
		
	}
	@Override
	public void displayToDoList(ToDoItem[] todoList) {
		if(todoList==null) return;
		else{
			//System.out.println(taskListTitle());
			System.out.println(dividingLine());
		for(int i=0;i<todoList.length;i++){
			ToDoItem thisTodo= todoList[i];
			System.out.println(thisTodo.toString());
		}
		}
		
		
	}
	@Override
	public void displayToDoItem(ToDoItem todoItem) {
		System.out.println(todoItem.toString());
		
	}

	@Override
	public void displayEventFeedBack(EventItem event) {
		System.out.println(event.toFileString());
		
	}

	@Override
	public void displayEventFile(EventItem event) {
		System.out.println(event.toString());	
	}

	@Override
	public void displayTaskFeedBack(TaskItem task) {
		
		System.out.println(task.getFeedbackString());	
	}

	@Override
	public void displayTaskFile(TaskItem taskItem) {
		
		System.out.println(taskItem.getFeedbackString());	
	}



	@Override
	public void displayToDoFeedBack(ToDoItem todoItem) {
		System.out.println(todoItem.getFeedbackString());
		
	}

	@Override
	public void displayToDoFile(ToDoItem todoItem) {
		System.out.println(todoItem.toFileString());
		
	}
	
	@Override
	public void printPrompt(String promtMessage) {
		System.out.print(promtMessage);	
	}
	
	@Override
	public void displayHelpPage() {
		System.out.println("Help Page");	
	}
	
	
}
