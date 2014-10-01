package edu.dynamic.dynamiz.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import edu.dynamic.dynamiz.logic.FileHandler;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the storage class holding the list of tasks and events.
 * @author zixian
 * ToDo: Confirm the return type of parser and pass complexity of creating/updating items
 * 	to this class instead of Controller.
 */
public class Storage {
    //Main data members
    private ArrayList<ToDoItem> mainList;	//The main list
    private ArrayList<ToDoItem> toDoItemList;	//Holds items without any dates
    private ArrayList<EventItem> eventList;	//Holds events
    private ArrayList<TaskItem> taskList;	//Holds deadline tasks
    private TreeMap<String, ToDoItem> searchTree;	//Maps each item to its ID for faster search by ID
    
    //Constructor
    public Storage(){
	mainList = FileHandler.getListFromFile();
	searchTree = new TreeMap<String, ToDoItem>();
	toDoItemList = new ArrayList<ToDoItem>();
	eventList = new ArrayList<EventItem>();
	taskList = new ArrayList<TaskItem>();
	Iterator<ToDoItem> itr = mainList.iterator();
	ToDoItem temp;
	while(itr.hasNext()){
	    temp = itr.next();
	    searchTree.put(temp.getId(), temp);
	    if(temp instanceof TaskItem){
		taskList.add((TaskItem)temp);
	    } else if(temp instanceof EventItem){
		eventList.add((EventItem)temp);
	    } else{
		toDoItemList.add(temp);
	    }
	}
    }
    
    /**
     * Adds the given item to the list.
     * @return The TodoItem that is added to the list.
     * Signature and implementation to change after confirming parameters returned by parser.
     * Exceptions to be thrown when dealing with fail cases.
     */
    public ToDoItem addItem(ToDoItem item){
	mainList.add(item);
	searchTree.put(item.getId(), item);
	if(item instanceof TaskItem){
	    taskList.add((TaskItem)item);
	} else if(item instanceof EventItem){
	    eventList.add((EventItem)item);
	} else{
	    toDoItemList.add(item);
	}
	try {
	    FileHandler.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return item;	
    }
    
    /**
     * Updates the item with the given id with the specified changes.
     * @return The updated ToDoItem.
     * Signature and implementation to be edited after confirming parameters returned by parser.
     * Exceptions to be thrown on fail cases.
     */
    public ToDoItem updateItem(String id){
	ToDoItem target = searchTree.get(id);
	try {
	    FileHandler.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return target;	
    }
    
    /**
     * Returns the list of tasks and events in an array sorted in lexicographical order of their ID.
     * @return An array of ToDoItem objects sorted in lexicographical order of their ID
     * 		or null if the list is empty.
     */
    public ToDoItem[] getList(){
	if(mainList.isEmpty()){
	    return null;
	}
	return mainList.toArray(new ToDoItem[mainList.size()]);
    }
    
    /**
     * Returns a list of events sorted in lexicographical order of their ID.
     * @return An array of EventItem objects sorted in lexicographical order of their ID
     * 		or null if the list is empty.
     */
    public EventItem[] getEvents(){
	if(eventList.isEmpty()){
	    return null;
	}
	Collections.sort(eventList);
	return eventList.toArray(new EventItem[eventList.size()]);
    }
    
    /**
     * Returns a list of deadline tasks in lexicographical order or their ID.
     * @return An array of TaskItem objects sorted in lexicographical order of their ID
     * 		or null if the list is empty.
     */
    public TaskItem[] getTasks(){
	if(taskList.isEmpty()){
	    return null;
	}
	Collections.sort(taskList);
	return taskList.toArray(new TaskItem[taskList.size()]);
    }
    
    /**
     * Gets the list of events sorted in ascending order of start date.
     * @return An array of EventItem sorted in ascending order by start date
     * 		or null if the list is empty.
     * Implementation is currently only a stub, to be properly implemented when use case requirements
     * are confirmed.
     */
    public EventItem[] getListSortedByStartDate(){
	if(eventList.isEmpty()){
	    return null;
	}
	Collections.sort(eventList);
	return eventList.toArray(new EventItem[eventList.size()]);
    }
    
    /**
     * Gets the list of events sorted in ascending order of end date.
     * @return An array of EventItem sorted in ascending order by start date
     * 		or null if the list is empty.
     * Implementation is currently only a stub, to be properly implemented when use case requirements
     * are confirmed.
     */
    public EventItem[] getListSortedByEndDate(){
	if(eventList.isEmpty()){
	    return null;
	}
	Collections.sort(eventList);
	return eventList.toArray(new EventItem[eventList.size()]);
    }
    
    /**
     * Gets the list of deadline tasks sorted in ascending order of their deadlines.
     * @return An array of TaskItem objects sorted in ascending order of their deadlines.
     * Implementation is currently only a stub, to be properly implemented when use case requirements
     * are confirmed.
     */
    public TaskItem[] getListSortedByDeadline(){
	if(taskList.isEmpty()){
	    return null;
	}
	Collections.sort(taskList);
	return taskList.toArray(new TaskItem[taskList.size()]);
    }
    
    /**
     * Removes the ToDoItem with the given ID from the list.
     * @return The ToDoItem object that was removed from the list by this operation.
     */
    public ToDoItem removeItem(String id){
	ToDoItem temp = searchTree.remove(id);
	if(temp!=null){
	    mainList.remove(temp);
	    if(temp instanceof TaskItem){
		taskList.remove((TaskItem)temp);
	    } else if(temp instanceof EventItem){
		eventList.remove((EventItem)temp);
	    } else{
		toDoItemList.remove(temp);
	    }
	}
	try {
	    FileHandler.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return temp;
    }
}