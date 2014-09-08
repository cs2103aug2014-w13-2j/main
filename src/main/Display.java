package main;

import java.util.ArrayList;
import java.awt.color.*;
public class Display {
	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";
	
	public static void taskDisplay(ArrayList<TaskItem> taskList){
		
	}
	
	public static void taskDisplay(TaskItem thisTask){
		
	}
	public static void stringDisplay(ArrayList<String> strings){
		for(int i=0;i<strings.size();i++){
			System.out.println(strings.get(i));
		}
	}
	public static void stringDisplay(String thisString){
		System.out.println(thisString);
	}
	public static void welcomeMessage(){
		
	}
}
