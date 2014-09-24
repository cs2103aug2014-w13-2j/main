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
 * @author zixian
 */
public class FileHandler {
    //Name of the default file to read from/write to.
    private static final String FILENAME_DEFAULT = "todo.txt";
    
    private static final String MSG_MISSINGFILE = "%1$s is missing, creating new empty file...";

    /*
     * Defines regular expression format for each TaskItem object
     * represented on each line.
     */
    private static final String FORMAT_DATE = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4} " +
	    					"(([0-9]{1,2}:[0-9]{2})||(--:--))";
    private static final String FORMAT_EVENTITEM = ".+; [0-9]+; .+; "+FORMAT_DATE+"; "+FORMAT_DATE;
    private static final String FORMAT_TASKITEM = ".+; [0-9]+; .+; --/--/---- --:--; "+FORMAT_DATE;
    private static final String FORMAT_TODOITEM = ".+; [0-9]+; .+; --/--/---- --:--; --/--/---- --:--";
    
    //Delimiters for data processing purposes.
    private static final String DATE_DELIM = "/";
    private static final String DATETIME_DELIM = " ";
    private static final String FILESTRING_DELIM = "; ";
    private static final String TIME_DELIM = ":";
    
    //Represents invalid time.
    private static final String NULLTIME = "--:--";
    
    //Indices of various parameters in the params
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_PRIORITY = 1;
    private static final int INDEX_STATUS = 2;
    private static final int INDEX_STARTDATE = 3;
    private static final int INDEX_ENDDATE = 4;
    private static final int INDEX_DEADLINE = 4;
    
    //Indices of date and time in dateTime
    private static final int DATEINDEX = 0;
    private static final int TIMEINDEX =1;
    
    //Indices of date, month, year in date
    private static final int DATEINDEX_DATE = 0;
    private static final int DATEINDEX_MONTH = 1;
    private static final int DATEINDEX_YEAR = 2;
    
    //Indices of hour and minute in time
    private static final int TIMEINDEX_HOUR = 0;
    private static final int TIMEINDEX_MINUTE = 1;

    //Object for reading from file.
    private static BufferedReader reader;
    
    //Object for writing to file.
    private static PrintWriter writer;
    
    /**
     * Reads the task list from "todo.txt".
     * Stops reading on encountering IOException. May return incomplete list in such case.
     * @return An ArrayList of ToDoItem objects.
     */
    public static ArrayList<ToDoItem> getListFromFile(){
	return getListFromFile(FILENAME_DEFAULT);
    }
    
    /**
     * Reads the task list from the from the specified filename.
     * Stops reading on encountering IOException. May return incomplete list in such case.
     * @return An ArrayList of ToDoItem objects.
     */
    public static ArrayList<ToDoItem> getListFromFile(String filename){
	File file = new File(filename);
	ArrayList<ToDoItem> tempList = new ArrayList<ToDoItem>();
	
	try{
	    if(!file.exists()){
		printFileMissingMessage(filename);
		file.createNewFile();
	    }
	    reader = new BufferedReader(new FileReader(file));
	    String lineInput = reader.readLine();
	    while(lineInput!=null){
		if(lineInput.matches(FORMAT_TASKITEM)){
		    tempList.add(makeTaskItem(lineInput));
		} else if(lineInput.matches(FORMAT_EVENTITEM)){
		    tempList.add(makeEventItem(lineInput));
		} else if(lineInput.matches(FORMAT_TODOITEM)){
		    tempList.add(makeToDoItem(lineInput));
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
     * Writes the tasks in list to "to-do.txt".
     * @throws IOException if error occurs while creating output file if
     * 		if does not exists.
     */
    public static void writeListToFile(ArrayList<ToDoItem> list) throws IOException {
	writeListToFile(list, FILENAME_DEFAULT);
    }
    
    /**
     * Writes the tasks in list to the specified filename.
     * @throws IOException if error occurs while creating output file if
     * 		if does not exists.
     */
    public static void writeListToFile(ArrayList<ToDoItem> list, String filename) throws IOException {
	File file = new File(filename);
	if(!file.exists()){
	    file.createNewFile();
	}
	writer = new PrintWriter(file);
	int numItems = list.size();
	for(int i=0; i<numItems; i++){
	    writer.println(list.get(i).toFileString());
	}
	writer.close();
    }
    
    //Creates an EventItem from the given data.
    //Pre-condition: data must follow the format of FORMAT_EVENTITEM.
    private static EventItem makeEventItem(String data){
	EventItem event;
	String[] params = data.split(FILESTRING_DELIM);
	String description = params[INDEX_DESCRIPTION];
	int priority = Integer.parseInt(params[INDEX_PRIORITY]);
	String status = params[INDEX_STATUS];
	
	String[] startDateTime = params[INDEX_STARTDATE].split(DATETIME_DELIM);
	String[] startDate = startDateTime[DATEINDEX].split(DATE_DELIM);
	int startDay = Integer.parseInt(startDate[DATEINDEX_DATE]);
	int startMonth = Integer.parseInt(startDate[DATEINDEX_MONTH]);
	int startYear = Integer.parseInt(startDate[DATEINDEX_YEAR]);
	
	String[] endDateTime = params[INDEX_ENDDATE].split(DATETIME_DELIM);
	String[] endDate = endDateTime[DATEINDEX].split(DATE_DELIM);
	int endDay = Integer.parseInt(endDate[DATEINDEX_DATE]);
	int endMonth = Integer.parseInt(endDate[DATEINDEX_MONTH]);
	int endYear = Integer.parseInt(endDate[DATEINDEX_YEAR]);
	
	Date start, end;
	if(!startDateTime[TIMEINDEX].equals(NULLTIME)){
	    String[] startTime = startDateTime[TIMEINDEX].split(TIME_DELIM);
	    int hour = Integer.parseInt(startTime[TIMEINDEX_HOUR]);
	    int minute = Integer.parseInt(startTime[TIMEINDEX_MINUTE]);
	    start = new DateTime(startDay, startMonth, startYear, hour, minute);
	} else{
	    start = new Date(startDay, startMonth, startYear);
	}
	
	if(!endDateTime[TIMEINDEX].equals(NULLTIME)){
	    String[] endTime = endDateTime[TIMEINDEX].split(TIME_DELIM);
	    int hour = Integer.parseInt(endTime[TIMEINDEX_HOUR]);
	    int minute = Integer.parseInt(endTime[TIMEINDEX_MINUTE]);
	    end = new DateTime(endDay, endMonth, endYear, hour, minute);
	} else{
	    end = new Date(endDay, endMonth, endYear);
	}
	
	event = new EventItem(description, priority, start, end);
	event.setStatus(status);
	return event;
    }
    
    //Creates a ToDoItem from the given data.
    //Pre-condition: data must follow the format of FORMAT_TODOITEM.
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
    
    //Creates a TaskItem from the given data.
    //Pre-condition: data must follow the format of FORMAT_TASKITEM.
    private static TaskItem makeTaskItem(String data){
	TaskItem task;
	String[] params = data.split(FILESTRING_DELIM);
	String description = params[0];
	int priority = Integer.parseInt(params[INDEX_PRIORITY]);
	String status = params[INDEX_STATUS];
	String[] deadlineDateTime = params[INDEX_DEADLINE].split(DATETIME_DELIM);
	String[] deadlineDate = deadlineDateTime[DATEINDEX].split(DATE_DELIM);
	int day = Integer.parseInt(deadlineDate[DATEINDEX_DATE]);
	int month = Integer.parseInt(deadlineDate[DATEINDEX_MONTH]);
	int year = Integer.parseInt(deadlineDate[DATEINDEX_YEAR]);
	if(deadlineDateTime[TIMEINDEX].equals(NULLTIME)){
	    Date deadline = new Date(day, month, year);
	    task = new TaskItem(description, priority, deadline);
	} else{
	    String[] time = deadlineDateTime[TIMEINDEX].split(TIME_DELIM);
	    int hour = Integer.parseInt(time[TIMEINDEX_HOUR]);
	    int minute = Integer.parseInt(time[TIMEINDEX_MINUTE]);
	    DateTime deadline = new DateTime(day, month, year, hour, minute);
	    task = new TaskItem(description, priority, deadline);
	}
	task.setStatus(status);
	return task;
    }
    
    private static void printFileMissingMessage(String filename){
	System.out.println(String.format(MSG_MISSINGFILE, filename));
    }
}
