package edu.dynamic.dynamiz.displayer;

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
 *
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
	
	@Override
	public void printString(String string) {
		System.out.println(string);
		
	}
	@Override
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
	@Override
	public void displayTaskItem(TaskItem task) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void displayEventList(ArrayList<EventItem> eventList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayEventList(EventItem[] eventList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayEventItem(EventItem event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayToDoList(ArrayList<ToDoItem> todoList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayToDoList(ToDoItem[] todoList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayToDoItem(ToDoItem todoItem) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printPrompt(String promtMessage) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void displayHelpPage() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
