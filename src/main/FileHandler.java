package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

public class FileHandler {
    private static final String FILENAME_DEFAULT="todo.txt";
    
    private static final String MESSAGE_FILE_ERROR = "File does not exist or is corrupted. Exiting.";
    private static final String MESSAGE_FILE_WRITE_ERROR = "Error creating file/writing to file." +
    		"Some/All of the contents may not be saved.";
    private static final String MESSAGE_SAVE_SUCCESS = "Save successful!";
    
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

    
    
    //The name of the file to read from/write to.
    private String fileName;
    
    //The file to read from/write to
    private File file;
    
    //The object to read from file.
    private BufferedReader reader;
    
    //The object used to write to file
    private PrintWriter writer;
    
    public FileHandler(){
	this(FILENAME_DEFAULT);
    }
    
    public FileHandler(String fileName){
	this.fileName = fileName;
	file = new File(fileName);
    }
    
    /**
     * Reads the task list from the file specified in the constructor or
     * todo.txt.
     * @return An ArrayList of TaskItem objects, each TaskItem object represents
     * 		a task in the file being read from.
     */
    public ArrayList<TaskItem> getListFromFile(){
	ArrayList<TaskItem> tempList = new ArrayList<TaskItem>();
	String[] params, categoryList;
	String lineInput;
	TaskItem temp;
	
	try{
	    reader = new BufferedReader(new FileReader(file));
	    lineInput = reader.readLine();
	    while(lineInput!=null){
		if(lineInput.matches(INPUTFORMAT)){
		    params = lineInput.split("\\t");
		    temp = new TaskItem(params[0]);
		    if(Date.isValidDate(params[1])){
			//set start date
		    }
		    if(Date.isValidDate(params[2])){
			//set end date
		    }
		    temp.setPriorityType(params[3]);
		    categoryList = TaskItem.splitCategories(params[4]);
		    for(int i=0; i<categoryList.length; i++){
			temp.addCategory(categoryList[i]);
		    }
		    temp.setStatus(params[5]);
		    tempList.add(temp);
		} else{
		    throw new IOException();
		}    
		lineInput = reader.readLine();
	    }
	}
	catch(IOException ioe){
	    printErrorMessage(MESSAGE_FILE_ERROR);
	    System.exit(1);
	}
	finally{
	    reader.close();
	}
	return tempList;
    }
    
    /**
     * Writes the tasks in list to the file specified in the constructor of
     * "to-do.txt".
     */
    public void writeToFile(ArrayList<TaskItem> list){
	try{
	    file.createNewFile();
	    writer = new PrintWriter(file);
	    while(!list.isEmpty()){
		writer.println(list.remove(0));
	    }
	    printMessage(MESSAGE_SAVE_SUCCESS);
	} catch(IOException ioe){
	    printErrorMessage(MESSAGE_FILE_WRITE_ERROR);
	} finally{
	    writer.close();
	}
    }
    
    private void printMessage(String message){
	System.out.println(message);
    }
    
    private void printErrorMessage(String message){
	System.out.println(message);
    }
}
