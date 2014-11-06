package edu.dynamic.dynamiz.structure;

import java.util.Comparator;

/**
 * Defines the comparator object that compares 2 ToDoItem objects by their priority level.
 * 
 * Constructor
 * PriorityComparator()	//Creates a new instance of this comparator.
 * 
 * Public Methods
 * int compare(ToDoItem item1, ToDoItem item2)	//Compares the 2 items
 * 
 * @author A0110781N
 */
public class PriorityComparator implements Comparator<ToDoItem> {
    
    /**
     * Creates a new instance of this comparator.
     */
    public PriorityComparator(){
	
    }
    
    @Override
    /**
     * Compares 2 ToDoItem objects by their priority level.
     * @param item1 The 1st of the 2 tiems to be compared.
     * @param item2 The 2nd of the 2 items to be compared.
     * @return item1.getPriority()-item2.getPriority()
     */
    public int compare(ToDoItem item1, ToDoItem item2){
	assert item1!=null && item2!=null;
	return item1.getPriority()-item2.getPriority();
    }
}
