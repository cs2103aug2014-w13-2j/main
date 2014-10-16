package edu.dynamic.dynamiz.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;

import edu.dynamic.dynamiz.controller.DataFileReadWrite;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.EndDateComparator;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.PriorityComparator;
import edu.dynamic.dynamiz.structure.StartDateComparator;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the storage class holding the list of tasks and events.
 * 
 * Public Methods(currently that can be used)
 * static Storage getInstance()	//gets the Storage instance
 * ToDoItem addItem(ToDoItem item)	//Adds the given item to the list.
 * ToDoItem[] getList()	//Gets the list of ToDoItem objects held by this storage.
 * ToDoItem[] getListSortedByEndDate()	//Gets a list of ToDoItem sorted in ascending order by end date.
 * ToDoItem[] getListSortedByPriority()	//Gets a list of ToDoItem sorted in descending order by priority.
 * ToDoItem[] getListSortedByStartDate()	//Gets a list of ToDoItem sorted in ascending order by start date.
 * ToDoItem removeItem(String id)	//Removes the item with the specified id from this storage.
 * ToDoItem[] searchItems(String keyword, int priority, Date start, Date end)	//Gets a list of items with the given parameter values.
 * ToDoItem[] updateItem(String id, String description, int priority, Date start, Date end)	//Updates the ToDoItem with the given id with the specified details. 
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
    private Stack<ToDoItem> completedList;	//The list of completed items
    private static Storage storage;	//Holds the only instance of the Storage object
    
    /**
     * Creates a new instance of Storage.
     */
    private Storage(){
	mainList = DataFileReadWrite.getListFromFile();
	searchTree = new TreeMap<String, ToDoItem>();
	toDoItemList = new ArrayList<ToDoItem>();
	eventList = new ArrayList<EventItem>();
	taskList = new ArrayList<TaskItem>();
	completedList = new Stack<ToDoItem>();
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
     * Gets the Storage instance.
     * @return The only Storage instance of this class.
     */
    public static Storage getInstance(){
	if(storage==null)
	    storage = new Storage();
	return storage;
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
	    DataFileReadWrite.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {

	}
	return item;	
    }
    
    /**
     * Updates the item with the given id with the specified changes.
     * @param id The id of the item to update.
     * @param description The new description of the target object.
     * @param priority The new priority for the item to be updated, use a negative number to indicate
     * 			no change to priority.
     * @param start The new start date for the item, or null if start date is not to be changed.
     * @param end The new end date for the item, or null if end date is not to be changed.
     * @return The updated ToDoItem.
     * @throws IllegalArgumentException if there is no such item with the given id.
     */
    public ToDoItem[] updateItem(String id, String description, int priority, Date start, Date end) {
	assert id!=null && !id.isEmpty();
	
	ToDoItem[] list = new ToDoItem[2];
	ToDoItem target = searchTree.get(id);
	
	if(target==null){
	    throw new IllegalArgumentException("No such ID");
	}
	
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
	    DataFileReadWrite.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {

	}
	return list;	
    }
    
    /**
     * Gets a list of ToDoItem objects whose description contains this keyword.
     * @param keyword The keyword to search in the objects.
     * @return An array of ToDoItem objects containing all of the given values or null
     * 		if the list is empty.
     */
    public ToDoItem[] searchItems(String keyword, int priority, Date start, Date end){
	ArrayList<ToDoItem> temp = mainList;;
	if(keyword!=null && !keyword.isEmpty()){
	    temp = searchByKeyword(temp, keyword);
	}
	if(priority!=-1){
		temp = searchByPriority(temp, priority);
	}
	if(start!=null){
	    temp = searchByStartDate(temp, start);
	}
	if(end!=null){
	    temp = searchByEndDate(temp, end);
	}
	if(temp.isEmpty()){
	    return null;
	}
	return temp.toArray(new ToDoItem[temp.size()]);
    }
    
    /**
     * Gets a list of items with the keyword in their description from the given list.
     * @param list The list to perform search on.
     * @return An ArrayList of ToDoItem objects whose description contain the keyword.
     */
    private ArrayList<ToDoItem> searchByKeyword(ArrayList<ToDoItem> list, String keyword){
	assert list!=null && keyword!=null && !keyword.isEmpty();
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>();
	for(ToDoItem i: list){
	    if(i.getDescription().contains(keyword)){
		temp.add(i);
	    }
	}
	return temp;
    }
    
    /**
     * Gets a list of items with the given priority from the given list.
     * @param list The list to perform search on.
     * @param priority The priority value used to filter the items.
     * @return An Arraylist of ToDoItem objects with the given priority level.
     */
    private ArrayList<ToDoItem> searchByPriority(ArrayList<ToDoItem> list, int priority){
	assert list!=null && priority>=0;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>();
	for(ToDoItem i: list){
	    if(i.getPriority()==priority){
		temp.add(i);
	    }
	}
	return temp;
    }
    
    /**
     * Gets a list of items with the given start date drom the given list.
     * @param list The list to perform search on.
     * @param start The start date value to search.
     * @return An ArrayList of ToDoItem objects with the given start date.
     */
    private ArrayList<ToDoItem> searchByStartDate(ArrayList<ToDoItem> list, Date start){
	assert start!=null && list!=null;
	ArrayList<ToDoItem> temp = new ArrayList<>();
	for(ToDoItem i: list){
	    if((i instanceof EventItem) && ((EventItem)i).getStartDate().equals(start)){
		temp.add(i);
	    }
	}
	return temp;
    }
    
    /**
     * Gets a list of items with the given end date/deadline.
     * @param list The list to perform search on.
     * @param end The end date/deadline value to search.
     */
    private ArrayList<ToDoItem> searchByEndDate(ArrayList<ToDoItem> list, Date end){
	assert list!=null && end!=null;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>();
	for(ToDoItem i: list){
	    if(((i instanceof EventItem) && ((EventItem)i).getEndDate().equals(end)) ||
		    ((i instanceof TaskItem) && ((TaskItem)i).getDeadline().equals(end))){
		temp.add(i);
	    }
	}
	return temp;
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
     * Gets a list that is sorted by start date in ascending order.
     * @return An array of ToDoItem sorted in ascending order by start date
     * 		or null if there is no item in the storage.
     .*/
    public ToDoItem[] getListSortedByStartDate(){
	if(mainList.isEmpty()){
	    return null;
	}
	Collections.sort(mainList, new StartDateComparator());
	return mainList.toArray(new ToDoItem[mainList.size()]);
    }
    
    /**
     * Gets a list that is sorted by end date in ascending order.
     * @return An array of ToDoItem sorted in ascending order by end date
     * 		or null if there is no item in the storage.
     .*/
    public ToDoItem[] getListSortedByEndDate(){
	if(mainList.isEmpty()){
	    return null;
	}
	Collections.sort(mainList, new EndDateComparator());
	return mainList.toArray(new ToDoItem[mainList.size()]);
    }
    
    /**
     * Gets a list of ToDoItem sorted in descending order by priority level.
     * @return An array of ToDoItem sorted in descending order by priority or
     * 		null if there is no item in this storage.
     */
    public ToDoItem[] getListSortedByPriority(){
	if(mainList.isEmpty()){
	    return null;
	}
	Collections.sort(mainList, Collections.reverseOrder(new PriorityComparator()));
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
     * @throws IllegalArgumentException if no item with the given ID is found.
     */
    public ToDoItem removeItem(String id){
	assert id!=null && !id.isEmpty();
	
	ToDoItem temp = searchTree.remove(id);
	if(temp==null){
	    throw new IllegalArgumentException("No such ID.");
	}
	mainList.remove(temp);
	if(temp instanceof TaskItem){
	    taskList.remove((TaskItem)temp);
	} else if(temp instanceof EventItem){
	    eventList.remove((EventItem)temp);
	} else{
	    toDoItemList.remove(temp);
	}
	try {
	    DataFileReadWrite.writeListToFile(mainList, "output.txt");
	} catch (IOException e) {
	    
	}
	return temp;
    }
    
    /**
     * Marks the item with the given ID as completed.
     * Note: Currently does not write the completed item to file.
     * @param id The id of the item to mark as completed.
     * @return The ToDoItem that is marked as completed.
     * @throws IllegalArgumentException if there is no item with the given ID.
     */
    public ToDoItem completeItem(String id){
	ToDoItem item = removeItem(id);
	if(item!=null){
	    if(item instanceof EventItem){
		completedList.push(new EventItem((EventItem)item));
	    } else if(item instanceof TaskItem){
		completedList.push(new TaskItem((TaskItem)item));
	    } else{
		completedList.push(new ToDoItem(item));
	    }
	    item.setStatus(ToDoItem.STATUS_COMPLETED);
	}
	return item;
    }
    
    /**
     * Unmark the most recent item that is marked completed.
     * @return The ToDoItem object that is unmarked from completed list.
     */
    public ToDoItem undoComplete(){
	ToDoItem temp = completedList.pop();
	addItem(temp);
	return temp;
    }
}
