package main;

/**
 * Defines each event in the To-Do list.
 * Each event is defined as having start and/or end date(s).
 * Possible usage: new EventItem(description, Date eventDate)	//start and end dates are the same with no specified time.
 * 		   new EventItem(description, DateTime eventDateTime)	//both start and end times will be the same.
 * 		   new EventItem(description, Date start, Date end)	//start and end dates can be different with no specified time.
 * 		   new EventItem(description, Date start, DateTime end)	//end has specified time while start does not.
 * 		   new EventItem(description, DateTime start, Date end)	//start has specified time while end does not.
 * 		   new EventItem(description, DateTime start, DateTime end)	//both start and end have specified times and can be different.
 * 		   set start/end dates with setStartDate()/setEndDate() respectively.
 * 		   set start/end times with setStartTime()/setEndTime() respectively.
 * 		   set date and time by using the above 2 uses together.
 * @author zixian
 */
public class EventItem extends ToDoItem {
    private static final String FORMAT_FILESTRING = "%1$s; %2$d; %3$s; %4$s; %5$s";
    private static final String FORMAT_PRINTSTRING = "%1$s %2$s %3$d %4$s %5$s %6$s";
    private static final String FORMAT_EMPTYTIME = " --:--";
    
    //Main data members
    private Date startDate, endDate;
    
    /* Constructors */
    //For events that start and end on the same day.
    public EventItem(String description, Date date){
	this(description, date, (date instanceof DateTime)? new DateTime(date): new Date(date));
    }
    
    public EventItem(String description, int priority, Date date){
	this(description, priority, date, (date instanceof DateTime)? new DateTime(date): new Date(date));
    }
    
    //For events that start and end on different days.
    public EventItem(String description, Date startDate, Date endDate){
	super(description);
	setStartDate(startDate);
	setEndDate(endDate);
    }
    
    public EventItem(String description, int priority, Date startDate, Date endDate){
	super(description, priority);
	setStartDate(startDate);
	setEndDate(endDate);
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
     * Returns the start date of this event.
     * @return A Date object of the start date or null if the start date is not set.
     */
    public Date getStartDate(){
	return startDate;
    }
    
    /**
     * Returns the end date of this event.
     * @return A Date object of the end date or null if the end date is not set.
     */
    public Date getEndDate(){
	return endDate;
    }

    /**
     * Sets the start date for this event.
     * Does nothing if the given startDate is null.
     */
    public void setStartDate(Date startDate){
	if(startDate!=null){
	    if(this.startDate!=null){
		int date = startDate.getDayOfMonth();
		int month = startDate.getMonth();
		int year = startDate.getYear();
		this.startDate.setDate(date, month, year);
	    } else{
		this.startDate = startDate;
	    }
	}
    }
    
    /**
     * Updates the start time for this event.
     * Does nothing if the specified time is invalid on 24-hour clock.
     */
    public void setStartTime(int hour, int minute){
	if(DateTime.isValidTime(hour, minute)){
	    if(!(startDate instanceof DateTime)){
		startDate = new DateTime(startDate);
	    } 
	    ((DateTime)startDate).setTime(hour, minute);
	}
    }
    
    /**
     * Updates the end time for this event.
     * Does nothing if the specified time is invalid on 24-hour clock.
     */
    public void setEndTime(int hour, int minute){
	if(DateTime.isValidTime(hour, minute)){
	    if(!(endDate instanceof DateTime)){
		endDate = new DateTime(endDate);
	    } 
	    ((DateTime)endDate).setTime(hour, minute); 
	}
    }
    
    /**
     * Sets the end date for this event.
     * Does nothing if the given endDate is null.
     */
    public void setEndDate(Date endDate){
	if(endDate!=null){
	    if(this.endDate!=null){
		int date = endDate.getDayOfMonth();
		int month = endDate.getMonth();
		int year = endDate.getYear();
		this.endDate.setDate(date, month, year);
	    } else{
		this.endDate = endDate;
	    }
	}
    }
    
    @Override
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status,
		     		getDateString(startDate), getDateString(endDate));
    }
    
    @Override
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status,
				getDateString(startDate), getDateString(endDate));
    }
}
