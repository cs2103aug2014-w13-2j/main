package edu.dynamic.dynamiz.structure;

/**
 * Defines each event in the To-Do list.
 * Each event is defined as having start and/or end date(s).
 * 
 * Constructors
 * EventItem(String description, MyDate eventDate)	//start and end dates are the same with no specified time.
 * EventItem(String description, MyDate start, MyDate end)	//start and end dates can be different with no specified time.
 * EventItem(String description, int priority, MyDate date)
 * EventItem(String description, int priority, MyDate start, MyDate end)	//The full form of constructor
 * EventItem(EventItem event)	//Creates a new copy of the given event.
 * EventItem(ToDoItem item, MyDate start, MyDate end)	//Converts ToDoItem to EventItem
 * EventItem(TaskItem task, MyDate start)	//Converts TaskItem into EventItem
 * 
 * Public Methods
 * MyDate getEndDate()	//Gets the end date for this event.
 * String getEndDateString()	//Gets the string representation of this event's end date.
 * MyDate getStartDate()	//gets the start date for this event.
 * String getStartDateString()	//Gets the string representation of this event's start date.
 * void setEndDate(MyDate end)	//Sets the end date for this event.
 * void setStartDate(MyDate start)	//Sets the start date for this event.
 * 
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
    /**
     * Creates a new instance of this event.
     * @param description The event's description.
     * @param date The event's start and end date.
     */
    public EventItem(String description, MyDate date){
	this(description, date, (date instanceof MyDateTime)? new MyDateTime(date): new MyDate(date));
    }
    
    /**
     * Creates a new instance of this event.
     * @param description The event's description.
     * @param priority The event's priority level.
     * @param date The event's start and end date.
     */
    public EventItem(String description, int priority, MyDate date){
	this(description, priority, date, (date instanceof MyDateTime)? new MyDateTime(date): new MyDate(date));
    }
    
    /**
     * Creates a new instance of this event.
     * @param description The event's description.
     * @param startDate The event's start date.
     * @param end The event's end date.
     */
    public EventItem(String description, MyDate startDate, MyDate endDate){
	super(description);
	setStartDate(startDate);
	setEndDate(endDate);
    }
    
    /**
     * Creates a new instance of this event.
     * @param description The event's description.
     * @param priority The event's priority.
     * @param startDate The event's start date.
     * @param endDate The event's end date.
     */
    public EventItem(String description, int priority, MyDate startDate, MyDate endDate){
	super(description, priority);
	setStartDate(startDate);
	setEndDate(endDate);
    }
    
    /**
     * Creates a copy of the given event. The resulting instance, e, is such that
     * e!=event and e.equals(event) returns true.
     * @param event The EventItem to be copied.
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
     * @param startDate The new start date for this event.
     */
    public void setStartDate(MyDate startDate){
	assert startDate!=null;
	this.startDate = startDate;
    }
    
    /**
     * Sets the end date for this event.
     * @param endDate The new end date for this event.
     */
    public void setEndDate(MyDate endDate){
	assert endDate!=null;
	this.endDate = endDate;
    }
    
    @Override
    /**
     * Gets a String representation of this event.
     * @return The String representation of this event.
     */
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status,
		     		getDateString(startDate), getDateString(endDate));
    }
    
    @Override
    /**
     * Gets a String representation of this event for feedback use.
     * @return The String representation of this event formatted for feedback purpose.
     */
    public String getFeedbackString(){
	return String.format(FORMAT_FEEDBACKSTRING, id, description, priority, getDateString(startDate),
				getDateString(endDate), status);
    }
    
    @Override
    /**
     * Gets the String representation of this event used for file I/O purpose.
     * @return The String representation of this event formatted for file I/O.
     */
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status,
				getDateString(startDate), getDateString(endDate));
    }
}
