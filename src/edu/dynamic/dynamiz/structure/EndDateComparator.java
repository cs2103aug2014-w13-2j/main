package edu.dynamic.dynamiz.structure;

import java.util.Comparator;

public class EndDateComparator implements Comparator<ToDoItem>{
    
    /**
     * Creates a new instance of this comparator.
     */
    public EndDateComparator(){
	
    }
    
    @Override
    /**
     * Compares 2 ToDoItem objects by their end dates.
     * @param item1 The 1st of the 2 items to compare.
     * @param item2 The 2nd of the 2 items to compare.
     * @return A positive number if item1 has "larger" end date than item2, 0 if both items
     * 		have the same end date, and negative number if item1 has "smaller"
     * 		end date than item2.
     * 		Comparison is such that ToDoItem is always larger than its subclasses, and TaskItem's
     * 		end date is its deadline.
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
		return ((EventItem)item1).getEndDate().compareTo(((EventItem)item2).getEndDate());
	    } else{
		return ((EventItem)item1).getEndDate().compareTo(((TaskItem)item2).getDeadline());
	    }
	} else if(isEvent2){
	    return ((TaskItem)item1).getDeadline().compareTo(((EventItem)item2).getEndDate());
	} else{
	    return ((TaskItem)item1).getDeadline().compareTo(((TaskItem)item2).getDeadline());
	}
    }
    
    @Override
    /**
     * Checks if the given object is the same reference as this.
     */
    public boolean equals(Object obj){
	return this==obj;
    }
}
