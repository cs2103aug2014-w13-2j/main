package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class FileHandler {
    private static final String FILENAME_DEFAULT="todo.txt";
    
    /*
     * Defines regular expression format for each TaskItem object
     * represented on each line.
     */
    private static final String INPUTFORMAT="[a-zA-Z0-9\\s]+\\t"+
	    		"(([0-9]{1,2}/[0-9]{1,2}/[0-9]{4})||(--/--/----)) "+
	    		"(([0-9]{1,2}:[0-9]{1,2})||(--:--))\\t"+
	    		"(([0-9]{1,2}/[0-9]{1,2}/[0-9]{4})||(--/--/----)) "+
	    		"(([0-9]{1,2}:[0-9]{1,2})||(--:--))\\t[a-z]+\\t"+
	    		"(([a-zA-Z0-9,]+)||(---))\\t[a-z]+";
    
    private String fileName;
    
    public FileHandler(){
	this(FILENAME_DEFAULT);
    }
    
    public FileHandler(String fileName){
	this.fileName = fileName;
    }
    
    public ArrayList<TaskItem> getListFromFile(){
	ArrayList<TaskItem> tempList = new ArrayList<TaskItem>();
	File file = new File(fileName);
	String[] params;
	String lineInput;
	TaskItem temp;
	
	try{
	    file.createNewFile();
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    lineInput = reader.readLine();
	    while(lineInput!=null){
		if(lineInput.matches(INPUTFORMAT))
		    System.out.println("Ok");
		else
		    System.out.println("Not ok");
		lineInput = reader.readLine();
	    }
	    reader.close();
	}
	catch(IOException ioe){
	    return null;
	}
	return tempList;
    }
}
