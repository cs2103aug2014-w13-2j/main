package edu.dynamic.dynamiz.structure;

/**
 * Defines each item in the To-Do list in general.
 * Natural ordering for each ToDoItem is done using lexicographical ordering of its id.
 * 
 * Constructors
 * ToDoItem(String description)	//Creates a new instance of this item.
 * ToDoItem(String description, int priority)	//Creates a new instance of this item with the given priority level
 * protected ToDoItem(String description, int priority, String status)
 * ToDoItem(ToDoItem item)	//Creates a new copy of the given item
 * 
 * Public Methods
 * int compareTo(ToDoItem item)	//Compares this with the given item.
 * boolean equals(Object obj)	//Checks if this equals to the given object.
 * String getDescription()	//Gets the description of this item.
 * String getFeedbackString()	//Gets the feedback string format of this item.
 * int getId()	//Gets the ID of this item.
 * int getPriority()	//Gets the priority level of this item.
 * String getStatus()	//Gets the status of this item.
 * void setDescription(String description)	//Changes the description of this item.
 * void setPriority(int priority)	//Changes the priority of this item.
 * void setStatus(String status)	//Changes the status of this item.
 * String toFileString()	//Gets string representation of this item used in files.
 * String toString()	//Gets the string representation of this item.
 * 
 * @author A0110781N
 */
public class ToDoItem implements Comparable<ToDoItem>{

    //ToDoItem defaults
    private static final int DEFAULT_PRIORITY = 0;
    protected static final String DEFAULT_STATUS = "Pending";
    
    //Allowed status values
    public static final String STATUS_PENDING = DEFAULT_STATUS;
    public static final String STATUS_COMPLETED = "Completed";
    
    //Allowed priority levels
    public static final int PRIORITY_URGENT = 8;
    public static final int PRIORITY_HIGH = 4;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_NONE = 0;
    
    //Print formats
    private static final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
	    						"Status: %4$s";
    private static final String FORMAT_FILESTRING = "%1$s; %2$d; %3$s; --/--/---- --:--; --/--/---- --:--";
    private static final String FORMAT_PRINTSTRING = "%1$s %2$s %3$d %4$s --/--/---- --:-- --/--/---- --:--";
    
    //ID is of the form idLetter followed by idNUm.
    //Highest idNum possible is 99, after which idLetter will advance to the next alphabet.
    private static int curId = 1;
    
    //Main data members
    protected String description, status;
    protected int priority, id;
    
    //Constructors
    /**
     * Creates a new instance of this item.
     * @param description The description of this item.
     */
    public ToDoItem(String description){
	this(getNextId(), description, DEFAULT_PRIORITY, DEFAULT_STATUS);
    }
    
    /**
     * Creates a new instance of this item with the given priority level.
     * @param description The description of this object.
     * @param priority The specified priority level of this item.
     */
    public ToDoItem(String description, int priority){
	this(getNextId(), description, priority, DEFAULT_STATUS);
    }
    
    //The main constructor responsible for creating the item.
    protected ToDoItem(int id, String description, int priority, String status){
	setId(id);
	setDescription(description);
	setPriority(priority);
	setStatus(status);
    }
    
    //Copy constructor
    /**
     * Creates a new instance of the given item with the same data member values as the provided item.
     * @param item The item to copy.
     */
    public ToDoItem(ToDoItem item){
	this(item.getId(), item.getDescription(), item.getPriority(), item.getStatus());
    }
    
    @Override
    /**
     * Compares this item with the given item by its natural ordering.
     * @param The ToDoItem to compare with.
     * @return Positive number if this.id is lexicographically greater than item.id,
     * 		0 if this.id.equals(item.id) is true, and negative number if this.id is lexicographically
     * 		smaller than item.id.
     */
    public int compareTo(ToDoItem item){
	assert item!=null;
	if(status.equals(STATUS_PENDING) && item.getStatus().equals(STATUS_COMPLETED)){
	    return -1;
	} else if(status.equals(STATUS_COMPLETED) && item.getStatus().equals(STATUS_PENDING)){
	    return 1;
	} else if(!item.getClass().getName().equals(this.getClass().getName())){
	    return 1;
	} else if(priority!=item.getPriority()){
	    return item.getPriority()-priority;
	}
	return id-item.getId();
    }
    
    @Override
    /**
     * Checks if this equals the given object.
     * @param obj The object to compare with.
     * @return A boolean value given by (obj instanceof ToDoItem)? this.id.equals(((ToDoItem)obj).id): false;
     */
    public boolean equals(Object obj){
	assert obj!=null;
	if(obj instanceof ToDoItem){
	    ToDoItem temp = (ToDoItem)obj;
	    return id==temp.getId();
	}
	return false;
    }
    
    /**
     * Checks if the priority level is valid.
     * @param priority The priority level to check.
     * @return A boolean value such that (priority>=0)? true: false
     */
    public static boolean isValidPriority(int priority){
	return priority>0;
    }
    
    /**
     * Gets the description of this item.
     * @return The description string of this item.
     */
    public String getDescription(){
	return description;
    }
    
    /**
     * Gets the ID of this item.
     * @return The ID string of this object.
     */
    public int getId(){
	return id;
    }
    
    //Gets the next id to assign to a new ToDoItem.
    private static int getNextId(){
	return curId++;
    }
    
    /**
     * Gets the priority of this item.
     * @return The priority level of this item.
     */
    public int getPriority(){
	return priority;
    }
    
    /**
     * Gets the status of this item.
     * @return The status string of this item.
     */
    public String getStatus(){
	return status;
    }
    
    /**
     * Changes this item's description.
     * @param description The new description.
     */
    public void setDescription(String description){
	assert description!=null && !description.isEmpty();
	this.description = description;
    }
    
    //Changes the ID of this item.
    private void setId(int id){
	this.id = id;
    }
    
    /**
     * Changes the priority level of this item.
     * @param priority The new priority level of this item.
     * @throws IllegalArgumentException if ToDoItem.isValidPriority(priority) returns false.
     */
    public void setPriority(int priority) throws IllegalArgumentException{
	if(priority<DEFAULT_PRIORITY){
	    throw new IllegalArgumentException();
	}
	this.priority = priority;
    }
    
    /**
     * Changes the status of this item.
     * @param status The new status of this item. Acceptable values are defined as static constants in this class.
     * @throws IllegalArgumentException if the given status is not in the list of acceptable values.
     */
    public void setStatus(String status) throws IllegalArgumentException{
	assert status!=null && !status.isEmpty();
	if(!status.equals(DEFAULT_STATUS) && !status.equals(STATUS_COMPLETED)){
	    throw new IllegalArgumentException("Invalid status.");
	}
	this.status = status;
    }
    
    /**
     * Gets the feedback string format of this item.
     * @return The feedback string representation of this item.
     */
    public String getFeedbackString(){
	return String.format(FORMAT_FEEDBACKSTRING, id, description, priority, status);
    }
    
    /**
     * Returns a string representation of this item to be displayed for feedback and confirmation.
     * @return A formatted String representing this item.
     */
    public String toFileString(){
	return String.format(FORMAT_FILESTRING, description, priority, status);
    }
    
    @Override
    /**
     * Gets the string representation of this item.
     * @return The String represetnation of this item.
     */
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status);
    }
}
