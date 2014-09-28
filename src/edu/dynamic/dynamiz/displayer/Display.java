package edu.dynamic.dynamiz.displayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.awt.color.*;

import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.TaskItem;

/**
 * @author Hu Wenyan
 *
 */
public class Display implements DisplayerInterface {
	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";

	public void printWelcomeMessage(){
		System.out.println(WELCOME_MESSAGE);
	}
	public void taskDisplay(ArrayList<TaskItem> taskList){
		if(taskList==null) return;
		for(int i=0;i<taskList.size();i++){
			TaskItem thisTask= taskList.get(i);
			System.out.println(thisTask.toString());
		}
	}
	private static String taskListTitle(){
		String s=String.format("%-20s%15s   %12s  %8s  %8s   ", "Description","Status","Priority","Start Time","End Time");
		return s;
	}
	private static String dividingLine(){
		return "--------------------------------------------------------------------------------------------------------";
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
	
	
	public static void stringDisplay(ArrayList<String> strings){
		for(int i=0;i<strings.size();i++){
			stringDisplay(strings.get(i));
		}
	}
	public static void stringDisplay(String thisString){
		System.out.println(thisString);
	}
	@Override
	public void stringPrint(String str) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stringPrint(ArrayList<String> arr) {
		// TODO Auto-generated method stub
		
	}
	
	
}
