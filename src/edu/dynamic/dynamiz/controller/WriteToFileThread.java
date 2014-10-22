package edu.dynamic.dynamiz.controller;

import java.util.Iterator;
import java.util.LinkedList;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines each thread that writes the given list to the specified file.
 * 
 * Constructor
 * WriteToFileThread(String[] list, String filename)	//Creates a new instance of this thread.
 * WriteToFileThread(ToDoItem[] list, String filename)	//Creates a new thread that writes the given ToDoItem list to file.
 * 
 * Public Methods
 * void run()	//Executes this thread.
 * 
 * @author zixian
 */
public class WriteToFileThread extends Thread {
    //Tracks the threads that were previously executed and to be terminated by the incoming thread.
    private static LinkedList<WriteToFileThread> todoListThreads = new LinkedList<WriteToFileThread>();
    private static LinkedList<WriteToFileThread> completedListThreads = new LinkedList<WriteToFileThread>();
    
    //Main data members
    private boolean isCompletedList = false;	//Indicates if this thread is writing the list of completed items to file
    private String[] list;	//The list of items to write to file
    private String filename;	//The name of the file to write to
    
    /**
     * Creates a new thread to write the given list to file.
     * This constructor implicitly implies that the given list is a list of ToDoItem objects that are
     * marked as completed.
     * @param list The list to be written to file.
     * @param filename The name of the file to write to.
     */
    public WriteToFileThread(String[] list, String filename){
	assert list!=null && filename!=null && !filename.isEmpty();
	this.list = list;
	this.filename = filename;
	this.isCompletedList = true;
    }
    
    /**
     * Creates a new thread that writes the file string representation of the items in the given list
     * to the specified file.
     * @param list The list of ToDoItem objects to be written to file.
     * @param filename The name of the file to write to.
     */
    public WriteToFileThread(ToDoItem[] list, String filename){
	assert list!=null && filename!=null && !filename.isEmpty();
	this.list = new String[list.length];
	int size = 0;
	for(ToDoItem i: list){
	    this.list[size++] = i.toFileString();
	}
	this.filename = filename;
    }
    
    @Override
    /**
     * Executes this thread.
     */
    public void run(){
	try{
	    if(isCompletedList){
		stopRunningThreadsInList(completedListThreads);
	    } else{
		stopRunningThreadsInList(todoListThreads);
	    }
	    DataFileReadWrite.writeListToFile(list, filename);
	} catch(InterruptedException e){
	    Thread.currentThread().interrupt();
	    return;
	}	
    }
    
    //Stops all running threads in the given thread list and adds this thread to the given list.
    private void stopRunningThreadsInList(LinkedList<WriteToFileThread> list) throws InterruptedException {
	synchronized(list){
	    WriteToFileThread thread;
	    Iterator<WriteToFileThread> itr = list.iterator();
	    while(itr.hasNext()){
		thread = itr.next();
		if(!thread.isAlive()){
		    list.remove(thread);
		} else{
		    thread.interrupt();
		    thread.join();
		}
	    }
	}
	list.add(this);
    }
}
