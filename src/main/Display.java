package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.awt.color.*;

/**
 * @author Hu Wenyan
 *
 */
public class Display {
	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";

	public static void printWelcomeMessage(){
		System.out.println(WELCOME_MESSAGE);
	}
	public static void taskDisplay(ArrayList<TaskItem> taskList){
		if(taskList==null) return;
		for(int i=0;i<taskList.size();i++){
			taskDisplay(taskList.get(i));
		}
	}
	public static String taskListTitle(){
		String s=String.format("%20s%20s%20s%20s%20s%20s", "Description","Status","Priority","Start Time","End Time","Categories");
		return s;
	}
	public static String dividingLine(){
		return "---------------------------------------------------------";
	}
	public static void taskDisplay(TaskItem thisTask){	
		
		String taskString=getTaskString(thisTask);
		System.out.println(taskString);
		
	}
	private static String getTaskString(TaskItem thisTask){
		String description=thisTask.getDescription();
		String startTime=thisTask.getStartDate();
		String endTime=thisTask.getEndDate();
		String priority=thisTask.getPriority();
		String status=thisTask.getStatus();
		ArrayList<String> categories=thisTask.getCategory();
		
		String s=String.format("%-20s%20s%10s%15s%15s%30s", description,status,priority,startTime,endTime,categories.toString());
		return s;
	}
	private String dateFormatter(Calendar c){
		String s = String.format("%1$tm,%1$te",c);
		return s;	
	}
	private String dateFormatter(Date d){
		
		String s = String.format("%tm,%td,%ty", d);
		return s;
	}
	private String timeFormatter(Date d){
		String s = String.format("%tH:%tM", d);
		return s;
	}
	
	
	public static void stringDisplay(ArrayList<String> strings){
		for(int i=0;i<strings.size();i++){
			stringDisplay(strings.get(i));
		}
	}
	public static void stringDisplay(String thisString){
		System.out.println(thisString);
	}
	
	
}
