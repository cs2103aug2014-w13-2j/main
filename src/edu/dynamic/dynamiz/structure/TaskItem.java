package edu.dynamic.dynamiz.structure;

/**
 * Defines a task.
 * A task is an item in the To-Do List with specified deadline.
 * 
 * Constructor
 * TaskItem(String description, MyDate deadline) //Creates a new instance of this task.
 * TaskItem(String description, int priority, MyDate deadline)	//Creates a new instance of this task.
 * TaskItem(TaskItem task)	//Creates a new copy of the given TaskItem.
 * TaskItem(ToDoItem item, MyDate deadline)	//Converts the given ToDoItem into a TaskItem.
 * 	  
 * Public Methods
 * int compareTo(ToDoItem item)	//Compares this task with the given ToDoItem.
 * MyDate getDeadline()	//Gets the deadline for this task.
 * String getDeadlineString()	//Gets the String representation of this task's deadline.
 * String getFeedbackString()	//Gets the String representation of this task used in feedback.
 * void setDeadline(MyDate deadline)	//Sets the deadline for this task.
 * String toFileString()	//Gets the String representation of this task used in file I/O.
 * String toString()	//Gets the String representation of this task.
 * 
 * @author zixian
 */
public class TaskItem extends ToDoItem {
    //String formatting used in this class
    private static final String FORMAT_EMPTYTIME = " --:--";
    private static final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
	    						"Deadline: %4$s\n"+"Status: %5$s";
    private static final String FORMAT_FILESTRING = "%1$s; %2$d; %3$s; --/--/---- --:--; %4$s";
    private static final String FORMAT_PRINTSTRING = "%1$s %2$s %3$d %4$s --/--/---- --:-- %5$s";
    
    //Main data member
    private MyDate deadline;
    
    /* Constructor */
    /**
     * Creates a new task with the specified deadline, using default priority level.
     * @param description This task's description.
     * @param deadline This task's deadline.
     */
    public TaskItem(String description, MyDate deadline){
	super(description);
	setDeadline(deadline);
    }
    
    /**
     * Creates a new task with the given priority and deadline.
     * @param description This task's description.
     * @param priority This task's priority.
     * @param deadline This task's deadline.
     */
    public TaskItem(String description, int priority, MyDate deadline){
	super(description, priority);
	setDeadline(deadline);
    }
    
    /**
     * The copy constructor for TaskItem class.
     * @param task The TaskItem to make a copy instance of.
     */
    public TaskItem(TaskItem task){
	super(task);
	if(task.getDeadline() instanceof MyDateTime){
	    setDeadline(new MyDateTime(task.getDeadline()));
	} else{
	    setDeadline(new MyDate(task.getDeadline()));
	}
    }
    
    /**
     * Converts the given item into a TaskItem.
     * @param item The item to convert into a TaskItem.
     * @param deadline The deadline for this task.
     * @return An independent, downcasted copy of the original item.
     */
    public TaskItem(ToDoItem item, MyDate deadline){
	super(item.getId(), item.getDescription(), item.getPriority(), item.getStatus());
	assert deadline!=null;
	setDeadline(deadline);
    }
    
    /**
     * Returns the string representation of the given Date object formatted
     * for EventItem.
     * Format is DD/MM/YYYY HH:MM as defined in DateTime class.
     * Null time is --:--
     * @param date The MyDate item to format into String.
     */
    private String getDateString(MyDate date){
	assert date!=null;
	if(date instanceof MyDateTime){
	    return date.toString();
	} else{
	    return date.toString()+FORMAT_EMPTYTIME;
	}
    }
    
    /**
     * Gets the deadline for this task.
     * @return A Date reference to the deadline for this task.
     */
    public MyDate getDeadline(){
	return deadline;
    }
    
    /**
     * Returns the string representation of this task's deadline.
     * @return A String representation of the deadline in this class.
     */
    public String getDeadlineString(){
	return getDateString(deadline);
    }
    
    /**
     * Sets the deadline date and/or time for this task.
     * Does nothing if specified deadline is null.
     * Equivalent to setDeadlineDate() if deadline is not a DateTime object.
     * @param deadline the new deadline of this task.
     */
    public void setDeadline(MyDate deadline){
	assert deadline!=null;
	this.deadline = deadline;
    }
    
    /**
     * Sets the deadline date for this task.
     * Does nothing if specified deadline is null.
     * @param deadline The date of this task's new deadline.
     */
    public void setDeadlineDate(MyDate deadline){
	assert deadline!=null;
	this.deadline.setDate(deadline.getDayOfMonth(), deadline.getMonth(), deadline.getYear());
    }
    
    /**
     * Sets the deadline time.
     * Does nothing if the specified time is invalid on the 24-hour clock.
     * @param hour The hour parameter of the new deadline time.
     * @param minute The minute parameter of the new deadline time.
     */
    public void setDeadlineTime(int hour, int minute){
	if(MyDateTime.isValidTime(hour, minute)){
	    if(!(this.deadline instanceof MyDateTime)){
		this.deadline = new MyDateTime(this.deadline);
	    }
	    ((MyDateTime)deadline).setTime(hour, minute);
	}
    }
    
    @Override
    /**
     * Gets the String representation of this item.
     * @return The String representation of this item.
     */
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status, getDateString(deadline));
    }
    
    @Override
    /**
     * Gets the feedback String representation of this item.
     * @return The feedback String representation of this item.
     */
    public String getFeedbackString(){
	return String.format(FORMAT_FEEDBACKSTRING, id, description, priority, getDateString(deadline), status);
    }
    
    @Override
    /**
     * Gets the String representation of this item to be used in file I/O.
     * @return The file String representaiton of this item.
     */
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status, getDateString(deadline));
    }
    
    @Override
    /**
     * Compares this item with the given ToDoItem.
     * @param item The ToDoItem to compare with.
     * @return Negative number if this item has 'pending' status while item has 'completed' status,
     * 		item is neither TaskItem nor EventItem or this has earlier deadline, this item has higher priority,
     * 		, this item has smaller ID number in decreasing order of precedence, 0 if both items have same 
     * 		field values, and positive number otherwise.
     */
    public int compareTo(ToDoItem item){
	assert item!=null;
	int result;
	if(status.equals(STATUS_PENDING) && item.getStatus().equals(STATUS_COMPLETED)){
	    return -1;
	} else if(status.equals(STATUS_COMPLETED) && item.getStatus().equals(STATUS_PENDING)){
	    return 1;
	} else if(item instanceof TaskItem){
	    if((result = deadline.compareTo(((TaskItem)item).getDeadline()))!=0){
		return result;
	    }
	} else if(item instanceof EventItem){
	    if((result = deadline.compareTo(((EventItem)item).getStartDate()))!=0){
		return result;
	    }
	} else{
	    return -1;
	}
	if(priority!=item.getPriority()){
	    return item.getPriority()-priority;
	}
	return id-item.getId();
    }
}
