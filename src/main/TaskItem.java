package main;

/**
 * Defines each task item in the To-Do list.
 * @author zixian
 */
public class TaskItem {
    enum PRIORITY_TYPE {VERY_URGENT, URGENT, NORMAL};
    enum STATUS_TYPE {PENDING, COMPLETED};
    
    private String description;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private String category = "---";
    private PRIORITY_TYPE priority = PRIORITY_TYPE.NORMAL;
    private STATUS_TYPE status = STATUS_TYPE.PENDING;
    
    /**
     * Creates a new TaskItem with the given description.
     */
    public TaskItem(String description){
	this.description = description;
    }
    
    public String getDescription(){
	return description;
    }
    
    /**
     * Returns the start date in the format used in
     * the text file.
     */
    public String getStartDate(){
	return startDate.toString();
    }
    
    /**
     * Returns the end date in the format used in
     * the text file.
     */
    public String getEndDate(){
	return endDate.toString();
    }
    
    public String getCategory(){
	return category;
    }
    
    /**
     * Returns the priority value in the form that is
     * saved/read to/from the file.
     */
    public String getPriority(){
	switch(priority){
	    case VERY_URGENT: return "high";
	    case URGENT: return "mid";
	    case NORMAL: return "low";
	    default: return "";
	}
    }
    
    /**
     * Returns the status in the form that is saved/read
     * to/from the file.
     */
    public String getStatus(){
	switch(status){
	    case PENDING: return "pending";
	    case COMPLETED: return "completed";  
	    default: return "";
	}
    }
    
    public String toString(){
	String output = getPriority()+"\t";
	output+=getDescription()+"\t";
	output+=getStartDate()+"\t";
	output+=getEndDate()+"\t";
	output+=getCategory()+"\t";
	return output;
    }

    /**
     * Sets the description for this to the new
     * description.
     */
    public void setDescription(String description){
	if(!description.isEmpty())
	    this.description = description;
    }
    
    public void setStartDate(int date, int month, int year){
	startDate.setDate(date, month, year);
    }
    
    public void setStartDate(int date, int month, int year,
	    		     int hour, int minute){
	startDate.setDate(date, month, year, hour, minute);
    }
    
    public void setEndDate(int date, int month, int year){
	endDate.setDate(date, month, year);
    }
    
    public void setEndDate(int date, int month, int year,
	    		     int hour, int minute){
	endDate.setDate(date, month, year, hour, minute);
    }

    public void setCategory(String category){
	   this.category = category;
    }
    
    /**
     * Sets the priority level of this task to the given
     * priority level, defaulting to 'low' if invalid
     * string is used as parameter.
     */
    public void setPriorityType(String priority){
	if(priority.equals("high")){
	    this.priority = PRIORITY_TYPE.VERY_URGENT;
	}else if(priority.equals("mid")){
	    this.priority = PRIORITY_TYPE.URGENT;
	}else{
	    this.priority = PRIORITY_TYPE.NORMAL;
	}
    }
    
    /**
     * Marks this task as completed.
     */
    public void completeTask(){
	status = STATUS_TYPE.COMPLETED;
    }
}
