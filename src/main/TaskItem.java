package main;

/**
 * Defines a task.
 * A task is an item in the To-Do List with specified deadline.
 * @author zixian
 */
public class TaskItem extends ToDoItem {
    private static final String FORMAT_FILESTRING = "%1$s; %2$d; %3$s; --/--/---- --:--; %4$s";
    private static final String FORMAT_EMPTYTIME = " --:--";
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
     * @return A reference to the deadline for this task.
     */
    public Date getDeadline(){
	return deadline;
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
	if(!(this.deadline instanceof DateTime)){
	    this.deadline = new DateTime(this.deadline);
	}
	try{
	    ((DateTime)deadline).setTime(hour, minute);
	} catch(IllegalArgumentException e){
	    
	}
    }
    
    @Override
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status, getDateString(deadline));
    }
    
    @Override
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status, getDateString(deadline));
    }
}
