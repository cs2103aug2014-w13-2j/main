package main;

public class TaskItem extends ToDoItem {
    //Main data member
    private Date deadline;
    
    /* Constructor */
    public TaskItem(String description, Date deadline){
	super(description);
	setDeadline(deadline);
    }
    
    public TaskItem(String description, int priority, Date deadline){
	super(description, priority);
	setDeadline(deadline);
    }
    
    public Date getDeadline(){
	return deadline;
    }
    
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
}
