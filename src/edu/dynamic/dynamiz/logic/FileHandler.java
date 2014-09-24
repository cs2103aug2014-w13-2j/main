package edu.dynamic.dynamiz.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.TaskItem;

/**
 * Defines each FileHandler that reads/writes task list to/from the
 * specified file or "todo.txt".
 * @author Qua Zi Xian
 */
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
    private static final String FORMAT_DESCRIPTION = "[a-zA-Z0-9\\s]+";
    private static final String FORMAT_DATE = "(([0-9]{1,2}/[0-9]{1,2}/[0-9]{4})||(--/--/----)) " +
	    					"(([0-9]{1,2}:[0-9]{1,2})||(--:--))";
    private static final String FORMAT_PRIORITY = "[a-z]+";
    private static final String FORMAT_CATEGORY = "(([a-zA-Z0-9,]+)||(---))";
    private static final String FORMAT_STATUS = "[a-z]+";
    private static final String FORMAT_TASKITEM = FORMAT_DESCRIPTION+"\\t" +
	    		FORMAT_DATE + "\\t" + FORMAT_DATE + "\\t" + FORMAT_PRIORITY
	    		+ "\\t" + FORMAT_CATEGORY + "\\t" + FORMAT_STATUS;

    
    
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
	file = new File(this.fileName);
    }
    
    /**
     * Reads the task list from the file specified in the constructor or
     * todo.txt.
     * @return An ArrayList of TaskItem objects, each TaskItem object represents
     * 		a task in the file being read from.
     */
    public ArrayList<TaskItem> getListFromFile() throws IOException {
	ArrayList<TaskItem> tempList = new ArrayList<TaskItem>();
	String[] params, categoryList;
	String lineInput;
	TaskItem temp;
	
	reader = new BufferedReader(new FileReader(file));
	lineInput = reader.readLine();
	while(lineInput!=null){
	    if(lineInput.matches(FORMAT_TASKITEM)){
		params = lineInput.split("\\t");
		temp = new TaskItem(params[0]);
		/*if(Date.isValidDate(params[1])){
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
		temp.setStatus(params[5]);*/
		tempList.add(temp);
	    } else{
		throw new IOException();
	    }    
	    lineInput = reader.readLine();
   
	}
	reader.close();
	return tempList;
    }
    
    /**
     * Writes the tasks in list to the file specified in the constructor of
     * "to-do.txt".
     * @throws IOException if error occurs while creating output file if
     * 		if does not exists.
     */
    public void writeToFile(ArrayList<TaskItem> list) throws IOException {
	file.createNewFile();
	writer = new PrintWriter(file);
	int numItems = list.size();
	for(int i=0; i<numItems; i++){
	    writer.println(list.get(i));
	}
	writer.close();
    }
    
    private void printMessage(String message){
	System.out.println(message);
    }
    
    private void printErrorMessage(String message){
	System.out.println(message);
    }
}
