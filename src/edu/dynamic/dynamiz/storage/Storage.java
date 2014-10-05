package edu.dynamic.dynamiz.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import edu.dynamic.dynamiz.controller.FileHandler;
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
     * @return The updated ToDoItem.
     * Signature and implementation to be edited after confirming parameters returned by parser.
     */
    public ToDoItem[] updateItem(String id){
	//Checks that id is neither null nor an empty string
	assert id!=null && !id.isEmpty();
	
	ToDoItem[] list = new ToDoItem[2];
	ToDoItem target = searchTree.get(id);
	
	//Makes a copy of the current version of the object
	if(target instanceof TaskItem){
	    list[0] = new TaskItem((TaskItem)target);
	} else if(target instanceof EventItem){
	    list[0] = new EventItem((EventItem)target);
	} else{
	    list[0] = new ToDoItem(target);
	}
	//Use mutator methods to update the target object.
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
     * Undoes the update done by the Command object executing the update method.
     * item must be such that its ID matches an existing item in the storage.
     * For use by CommandUpdate's undo method.
     */
    public void undoUpdate(ToDoItem item){
	assert item!=null;
	
	removeItem(item.getId());
	addItem(item);
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
}
