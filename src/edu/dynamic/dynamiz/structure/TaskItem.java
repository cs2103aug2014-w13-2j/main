package edu.dynamic.dynamiz.structure;


/**
 * Defines a task.
 * A task is an item in the To-Do List with specified deadline.
 * Usage: new TaskItem(description, Date deadline) //deadline without specific time.
 * 	  new TaskItem(description, DateTime deadline)	//deadline with specific time.
 * 	  new TaskItem(description, priority, deadline)	//to initialise priority.
 * 	  setStatus(status)	//to set task status.
 * 	  setPriority(priority)	//to set priority.
 * @author zixian
 */
public class TaskItem extends ToDoItem {
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
     */
    public TaskItem(String description, MyDate deadline){
	super(description);
	setDeadline(deadline);
    }
    
    /**
     * Creates a new task with the given priority and deadline.
     */
    public TaskItem(String description, int priority, MyDate deadline){
	super(description, priority);
	setDeadline(deadline);
    }
    
    //Copy constructor
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
     */
    private String getDateString(MyDate date){
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
     */
    public void setDeadline(MyDate deadline){
	if(deadline!=null){
	    this.deadline = deadline;
	}
    }
    
    /**
     * Sets the deadline date for this task.
     * Does nothing if specified deadline is null.
     */
    public void setDeadlineDate(MyDate deadline){
	if(deadline!=null){
	    this.deadline.setDate(deadline.getDayOfMonth(), deadline.getMonth(), deadline.getYear());
	}
    }
    
    /**
     * Sets the deadline time.
     * Does nothing if the specified time is invalid on the 24-hour clock.
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
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status, getDateString(deadline));
    }
    
    @Override
    public String getFeedbackString(){
	return String.format(FORMAT_FEEDBACKSTRING, id, description, priority, getDateString(deadline), status);
    }
    
    @Override
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status, getDateString(deadline));
    }
}
