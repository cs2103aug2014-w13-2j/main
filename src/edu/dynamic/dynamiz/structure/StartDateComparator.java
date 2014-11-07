package edu.dynamic.dynamiz.structure;

import java.util.Comparator;

/**
 * Defines the comparator object to order ToDoItem by start date.
 * 
 * Constructor
 * StartDateComparator()	//Creates a new instance of this comparator.
 * 
 * Public Methods
 * int compare(ToDoItem item1, ToDoItem item2)	//Compares which item has later start date.
 */
//@author A0110781N
public class StartDateComparator implements Comparator<ToDoItem> {
    
    /**
     * Creates a new instance of this Comparator.
     */
    public StartDateComparator(){
	
    }
    
    @Override
    /**
     * Compares 2 ToDoItem objects by their start dates.
     * @param item1 The 1st of the 2 items to be compared.
     * @param item2 The 2nd of the 2 items to be compared.
     * @return A positive number if item1 has "larger" starting date than item2,
     * 		0 if both items have the "same" starting dates, and negative number if
     * 		item1 has "smaller" starting date than item2.
     * 		Comparison is such that ToDoItem is "larger" than any of its subclass and the "starting date"
     * 		of TaskItem object is its deadline.
     */
    public int compare(ToDoItem item1, ToDoItem item2){
	assert item1!=null && item2!=null;
	boolean isEvent1 = item1 instanceof EventItem;
	boolean isTask1 = item1 instanceof TaskItem;
	boolean isEvent2 = item2 instanceof EventItem;
	boolean isTask2 = item2 instanceof TaskItem;
	if(!(isEvent1 || isTask1) && (isEvent2 || isTask2)){
	    return 1;
	}
	if((isEvent1 || isTask1) && !(isEvent2 || isTask2)){
	    return -1;
	}
	if(!(isEvent1 || isTask1) && !(isEvent2 || isTask2)){
	    return 0;
	}
	if(isEvent1){
	    if(isEvent2){
		return ((EventItem)item1).getStartDate().compareTo(((EventItem)item2).getStartDate());
	    } else{
		return ((EventItem)item1).getStartDate().compareTo(((TaskItem)item2).getDeadline());
	    }
	} else if(isEvent2){
	    return ((TaskItem)item1).getDeadline().compareTo(((EventItem)item2).getStartDate());
	} else{
	    return ((TaskItem)item1).getDeadline().compareTo(((TaskItem)item2).getDeadline());
	}
    }
    
    @Override
    /**
     * Checks whether the given object is the same reference as this object.
     */
    public boolean equals(Object obj){
	return this==obj;
    }
}
