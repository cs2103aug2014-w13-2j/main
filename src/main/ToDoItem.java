package main;

/**
 * Defines each item in the To-Do list in general.
 * Ordering for each ToDoItem is done using lexicographical ordering of its id.
 * @author zixian
 */
public class ToDoItem implements Comparable<ToDoItem>{

    private static final int DEFAULT_PRIORITY = 0;
    protected static final String DEFAULT_STATUS = "pending";
    
    public static final String STATUS_INPROGRESS = "in progress";
    public static final String STATUS_COMPLETED = "completed";
    
    //Number of ToDoItems with the same alphabetical prefix
    private static final int MAX_IDNUM = 99;
    
    //Print formats
    private static final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
	    						"Status: %4$s";
    private static final String FORMAT_FILESTRING = "%1$s; %2$d; %3$s; --/--/---- --:--; --/--/---- --:--";
    private static final String FORMAT_PRINTSTRING = "%1$s %2$s %3$d %4$s --/--/---- --:-- --/--/---- --:--";
    
    //ID is of the form idLetter followed by idNUm.
    //Highest idNum possible is 99, after which idLetter will advance to the next alphabet.
    private static char idLetter = 'A';
    private static int idNum = 1;
    
    //Main data members
    protected String id, description, status;
    protected int priority;
    
    //Constructors
    public ToDoItem(String description){
	this(description, DEFAULT_PRIORITY);
    }
    
    public ToDoItem(String description, int priority){
	setId(getNextId());
	setDescription(description);
	setPriority(priority);
	setStatus(DEFAULT_STATUS);
    }
    
    @Override
    public int compareTo(ToDoItem item){
	return id.compareTo(item.getId());
    }
    
    @Override
    public boolean equals(Object obj){
	if(obj instanceof ToDoItem){
	    ToDoItem temp = (ToDoItem)obj;
	    return id.equals(temp.getId());
	}
	return false;
    }
    
    public String getDescription(){
	return description;
    }
    
    public String getId(){
	return id;
    }
    
    //Gets the next id to assign to a new ToDoItem.
    private String getNextId(){
	StringBuilder newId = new StringBuilder(Character.toString(idLetter));
	newId = newId.append(Integer.toString(idNum));
	if(idNum==MAX_IDNUM){
	    idLetter++;
	}
	idNum = (idNum+1)%MAX_IDNUM;
	return newId.toString();
    }
    
    public int getPriority(){
	return priority;
    }
    
    public String getStatus(){
	return status;
    }
    
    public void setDescription(String description){
	this.description = description;
    }
    
    private void setId(String id){
	this.id = id;
    }
    
    public void setPriority(int priority) throws IllegalArgumentException{
	if(priority<DEFAULT_PRIORITY){
	    throw new IllegalArgumentException();
	}
	this.priority = priority;
    }
    
    public void setStatus(String status) throws IllegalArgumentException{
	if(!status.equals(DEFAULT_STATUS) && !status.equals(STATUS_INPROGRESS) && !status.equals(STATUS_COMPLETED)){
	    throw new IllegalArgumentException();
	}
	this.status = status;
    }
    
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
    public String toString(){
	return String.format(FORMAT_PRINTSTRING, id, description, priority, status);
    }
}
