package edu.dynamic.dynamiz.structure;


/**
 * Defines each event in the To-Do list.
 * Each event is defined as having start and/or end date(s).
 * Possible usage: new EventItem(description, Date eventDate)	//start and end dates are the same with no specified time.
 * 		   new EventItem(description, DateTime eventDateTime)	//both start and end times will be the same.
 * 		   new EventItem(description, Date start, Date end)	//start and end dates can be different with no specified time.
 * 		   new EventItem(description, Date start, DateTime end)	//end has specified time while start does not.
 * 		   new EventItem(description, DateTime start, Date end)	//start has specified time while end does not.
 * 		   new EventItem(description, DateTime start, DateTime end)	//both start and end have specified times and can be different.
 * 		   new EventItem(description, priority, date) or
 * 		   new EventItem(description, priority, start, end)	//to initialise priority.
 * 		   set start/end dates with setStartDate()/setEndDate() respectively.
 * 		   set start/end times with setStartTime()/setEndTime() respectively.
 * 		   set date and time by using the above 2 uses together.
 * @author zixian
 */
public class EventItem extends ToDoItem {
    private static final String FORMAT_EMPTYTIME = " --:--";
    private static final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
	    						"Start: %4$s\n"+"End: %5$s\n"+"Status: %6$s";
    private static final String FORMAT_FILESTRING = "%1$s; %2$d; %3$s; %4$s; %5$s";
    private static final String FORMAT_PRINTSTRING = "%1$s %2$s %3$d %4$s %5$s %6$s";
    
    
    //Main data members
    private MyDate startDate, endDate;
    
    /* Constructors */
    //For events that start and end on the same day.
    public EventItem(String description, MyDate date){
	this(description, date, (date instanceof MyDateTime)? new MyDateTime(date): new MyDate(date));
    }
    
    public EventItem(String description, int priority, MyDate date){
	this(description, priority, date, (date instanceof MyDateTime)? new MyDateTime(date): new MyDate(date));
    }
    
    //For events that start and end on different days.
    public EventItem(String description, MyDate startDate, MyDate endDate){
	super(description);
	setStartDate(startDate);
	setEndDate(endDate);
    }
    
    public EventItem(String description, int priority, MyDate startDate, MyDate endDate){
	super(description, priority);
	setStartDate(startDate);
	setEndDate(endDate);
    }
    
    /**
     * Creates a new instance that is a copy of the given event.
     * @param event The EventItem to be copied.
     * @return An instance of EventItem that is a different reference from event but has same field members
     * 		as event.
     */
    public EventItem(EventItem event){
	super(event);
	if(event.getStartDate() instanceof MyDateTime){
	    setStartDate(new MyDateTime(event.getStartDate()));
	} else{
	    setStartDate(new MyDate(event.getStartDate()));
	}
	if(event.getEndDate() instanceof MyDateTime){
	    setEndDate(new MyDateTime(event.getEndDate()));
	} else{
	    setEndDate(new MyDate(event.getEndDate()));
	}
    }
    
    /**
     * Creates a new instance of the given item as an event, while retaining all its existing values.
     * @param start The start date of this event.
     * @return An instance of EventItem that is downcasted from the given ToDoItem.
     */
    public EventItem(ToDoItem item, MyDate start){
	this(item, start, (start instanceof MyDateTime)? new MyDateTime(start): new MyDate(start));
    }
    
    /**
     * Creates a new instance of the given item as an event, while retaining all its existing values.
     * @param item The ToDoItem object to downcast.
     * @param start The start date of this event.
     * @param end The end date of this event.
     * @return An instance of EventItem that is downcasted from the given ToDoItem.
     */
    public EventItem(ToDoItem item, MyDate start, MyDate end){
	super(item.getId(), item.getDescription(), item.getPriority(), item.getStatus());
	setStartDate(start);
	setEndDate(end);
    }
    
    /**
     * Creates a new EventItem instance from the given TaskItem.
     * This is not typecasting but is a copy conversion.
     * @param task The TaskItem object to be converted.
     * @start The start date of the new event.
     */
    public EventItem(TaskItem task, MyDate start){
	this(task, start, task.getDeadline());
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
     * Returns the start date of this event.
     * @return A Date object of the start date or null if the start date is not set.
     */
    public MyDate getStartDate(){
	return startDate;
    }
    
    /**
     * Returns the string representation of the start date and/or time in this class.
     * @return A String representation of the start date and/or time used in this class.
     */
    public String getStartDateString(){
	return getDateString(startDate);
    }
    
    /**
     * Returns the end date of this event.
     * @return A Date object of the end date or null if the end date is not set.
     */
    public MyDate getEndDate(){
	return endDate;
    }
    
    /**
     * Returns the string representation of the end date and/or time in this class.
     * @return A String representation of the end date and/or time used in this class.
     */
    public String getEndDateString(){
	return getDateString(endDate);
    }

    /**
     * Sets the start date for this event.
     * Does nothing if the given startDate is null.
     */
    public void setStartDate(MyDate startDate){
	if(startDate!=null){
	    this.startDate = startDate;
	}
    }
    
    /**
     * Updates the start time for this event.
     * Does nothing if the specified time is invalid on 24-hour clock.
     */
    public void setStartTime(int hour, int minute){
	if(MyDateTime.isValidTime(hour, minute)){
	    if(!(startDate instanceof MyDateTime)){
		startDate = new MyDateTime(startDate);
	    } 
	    ((MyDateTime)startDate).setTime(hour, minute);
	}
    }
    
    /**
     * Updates the end time for this event.
     * Does nothing if the specified time is invalid on 24-hour clock.
     */
    public void setEndTime(int hour, int minute){
	if(MyDateTime.isValidTime(hour, minute)){
	    if(!(endDate instanceof MyDateTime)){
		endDate = new MyDateTime(endDate);
	    } 
	    ((MyDateTime)endDate).setTime(hour, minute); 
	}
    }
    
    /**
     * Sets the end date for this event.
     * Does nothing if the given endDate is null.
     */
    public void setEndDate(MyDate endDate){
	if(endDate!=null){
	    this.endDate = endDate;
	}
    }
    
    @Override
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status,
		     		getDateString(startDate), getDateString(endDate));
    }
    
    @Override
    public String getFeedbackString(){
	return String.format(FORMAT_FEEDBACKSTRING, id, description, priority, getDateString(startDate),
				getDateString(endDate), status);
    }
    
    @Override
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status,
				getDateString(startDate), getDateString(endDate));
    }
}
