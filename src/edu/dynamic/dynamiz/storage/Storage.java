package edu.dynamic.dynamiz.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import edu.dynamic.dynamiz.controller.FileHandler;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the storage class holding the list of tasks and events.
 * 
 * Constructor
 * Storage()	//Creates a new instance of this Storage object.
 * 
 * Public Methods(currently that can be used)
 * ToDoItem addItem(ToDoItem)	//Adds the given item to the list.
 * ToDoItem[] updateItem(String id)	//Updates the ToDoItem with the given id with the specified details.
 * ToDoItem[] getList()	//Gets the list of ToDoItem objects held by this storage.
 * ToDoItem removeItem(String id)	//Removes the item with the specified id from this storage.
 * 
 * @author zixian
 */
public class Storage {
    //Main data members
    private ArrayList<ToDoItem> mainList;	//The main list
    private ArrayList<ToDoItem> toDoItemList;	//Holds items without any dates
    private ArrayList<EventItem> eventList;	//Holds events
    private ArrayList<TaskItem> taskList;	//Holds deadline tasks
    private TreeMap<String, ToDoItem> searchTree;	//Maps each item to its ID for faster search by ID
    
    /**
     * Creates a new instance of Storage.
     */
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
     * Adds the given item to the list. For use by CommandDelete's undo method.
     * @param item The ToDoItem to be added to the list.
     * @return The TodoItem that is added to the list.
     */
    public ToDoItem addItem(ToDoItem item){
	//item must not be null.
	assert item!=null;
	
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
     * @param id The id of the item to update.
     * @param priority The new priority for the item to be updated, use a negative number to indicate
     * 			no change to priority.
     * @param start The new start date for the item, or null if start date is not to be changed.
     * @param end The new end date for the item, or null if end date is not to be changed.
     * @return The updated ToDoItem.
     * @throws IllegalArgumentException if there is no such item with the given id.
     */
    public ToDoItem[] updateItem(String id, String description, int priority, Date start, Date end){
	assert id!=null && !id.isEmpty();
	
	ToDoItem[] list = new ToDoItem[2];
	ToDoItem target = searchTree.get(id);
	
	if(target==null)
	    throw new IllegalArgumentException("No such ID");
	
	//Makes a copy of the current version of the object
	if(target instanceof TaskItem){
	    list[0] = new TaskItem((TaskItem)target);
	} else if(target instanceof EventItem){
	    list[0] = new EventItem((EventItem)target);
	} else{
	    list[0] = new ToDoItem(target);
	}
	
	if(description!=null && !description.isEmpty()){
	    target.setDescription(description);
	}

	if(ToDoItem.isValidPriority(priority)){
	    target.setPriority(priority);
	}
	
	if(start!=null && !(target instanceof EventItem)){
	    target = new EventItem(target, start);
	    removeItem(target.getId());
	    addItem(target);
	} else if(start!=null){
	    ((EventItem)target).setStartDate(start);
	}
	
	if(end!=null){
	    if(target instanceof EventItem){
		((EventItem)target).setEndDate(end);
	    } else if(target instanceof TaskItem){
		((TaskItem)target).setDeadline(end);
	    } else{
		target = new TaskItem(target, end);
		removeItem(target.getId());
		addItem(target);
	    }
	}
	
	list[1] = target;
	
	try {
	    FileHandler.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return list;	
    }
    
    /**
     * Gets the list of tasks and events in an array sorted in lexicographical order of their ID.
     * @return An array of ToDoItem objects sorted in lexicographical order of their ID
     * 		or null if the list is empty.
     */
    public ToDoItem[] getList(){
	if(mainList.isEmpty()){
	    return null;
	}
	Collections.sort(mainList);
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
	return taskList.toArray(new TaskItem[taskList.size()]);
    }
    
    /**
     * Gets the list of events sorted in ascending order of start date.
     * @return An array of EventItem sorted in ascending order by start date
     * 		or null if the list is empty.
     * Implementation is currently only a stub, to be properly implemented when use case requirements
     * are confirmed.
     */
    public EventItem[] getEventsSortedByStartDate(){
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
    public EventItem[] getEventsSortedByEndDate(){
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
    public TaskItem[] getTasksSortedByDeadline(){
	if(taskList.isEmpty()){
	    return null;
	}
	Collections.sort(taskList);
	return taskList.toArray(new TaskItem[taskList.size()]);
    }
    
    /**
     * Removes the ToDoItem with the given ID from the list.
     * For use by CommandAdd's undo method and CommandDelete's execute method.
     * @param id The id of the ToDoItem object to remove from the list.
     * @return The ToDoItem object that was removed from the list by this operation.
     */
    public ToDoItem removeItem(String id){
	assert id!=null && !id.isEmpty();
	
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
    
    /**
     * Creates a Date instance based on the day of the week specified by the given date string.
     * @param dateString The day of the week.
     * @return A Date instance corresponding to the specified day of week in the immediate week.
     * throws IllegalArgumentException if dateString is not a valid day of week.
     * Note: Implementation is a stub. Currently not supported.
     */
    public Date getDate(String dateString){
	throw new IllegalArgumentException();
    }
}
