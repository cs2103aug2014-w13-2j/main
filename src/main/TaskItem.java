package main;

import java.util.ArrayList;

/**
 * Defines each task item in the To-Do list.
 * This class does not implement Comparable interface.
 * To sort by any of the member attributes, please
 * use a Comparator class that operates on this class
 * instead.
 * @author zixian
 * Note: Please update author list if you have
 * made any changes here.
 */
public class TaskItem {
    private static final String EMPTY_CATEGORY = "---";
    
    private static final String STATUS_COMPLETED = "completed";
    private static final String STATUS_PENDING = "pending";
    
    enum PRIORITY_TYPE {VERY_URGENT, URGENT, NORMAL};
    enum STATUS_TYPE {PENDING, COMPLETED};
    
    private String description;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private ArrayList<String> category = new ArrayList<String>();
    private PRIORITY_TYPE priority = PRIORITY_TYPE.NORMAL;
    private STATUS_TYPE status = STATUS_TYPE.PENDING;
    
    /**
     * Creates a new TaskItem with the given description.
     */
    public TaskItem(String description){
	this.description = description;
    }
    
    public String getDescription(){
	return description;
    }
    
    /**
     * Returns the start date in the format used in
     * the text file.
     */
    public String getStartDate(){
	return startDate.toString();
    }
    
    /**
     * Returns the end date in the format used in
     * the text file.
     */
    public String getEndDate(){
	return endDate.toString();
    }
    
    /**
     * Returns a comma-separated list of categories this
     * item falls in.
     * Eg. "cs2103,project,code"
     */
    public String getCategory(){
	return "";
    }
    
    /**
     * Returns the priority value in the form that is
     * saved/read to/from the file.
     */
    public String getPriority(){
	switch(priority){
	    case VERY_URGENT: return "high";
	    case URGENT: return "mid";
	    case NORMAL: return "low";
	    default: return "";
	}
    }
    
    /**
     * Returns the status in the form that is saved/read
     * to/from the file.
     */
    public String getStatus(){
	switch(status){
	    case PENDING: return "pending";
	    case COMPLETED: return "completed";  
	    default: return "";
	}
    }
    
    /**
     * Represents this task item in the format
     * priority'\t'description'\t'start_date'\t'end_date'\t'category.
     */
    //To be updated/modified for listing purposes.
    public String toString(){
	String output = getPriority()+"\t";
	output+=getDescription()+"\t";
	output+=getStartDate()+"\t";
	output+=getEndDate()+"\t";
	output+=getCategory()+"\t";
	return output;
    }

    /**
     * Sets the description for this to the new
     * description.
     */
    public void setDescription(String description){
	if(!description.isEmpty())
	    this.description = description;
    }
    
    public void setStartDate(int date, int month, int year){
	startDate.setDate(date, month, year);
    }
    
    public void setStartDate(int date, int month, int year,
	    		     int hour, int minute){
	startDate.setDate(date, month, year, hour, minute);
    }
    
    public void setEndDate(int date, int month, int year){
	endDate.setDate(date, month, year);
    }
    
    public void setEndDate(int date, int month, int year,
	    		     int hour, int minute){
	endDate.setDate(date, month, year, hour, minute);
    }

    /**
     * Adds the specified category to this task.
     */
    public void addCategory(String category){
	   this.category.add(category);
    }
    
    /**
     * Sets the priority level of this task to the given
     * priority level, defaulting to 'low' if invalid
     * string is used as parameter.
     */
    public void setPriorityType(String priority){
	if(priority.equals("high")){
	    this.priority = PRIORITY_TYPE.VERY_URGENT;
	}else if(priority.equals("mid")){
	    this.priority = PRIORITY_TYPE.URGENT;
	}else{
	    this.priority = PRIORITY_TYPE.NORMAL;
	}
    }
    
    /**
     * Sets the status of this task. If the specified status is invalid,
     * this task's status is set to the default status.
     * Acceptable status values: "pending", "completed"
     */
    //To-Do: Update this method asfter adding in more status types.
    public void setStatus(String status){
	if(status.equals(STATUS_COMPLETED)){
	    this.status = STATUS_TYPE.COMPLETED;
	} else{
	    this.status = STATUS_TYPE.PENDING;
	}
    }
    
    /**
     * Marks this task as completed.
     */
    public void completeTask(){
	status = STATUS_TYPE.COMPLETED;
    }
    
    /**
     * Splits comma-separated categoryString into an array of categories.
     * Pre-condition: categoryString is in the form "x,y,etc", which is ensured
     * 			by file parsing in FileHandler.
     * @returns A String array of categories.
     */
    //Consider the case of "CS2103T,"
    public static String[] splitCategories(String categoryString){
	if(categoryString.equals(TaskItem.EMPTY_CATEGORY)){
	    return new String[0];
	}
	return categoryString.split(",");
    }
}
