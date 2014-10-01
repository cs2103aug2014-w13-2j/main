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
    private Date deadline;
    
    /* Constructor */
    /**
     * Creates a new task with the specified deadline, using default priority level.
     */
    public TaskItem(String description, Date deadline){
	super(description);
	setDeadline(deadline);
    }
    
    /**
     * Creates a new task with the given priority and deadline.
     */
    public TaskItem(String description, int priority, Date deadline){
	super(description, priority);
	setDeadline(deadline);
    }
    
    //Copy constructor
    public TaskItem(TaskItem task){
	super(task);
	if(task.getDeadline() instanceof DateTime){
	    setDeadline(new DateTime(task.getDeadline()));
	} else{
	    setDeadline(new Date(task.getDeadline()));
	}
    }
    
    /**
     * Returns the string representation of the given Date object formatted
     * for EventItem.
     * Format is DD/MM/YYYY HH:MM as defined in DateTime class.
     * Null time is --:--
     */
    private String getDateString(Date date){
	if(date instanceof DateTime){
	    return date.toString();
	} else{
	    return date.toString()+FORMAT_EMPTYTIME;
	}
    }
    
    /**
     * Gets the deadline for this task.
     * @return A Date reference to the deadline for this task.
     */
    public Date getDeadline(){
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
    public void setDeadline(Date deadline){
	if(deadline!=null){
	    if(this.deadline==null){
		this.deadline = deadline;
	    } else{
		setDeadlineDate(deadline);
		if(deadline instanceof DateTime){
		    setDeadlineTime(((DateTime)deadline).getHour(), ((DateTime)deadline).getMinute());
		}
	    }
	}
    }
    
    /**
     * Sets the deadline date for this task.
     * Does nothing if specified deadline is null.
     */
    public void setDeadlineDate(Date deadline){
	if(deadline!=null){
	    this.deadline.setDate(deadline.getDayOfMonth(), deadline.getMonth(), deadline.getYear());
	}
    }
    
    /**
     * Sets the deadline time.
     * Does nothing if the specified time is invalid on the 24-hour clock.
     */
    public void setDeadlineTime(int hour, int minute){
	if(DateTime.isValidTime(hour, minute)){
	    if(!(this.deadline instanceof DateTime)){
		this.deadline = new DateTime(this.deadline);
	    }
	    ((DateTime)deadline).setTime(hour, minute);
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
