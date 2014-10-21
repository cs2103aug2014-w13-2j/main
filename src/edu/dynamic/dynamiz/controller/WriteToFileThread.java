package edu.dynamic.dynamiz.controller;

import java.util.Iterator;
import java.util.LinkedList;

import edu.dynamic.dynamiz.structure.ToDoItem;

public class WriteToFileThread extends Thread {
    private static LinkedList<WriteToFileThread> todoListThreads = new LinkedList<WriteToFileThread>();
    private static LinkedList<WriteToFileThread> completedListThreads = new LinkedList<WriteToFileThread>();
    
    private boolean isCompletedList = false;
    private String[] list;
    private String filename;
    
    public WriteToFileThread(String[] list, String filename){
	this.list = list;
	this.filename = filename;
	this.isCompletedList = true;
    }
    
    public WriteToFileThread(ToDoItem[] list, String filename){
	this.list = new String[list.length];
	int size = 0;
	for(ToDoItem i: list){
	    this.list[size++] = i.toFileString();
	}
	this.filename = filename;
    }
    
    @Override
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
