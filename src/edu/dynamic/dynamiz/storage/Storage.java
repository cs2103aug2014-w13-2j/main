package edu.dynamic.dynamiz.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.dynamic.dynamiz.controller.DataFileReadWrite;
import edu.dynamic.dynamiz.controller.WriteToFileThread;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.EndDateComparator;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.PriorityComparator;
import edu.dynamic.dynamiz.structure.StartDateComparator;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0110781N
/**
 * Defines the storage class holding the list of tasks and events.
 * 
 * Public Methods(currently that can be used)
 * static Storage getInstance()	//gets the Storage instance
 * ToDoItem addItem(ToDoItem item)	//Adds the given item to the list.
 * ToDoItem[] getList(OptionType[] options)	//Gets the full list of items.
 * ToDoItem[] getList(String[] keywords, int[] priority, MyDate[] start, MyDate end, OptionsType[] optionsList)
 * 						//Gets the filtered list of items.
 * ToDoItem removeItem(String id)	//Removes the item with the specified id from this storage.
 * ToDoItem[] searchItems(int id)	//Gets the ToDoItem with the given ID.
 * ToDoItem[] searchItems(String keyword, int priority, MyDate start, MyDate end, OptionType[] optList)	//Gets a list of items with the given parameter values.
 * ToDoItem[] updateItem(String id, String description, int priority, Date start, Date end)	//Updates the ToDoItem with the given id with the specified details. 
 * ToDoItem markItem(int id)	//Marks the ToDoItem with the given id as completed
 * ToDoItem unmarkItem(int id)	//Marks the ToDoItem with the given id as not completed.
 * void markItem(ToDoItem item)	//Marks the given ToDoItem as completed.
 * void unmarkItem(ToDoItem item)	//Marks the given ToDoItem as not completed.
 */
public class Storage {
    private static final String TODOLIST_FILENAME = "todo.txt";
    private static final String OUTPUT_FILENAME = TODOLIST_FILENAME;
    
    //Error messages
    private static final String MSG_ITEMNOTFOUND = "Item not found.";
    private static final String MSG_INVALIDDATES = "Illogical start and end date relation.";
    
    //Main data members
    private ArrayList<ToDoItem> mainList;	//The main list
    private ArrayList<ToDoItem> toDoItemList;	//Holds items without any dates
    private ArrayList<EventItem> eventList;	//Holds events
    private ArrayList<TaskItem> taskList;	//Holds deadline tasks
    private TreeMap<Integer, ToDoItem> searchTree;	//Maps each item to its ID for faster search by ID
    private static Storage storage;	//Holds the only instance of the Storage object
    
    /**
     * Creates a new instance of Storage.
     */
    private Storage(){
	mainList = DataFileReadWrite.getListFromFile(TODOLIST_FILENAME);
	searchTree = new TreeMap<Integer, ToDoItem>();
	toDoItemList = new ArrayList<ToDoItem>();
	eventList = new ArrayList<EventItem>();
	taskList = new ArrayList<TaskItem>();
	
	//Adds each item in mainList to ID search tree
	for(ToDoItem temp: mainList){
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
	Thread writeToFile = new WriteToFileThread(mainList.toArray(new ToDoItem[mainList.size()]), OUTPUT_FILENAME);
	writeToFile.run();
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
    public ToDoItem[] updateItem(int id, String description, int priority, MyDate start, MyDate end) throws IllegalArgumentException{
	ToDoItem[] list = new ToDoItem[2];
	ToDoItem target = searchTree.get(id);
	
	if(target==null){
	    throw new IllegalArgumentException(MSG_ITEMNOTFOUND);
	}
	
	//Makes a copy of the current version of the object
	list[0] = makeCopy(target);
	
	if(description!=null && !description.isEmpty()){
	    target.setDescription(description);
	}

	if(ToDoItem.isValidPriority(priority)){
	    target.setPriority(priority);
	}
	
	//Checks for illogical date and time range.
	if(start!=null && end!=null){
	    if(start.compareIncludingTime(end)>0){
		throw new IllegalArgumentException(MSG_INVALIDDATES);
	    } else{
		target = new EventItem(target, start, end);
		removeItem(target.getId());
		addItem(target);
	    }
	} else if(start!=null && !(target instanceof EventItem)){
	    if(target instanceof TaskItem){
		if(((TaskItem)target).getDeadline().compareIncludingTime(start)<0){
		    throw new IllegalArgumentException(MSG_INVALIDDATES);
		}
		target = new EventItem((TaskItem)target, start);
	    } else{
		target = new EventItem(target, start);
	    }
	    removeItem(target.getId());
	    addItem(target);
	} else if(start!=null){
	    if(((EventItem)target).getEndDate().compareIncludingTime(start)<0){
		throw new IllegalArgumentException(MSG_INVALIDDATES);
	    }
	    ((EventItem)target).setStartDate(start);
	} else if(end!=null){
	    if(target instanceof EventItem){
		if(((EventItem)target).getStartDate().compareIncludingTime(end)>0){
		    throw new IllegalArgumentException(MSG_INVALIDDATES);
		}
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
	
	Thread writeToFile = new WriteToFileThread(mainList.toArray(new ToDoItem[mainList.size()]), OUTPUT_FILENAME);
	writeToFile.run();
	return list;	
    }
    
    /**
     * Gets the ToDoItem with the given ID, or null if no such ToDoItem exists.
     * @param id The ID of the ToDoItem to search.
     * @return A 1-element array of the ToDoItem with the given ID, or null, if no such ToDoItem exists.
     */
    public ToDoItem[] searchItems(int id) throws IllegalArgumentException {
	ToDoItem temp = searchTree.get(id);
	if(temp==null){
	    throw new IllegalArgumentException(MSG_ITEMNOTFOUND);
	}
	ToDoItem[] arr = new ToDoItem[1];
	arr[0] = temp;
	return arr;
    }
    
    /**
     * Gets a list of ToDoItem objects whose description contains this keyword.
     * @param keyword The keyword to search in the objects or null if no search by keyword is needed.
     * @param priority The priority level to the item(s) to search or -1 if not needed.
     * @param start the start date of the item(s) to search or null if search by start date is not needed.
     * @param end The end date of the item(s) to search or null if search by end date is not needed.
     * @param status the status of the item(s)to search or null if search by status is not needed.
     * @param optList The list of options(in descending precedence) to sort search results by.
     * @return An array of ToDoItem objects containing all of the given values or null
     * 		if the list is empty.
     */
    public ToDoItem[] searchItems(String keyword, int priority, MyDate start, MyDate end, String status, OptionType[] optList){
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
	if(status!=null && !status.isEmpty()){
	    temp = searchByStatus(temp, status);
	}
	if(temp.isEmpty()){
	    return null;
	}
	if(optList!=null){
	    int size = optList.length;
	    while(size-->0){
		sortListByOption(temp, optList[size]);
	    }
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
	    if(i.getDescription().toLowerCase().contains(keyword.toLowerCase())){
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
    private ArrayList<ToDoItem> searchByStartDate(ArrayList<ToDoItem> list, MyDate start){
	assert start!=null && list!=null;
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>();
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
    private ArrayList<ToDoItem> searchByEndDate(ArrayList<ToDoItem> list, MyDate end){
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
     * Gets a list of ToDoItem with the given status.
     * @param list The list to perform search on.
     * @param status The status to search for.
     * @return An ArrayList of ToDoItem from the given list with the given status.
     */
    private ArrayList<ToDoItem> searchByStatus(ArrayList<ToDoItem> list, String status){
	assert list!=null && status!=null && !status.isEmpty();
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>();
	for(ToDoItem i: list){
	    if(i.getStatus().equalsIgnoreCase(status)){
		temp.add(i);
	    }
	}
	return temp;
    }
    
    /**
     * Gets the list of tasks and events in an array sorted according to optionsList.
     * @param options The list of data fields to sort the list by in descending order of precedence
     * 		or null if no other sorting criteria is required.
     * 		Eg. {a, b} means most importantly, sort by a. For all items with same value of a, sort by b.
     * @return An array of ToDoItem objects sorted according to sorting criteria or null
     * 		if the storage has no item.
     */
    private ToDoItem[] getList(OptionType[] options){
	if(mainList.isEmpty()){
	    return null;
	}
	Collections.sort(mainList);
	if(options!=null){
	    int size = options.length;
	    while(size-->0){
		sortListByOption(mainList, options[size]);
	    }
	}
	return mainList.toArray(new ToDoItem[mainList.size()]);
    }
    
    /**
     * Gets the list of items filtered using the given field values, sorted using the given options list
     * in descending order of precedence. If no filtering values are used at all, the full list of items
     * will be returned instead.
     * @param priority The list of priority values used for filtering or null if not used.
     * @param start The list of start dates used for filtering or null if not used.
     * @param end The list of end dates used for filtering or null if not used.
     * @param options The list of field types in descending order of precedence used to sort the list.
     * @return A filtered array of ToDoItem sorted using options.
     */
    public ToDoItem[] getList(int[] priority, MyDate[] start, MyDate[] end, OptionType[] options){
	if(priority==null && start==null && end==null){
	    return getList(options);
	}
	ArrayList<ToDoItem> temp = new ArrayList<ToDoItem>();	//To hold the resulting list
	
	//Use BST for log(N) search time.
	TreeSet<Integer> priorityList = new TreeSet<Integer>();
	TreeSet<MyDate> startList = new TreeSet<MyDate>();
	TreeSet<MyDate> endList = new TreeSet<MyDate>();
	
	//Fills the hash tables with distinct values(if applicable).
	if(priority!=null){
	    for(int i: priority){
		if(!priorityList.contains(i)){
		    priorityList.add(i);
		}
	    }
	}
	
	if(start!=null){
	    for(MyDate i: start){
		if(!startList.contains(i)){
		    startList.add(i);
		}
	    }
	}
	
	if(end!=null){
	    for(MyDate i: end){
		if(!endList.contains(i)){
		    endList.add(i);
		}
	    }
	}
	
	//Performs big union(OR) filtering by values.
	Iterator<ToDoItem> itr = mainList.iterator();
	ToDoItem item;
	while(itr.hasNext()){
	    item = itr.next();
	    if(priorityList.contains(item.getPriority())){
		temp.add(item);
		continue;
	    }
	    if((item instanceof EventItem)){
		if((startList.contains(((EventItem)item).getStartDate())) ||
			endList.contains(((EventItem)item).getEndDate())){
		    	temp.add(item);
		    	continue;
		}
	    } else if((item instanceof TaskItem) && endList.contains(((TaskItem)item).getDeadline())){
		temp.add(item);
		continue;
	    }
	}
	
	//Sorts the filtered list.
	Collections.sort(temp);
	if(options!=null){
	    int size = options.length;
	    while(size-->0){
		sortListByOption(temp, options[size]);
	    }
	}
	
	//Returns the filtered list as an array.
	if(temp.isEmpty()){
	    return null;
	}
	return temp.toArray(new ToDoItem[temp.size()]);
    }
    
    /**
     * Sorts the given list by the given option type.
     * @param list The list to sort.
     * @optType The option to sort the list by.
     */
    private void sortListByOption(ArrayList<ToDoItem> list, OptionType optType){
	switch(optType){
	    case PRIORITY: Collections.sort(list, Collections.reverseOrder(new PriorityComparator()));
	    	break;
	    case START_TIME: Collections.sort(list, new StartDateComparator());
	    	break;
	    case END_TIME: Collections.sort(list, new EndDateComparator());
	    	break;
	    default: break;
	}
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
	Collections.sort(eventList, new StartDateComparator());
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
	Collections.sort(eventList, new EndDateComparator());
	return eventList.toArray(new EventItem[eventList.size()]);
    }
    
    /**
     * Gets the list of deadline tasks sorted in ascending order of their deadlines.
     * @return An array of TaskItem objects sorted in ascending order of their deadlines.
     */
    public TaskItem[] getTasksSortedByDeadline(){
	if(taskList.isEmpty()){
	    return null;
	}
	Collections.sort(taskList, new EndDateComparator());
	return taskList.toArray(new TaskItem[taskList.size()]);
    }
    
    /**
     * Removes the ToDoItem with the given ID from the list.
     * For use by CommandAdd's undo method and CommandDelete's execute method.
     * @param id The id of the ToDoItem object to remove from the list.
     * @return The ToDoItem object that was removed from the list by this operation.
     * @throws IllegalArgumentException if no item with the given ID is found.
     */
    public ToDoItem removeItem(int id) throws IllegalArgumentException {	
	ToDoItem temp = searchTree.remove(id);
	if(temp==null){
	    throw new IllegalArgumentException(MSG_ITEMNOTFOUND);
	}
	mainList.remove(temp);
	if(temp instanceof TaskItem){
	    taskList.remove((TaskItem)temp);
	} else if(temp instanceof EventItem){
	    eventList.remove((EventItem)temp);
	} else{
	    toDoItemList.remove(temp);
	}
	Thread writeToFile = new WriteToFileThread(mainList.toArray(new ToDoItem[mainList.size()]), OUTPUT_FILENAME);
	writeToFile.run();
	return temp;
    }
    
    /**
     * Marks the item with the given ID as completed.
     * @param id The id of the item to mark as completed.
     * @return The ToDoItem that is marked as completed or null if the item has already been marked as completed.
     * @throws IllegalArgumentException if there is no item with the given ID.
     */
    public ToDoItem markItem(int id)throws IllegalArgumentException {
	ToDoItem item = searchTree.get(id);
	if(item==null){
	    throw new IllegalArgumentException(MSG_ITEMNOTFOUND);
	}
	if(item.getStatus().equals(ToDoItem.STATUS_COMPLETED)){
	    return null;
	}
	markItem(item);
	return item;
    }
    
    /**
     * Marks the given ToDoItem as completed.
     * @param item The ToDoItem to marks as completed.
     */
    public void markItem(ToDoItem item){
	assert item!=null;
	item.setStatus(ToDoItem.STATUS_COMPLETED);
	Thread writeToFile = new WriteToFileThread(mainList.toArray(new ToDoItem[mainList.size()]), OUTPUT_FILENAME);
	writeToFile.run();
    }
    
    /**
     * Unmark the most recent item that is marked completed.
     * @param id The ID of the ToDoItem to unmark.
     * @return The ToDoItem object that is unmarked from completed list or null if this item's status is
     * 		already not 'completed'.
     * @throws IllegalArgumentException if no item with the given ID exists.
     */
    public ToDoItem unmarkItem(int id) throws IllegalArgumentException {
	ToDoItem temp = searchTree.get(id);
	if(temp==null){
	    throw new IllegalArgumentException(MSG_ITEMNOTFOUND);
	}
	if(temp.getStatus().equals(ToDoItem.STATUS_PENDING)){
	    return null;
	}
	unmarkItem(temp);
	return temp;
    }
    
    /**
     * Unmark the most recent item that is marked completed.
     * @param item The ToDoItem to unmark.
     */
    public void unmarkItem(ToDoItem item){
	assert item!=null;
	item.setStatus(ToDoItem.STATUS_PENDING);
	Thread writeToFile = new WriteToFileThread(mainList.toArray(new ToDoItem[mainList.size()]), OUTPUT_FILENAME);
	writeToFile.run();
    }
    
    //Creates a duplicate copy of the given item.
    private ToDoItem makeCopy(ToDoItem item){
	assert item!=null;
	if(item instanceof EventItem){
	    return new EventItem((EventItem)item);
	} else if(item instanceof TaskItem){
	    return new TaskItem((TaskItem)item);
	} else{
	    return new ToDoItem(item);
	}
    }
}
