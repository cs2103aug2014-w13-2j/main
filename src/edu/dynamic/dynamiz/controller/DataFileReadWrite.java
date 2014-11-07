package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.structure.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import edu.dynamic.dynamiz.structure.TaskItem;



/**
 * FileHandler is a class that reads/writes task list to/from the
 * specified file or "todo.txt".
 * This class is meant to be used in the same way as Java Math class: no object instance is required.
 * 
 * Public Methods:
 * static ArrayList<ToDoItem> getListFromFile(String filename)	Gets a list of ToDoItem from the specified filename
 * static void writeListToFile(String[] list, String filename)	//Writes the given list of strings to file.
 * 
 * Private Methods:
 * static EventItem makeEventItem(String data)	Creates an EventItem from the data read from file.
 * static TaskItem makeTaskItem(String data)	Creates a TaskItem from the data read from file.
 * static ToDoItem makeToDoItem(String data)	Creates a ToDoItem form the data read from file.
 * 
 * @author A0110781N
 */
public class DataFileReadWrite {
    /*
     * Defines regular expression format for each TaskItem object
     * represented on each line.
     */
    private static final String FORMAT_DATE = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4} " +
	    					"(([0-9]{1,2}:[0-9]{2})||(--:--))";
    private static final String FORMAT_EVENTITEM = ".+; [0-9]+; .+; "+FORMAT_DATE+"; "+FORMAT_DATE;
    private static final String FORMAT_TASKITEM = ".+; [0-9]+; .+; --/--/---- --:--; "+FORMAT_DATE;
    private static final String FORMAT_TODOITEM = ".+; [0-9]+; .+; --/--/---- --:--; --/--/---- --:--";
    
    //File names for todo list and completed.list
    private static final String FILENAME_COMPLETED = "completed.txt";
    private static final String FILE_DIR = "dynamiz";
    
    //Delimiters for data processing purposes.
    private static final String DATETIME_DELIM = " ";
    private static final String FILESTRING_DELIM = "; ";
    
    //Indices of various parameters in the params
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_PRIORITY = 1;
    private static final int INDEX_STATUS = 2;
    private static final int INDEX_STARTDATE = 3;
    private static final int INDEX_ENDDATE = 4;
    private static final int INDEX_DEADLINE = 4;
    
    //Index of date when splitting DateTime string.
    private static final int DATEINDEX = 0;
    
    //Number of days to keep completed items in todo.txt before archiving to completed.txt
    private static final int PERSISTENT_DURATION = 5;

    //Object for reading from file.
    private static BufferedReader reader;
    
    //Object for writing to file.
    private static PrintWriter writer;
    
    //The logger to log any errors.
    private static Logger logger = Logger.getLogger("edu.dynamic.dynamiz.controller.DataFileReadWrite");
    
    /**
     * Reads the task list from the from the specified filename.
     * Stops reading on encountering IOException. May return incomplete list in such case.
     * If the ToDoItem is marked as completed and its end date is older than the threshold date,
     * it will be written to completed.txt and not included in the list for managing.
     * @param filename Name of file to read list from.
     * @return An ArrayList of ToDoItem objects.
     */
    public static ArrayList<ToDoItem> getListFromFile(String filename){
	File file, completedFile, dir;
	ArrayList<ToDoItem> tempList = new ArrayList<ToDoItem>();
	DateTime currentTime = new DateTime();
	DateTime threshold = currentTime.minusDays(PERSISTENT_DURATION);
	MyDate thresholdDate = new MyDate(threshold.getDayOfMonth(), threshold.getMonthOfYear(), threshold.getYear());
	
	try{
	    dir = new File(FILE_DIR);
	    if(!dir.exists() || !dir.isDirectory()){
		dir.mkdirs();
	    }
	    file = new File(dir, filename);
	    completedFile = new File(dir, FILENAME_COMPLETED);
	    if(!file.exists() || !file.isFile()){
		logger.log(Level.INFO, "{0} does not exist, creating file...", filename);
		file.createNewFile();
	    }
	    if(!completedFile.exists() || !completedFile.isFile()){
		logger.log(Level.INFO, "{0} does not exist, creating file...", FILENAME_COMPLETED);
	    }
	    
	    //Opens todo.txt for reading and completed.txt for writing.
	    reader = new BufferedReader(new FileReader(file));
	    writer = new PrintWriter(new BufferedWriter(new FileWriter(completedFile, true)));
	    
	    //Process input
	    String lineInput;
	    TaskItem task;
	    EventItem event;
	    while((lineInput = reader.readLine())!=null){
		if(lineInput.matches(FORMAT_TASKITEM)){
		    task = makeTaskItem(lineInput);
		    if(task.getStatus().equals(ToDoItem.STATUS_COMPLETED) && task.getDeadline().compareTo(thresholdDate)<0){
			writer.println(lineInput);
		    } else{
			tempList.add(task);
		    }
		} else if(lineInput.matches(FORMAT_EVENTITEM)){
		    event = makeEventItem(lineInput);
		    if(event.getStatus().equals(ToDoItem.STATUS_COMPLETED) && event.getEndDate().compareTo(thresholdDate)<0){
			writer.println(lineInput);
		    } else{
			tempList.add(event);
		    }
		} else if(lineInput.matches(FORMAT_TODOITEM)){
		    tempList.add(makeToDoItem(lineInput));
		} else{
		    throw new IOException();
		}    
	    }
	    reader.close();
	    writer.close();
	    Thread writeToFileThread = new WriteToFileThread(tempList.toArray(new ToDoItem[tempList.size()]), filename);
	    writeToFileThread.start();
	} catch(IOException e){
	    logger.log(Level.SEVERE, "IO Exception.");
	}
	return tempList;
    }
    
    /**
     * Writes the given list of string to the given file, with each element in the list
     * written on a separate line.
     * @param list The list to be written to the file.
     * @param filename The name of the file to write to.
     */
    public static synchronized void writeListToFile(String[] list, String filename){
	File dir = new File(FILE_DIR), outFile;
	try{
	    if(!dir.exists() || !dir.isDirectory()){
		dir.mkdirs();
	    }
	    outFile = new File(dir, filename);
	    if(!outFile.exists() || !outFile.isFile()){
		outFile.createNewFile();
	    }
	    Thread currentThread = Thread.currentThread();
	    writer = new PrintWriter(outFile);
	    for(String str: list){
		writer.println(str);
		if(currentThread.isInterrupted()){
		    break;
		}
	    }
	    writer.close();
	} catch(IOException e){
	    logger.log(Level.SEVERE, "IO Exception.");
	}
    }
    
    /**
     * Creates an EventItem from the given data string.
     * data must be such that data.matches(FORMAT_EVENTITEM) returns true.
     * @param data The String representation of the EventItem object used to write to files.
     * @return An EventItem object such that its toFileString() method returns the same string as data.
     */
    private static EventItem makeEventItem(String data){
	EventItem event;
	String[] params = data.split(FILESTRING_DELIM);
	String description = params[INDEX_DESCRIPTION];
	int priority = Integer.parseInt(params[INDEX_PRIORITY]);
	String status = params[INDEX_STATUS];
	
	MyDate startDate, endDate;
	if(params[INDEX_STARTDATE].matches(MyDateTime.REGEX_DATETIME)){
	    startDate = MyDateTime.makeDateTime(params[INDEX_STARTDATE]);
	} else{
	    startDate = MyDate.makeDate(params[INDEX_STARTDATE].split(DATETIME_DELIM)[DATEINDEX]);
	}
	
	if(params[INDEX_ENDDATE].matches(MyDateTime.REGEX_DATETIME)){
	    endDate = MyDateTime.makeDateTime(params[INDEX_ENDDATE]);
	} else{
	    endDate = MyDate.makeDate(params[INDEX_ENDDATE].split(DATETIME_DELIM)[DATEINDEX]);
	}
	
	event = new EventItem(description, priority, startDate, endDate);
	event.setStatus(status);
	return event;
    }
    
    /**
     * Creates a ToDoItem from the given data string.
     * data must be such that data.matches(FORMAT_TODOITEM) returns true.
     * @param data The String representation of the ToDoItem object used to write to files.
     * @return A ToDoItem object such that its toFileString() method returns the same string as data.
     */
    private static ToDoItem makeToDoItem(String data){
	ToDoItem item;
	String[] params = data.split(FILESTRING_DELIM);
	String description = params[INDEX_DESCRIPTION];
	int priority = Integer.parseInt(params[INDEX_PRIORITY]);
	String status = params[INDEX_STATUS];
	item = new ToDoItem(description, priority);
	item.setStatus(status);
	return item;
    }
    
    /**
     * Creates a TaskItem from the given data string.
     * data must be such that data.matches(FORMAT_TASKITEM) returns true.
     * @param data The String representation of the TaskItem object used to write to files.
     * @return A TaskItem object such that its toFileString() method returns the same string as data.
     */
    private static TaskItem makeTaskItem(String data){
	TaskItem task;
	String[] params = data.split(FILESTRING_DELIM);
	String description = params[0];
	int priority = Integer.parseInt(params[INDEX_PRIORITY]);
	String status = params[INDEX_STATUS];
	
	MyDate deadline;
	if(params[INDEX_DEADLINE].matches(MyDateTime.REGEX_DATETIME)){
	    deadline = MyDateTime.makeDateTime(params[INDEX_DEADLINE]);
	} else{
	    deadline = MyDate.makeDate(params[INDEX_DEADLINE].split(DATETIME_DELIM)[DATEINDEX]);
	}
	
	task = new TaskItem(description, priority, deadline);
	task.setStatus(status);
	return task;
    }
}
