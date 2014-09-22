package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

/**
 * Defines each FileHandler that reads/writes task list to/from the
 * specified file or "todo.txt".
 * @author Qua Zi Xian
 */
public class FileHandler {
    private static final String FILENAME_DEFAULT="todo.txt";
    
    private static final String DATE_DELIM = "/";
    private static final String DATETIME_DELIM = " ";
    private static final String FILESTRING_DELIM = "; ";
    private static final String TIME_DELIM = ":";
    
    private static final String NULLTIME = "--:--";
    
    private static final String MESSAGE_FILE_ERROR = "File does not exist or is corrupted. Exiting.";
    private static final String MESSAGE_FILE_WRITE_ERROR = "Error creating file/writing to file." +
    		"Some/All of the contents may not be saved.";
    private static final String MESSAGE_SAVE_SUCCESS = "Save successful!";
    
    /*
     * Defines regular expression format for each TaskItem object
     * represented on each line.
     */
    private static final String FORMAT_DATE = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4} " +
	    					"(([0-9]{1,2}:[0-9]{2})||(--:--))";
    private static final String FORMAT_EVENTITEM = ".+; [0-9]+; .+; "+FORMAT_DATE+"; "+FORMAT_DATE;
    private static final String FORMAT_TASKITEM = ".+; [0-9]+; .+; --/--/---- --:--; "+FORMAT_DATE;
    private static final String FORMAT_TODOITEM = ".+; [0-9]+; .+; --/--/---- --:--; --/--/---- --:--";

    
    
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
     * todo.txt. Stops reading on encountering IOException
     * @return An ArrayList of ToDoItem objects, each ToDoItem object represents
     * 		an item in the file being read from.
     */
    public ArrayList<ToDoItem> getListFromFile(){
	ArrayList<ToDoItem> tempList = new ArrayList<ToDoItem>();
	String[] params;
	TaskItem temp;
	
	try{
	    reader = new BufferedReader(new FileReader(file));
	    String lineInput = reader.readLine();
	    while(lineInput!=null){
		if(lineInput.matches(FORMAT_TASKITEM)){
		    params = lineInput.split(FILESTRING_DELIM);
		    String[] dateTime = params[4].split(DATETIME_DELIM);
		    String[] date = dateTime[0].split(DATE_DELIM);
		    int day = Integer.parseInt(date[0]);
		    int month = Integer.parseInt(date[1]);
		    int year = Integer.parseInt(date[2]);
		    if(dateTime[1].equals(NULLTIME)){
			Date deadline = new Date(day, month, year);
			temp = new TaskItem(params[0], deadline);
		    } else{
			String[] time = dateTime[1].split(TIME_DELIM);
			int hour = Integer.parseInt(time[0]);
			int minute = Integer.parseInt(time[1]);
			DateTime deadline = new DateTime(day, month, year, hour, minute);
			temp = new TaskItem(params[0], deadline);
		    }
		    tempList.add(temp);
		} else if(lineInput.matches(FORMAT_EVENTITEM)){
		    //Add EventItem to tempList
		} else if(lineInput.matches(FORMAT_TODOITEM)){
		    //Add ToDoItem to tempList
		} else{
		    throw new IOException();
		}    
		lineInput = reader.readLine();
	    }
	    reader.close();
	} catch(IOException ioe){
	    
	}
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
