package edu.dynamic.dynamiz.controller;

import java.util.List;
import java.util.StringTokenizer;

import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.DateTime;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the Command object that updates a ToDoItem with the given id using the information
 * specified in the Options.
 * Currently, execute() method's implementation is incomplete, so this class actually does not mutate the
 * target object.
 * 
 * Constructor
 * CommandUpdate(String param, Options options)	//Creates a new instance of this Command
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * void undo()		//Undoes this command's execute method.
 * void redo()		//Redo this command.
 * String getCommandName()	//Gets the string representation of this command's type.
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItem objects that are affected by this command.
 * Options extractOptions(Options options)	//Gets the list of options that are applicable to this command.
 * 
 * @author zixian*/
public class CommandUpdate extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "update";
    
    //Error message
    private static final String MSG_EMPTYID = "Empty id string";
    
    //param-related constants
    private static final String PARAM_DELIM = " ";
    
    //ExtractOptions constant
    private static final int INDEX_FIRSTOPTIONOBJECT = 0;
    
    //List of options keywords
    private static final String KEYWORD_PRIORITY = "priority";
    private static final String KEYWORD_START = "from";
    private static final String KEYWORD_END = "to";
    
    //OptList-related constants
    private static final int OPTLISTINDEX_DATE = 0;
    private static final int OPTLISTINDEX_TIME = 1;
    private static final int OPTLIST_MINSIZE = 1;
    
    //UpdatedItem indices
    private static final int UPDATEDINDEX_OLD = 0;
    private static final int UPDATEDINDEX_NEW = 1;
    
    //Error messages
    private static final String MSG_INVALIDENDDATE = "Invalid end date/deadline value";
    private static final String MSG_INVALIDSTARTDATE = "Invalid start date value";
    private static final String MSG_INVALIDDATES = "Invalid start/end date(s)";
    
    //Main data members
    private String id, description = null;
    private ToDoItem[] updatedItem;
    private Options options;
    private int priority = OptionType.PRIORITY_NONE;
    private String start = null, end = null;
    private Date startDate;
    private Date endDate;
    
	public CommandUpdate(String id, String newDescription, int newPriority, Date newStartDate, Date newEndDate) {
		this.id = id;
		this.description = newDescription;
		this.priority = newPriority;
		this.startDate = newStartDate;
		this.endDate = newEndDate;
	}
	
    /**
     * 
     * Refactored into CommandLine
     * 
     * 
     * Creates a new instance of this Command object that updates the ToDoItem object with the given id
     * in the given Storage object with the new information specified in the Options list.
     * @param param The id (and new description) of the ToDoItem object to update.
     * @param options The list of options to apply to the target ToDoItem.
     * @param storage The Storage object in which the target object is contained.
     * @throws IllegalArgumentException if id is an empty string.
     */
    public CommandUpdate(String param, Options options){
	assert param!=null && options!=null;
	
	if(param.isEmpty()){
	    throw new IllegalArgumentException(MSG_EMPTYID);
	}
	
	this.id = param.trim();
	
	//Checks param for any new description specified
	StringTokenizer strtok = new StringTokenizer(this.id);
	assert strtok.hasMoreTokens();
	this.id = strtok.nextToken();
	if(strtok.hasMoreTokens()){
	    this.description = strtok.nextToken();
	    while(strtok.hasMoreTokens()){
		description+=PARAM_DELIM+strtok.nextToken();
	    }
	}
	
	//Checks for any new priority value
	if(this.options.hasOption(KEYWORD_PRIORITY)){
	    List<String> priorityList = this.options.getOptions(KEYWORD_PRIORITY).get(0).getValues();
	    if(!priorityList.isEmpty()){
		priority = Integer.parseInt(priorityList.get(0));
	    }
	}
	
	//Checks if the dates are Date or DateTime type, if applicable.
	if(this.options.hasOption(KEYWORD_START)){
	    List<String> startOptList = this.options.getOptions(KEYWORD_START).get(0).getValues();
	    if(startOptList.size()>OPTLIST_MINSIZE){
		start = startOptList.get(OPTLISTINDEX_DATE)+" "+startOptList.get(OPTLISTINDEX_TIME);
		if(!start.matches(DateTime.REGEX_DATETIME)){
		    start = startOptList.get(OPTLISTINDEX_DATE);
		}
	    } else if(!startOptList.isEmpty()){
		start = startOptList.get(OPTLISTINDEX_DATE);
	    }
	}
	if(this.options.hasOption(KEYWORD_END)){
	    List<String> endOptList = this.options.getOptions(KEYWORD_END).get(0).getValues();
	    if(endOptList.size()>OPTLIST_MINSIZE){
		end = endOptList.get(OPTLISTINDEX_DATE)+" "+endOptList.get(OPTLISTINDEX_TIME);
		if(!end.matches(DateTime.REGEX_DATETIME)){
		    end = endOptList.get(OPTLISTINDEX_DATE);
		}
	    } else if(!endOptList.isEmpty()){
		end = endOptList.get(OPTLISTINDEX_DATE);
	    }
	}
    }
    
    @Override
    /**
     * Executes this command.
     * @throws IllegalArgumentException if start and/or end represent invalid dates.
     */
    public void execute() {
//	if(start==null && end==null){
//	    updatedItem = storage.updateItem(id, description, priority, null, null);
//	} else if(start==null && end!=null){
//	    try{
//		Date endDate = makeDate(end);
//		updatedItem = storage.updateItem(id, description, priority, null, endDate);
//	    } catch(IllegalArgumentException e){
//		throw new IllegalArgumentException(MSG_INVALIDENDDATE);
//	    }
//	} else if(start!=null && end==null){
//	    try{
//		Date startDate = makeDate(start);
//		updatedItem = storage.updateItem(id, description, priority, startDate, null);
//	    } catch(IllegalArgumentException e){
//		throw new IllegalArgumentException(MSG_INVALIDSTARTDATE);
//	    }
//	} else{
//	    try{
//		Date startDate = makeDate(start);
//		Date endDate = makeDate(end);
//		updatedItem = storage.updateItem(id, description, priority, startDate, endDate);
//	    } catch(IllegalArgumentException e){
//		throw new IllegalArgumentException(MSG_INVALIDDATES);
//	    }
//	}
    	updatedItem = storage.updateItem(id, description, priority, startDate, endDate);
    }
    
    @Override
    /**
     * Undoes this command's execute method.
     * Can only be called after calling this command's execute method.
     */
    public void undo() {
	assert updatedItem!=null;
	
	storage.removeItem(updatedItem[UPDATEDINDEX_NEW].getId());
	storage.addItem(updatedItem[UPDATEDINDEX_OLD]);
    }
    
    @Override
    /**
     * Re-executes this command.
     * Must only be called after calling this command's undo() method.
     */
    public void redo(){
	storage.removeItem(updatedItem[UPDATEDINDEX_OLD].getId());
	storage.addItem(updatedItem[UPDATEDINDEX_NEW]);
    }
    
    @Override
    public String getCommandName() {
	return COMMAND_TYPE;
    }

    @Override
    /**
     * Gets the list of ToDoItem objects that are updated by this command.
     * Can only be called after calling this command's execute method.
     * @return A list of ToDoItem object that is updated by this command.
     * 		The element at index 0 is the state of the target object before the update.
     * 		The element at index 1 is the state of the target object after the update.
     */
    public ToDoItem[] getAffectedItems() {
	assert updatedItem!=null;
	
	return updatedItem;
    }

    /**
     * Creates a Date object from the given date string.
     * @param dateString The string representation of the Date instance to create.
     * @return A Date instance such that toString().equals(dateString) returns true.
     * @throws IllegalArgumentException if dateString does not represent a valid Date,
     * 		DateTime, and day of week.
     */
    private Date makeDate(String dateString){
	if(dateString.matches(Date.REGEX_DATE)){
	    return Date.makeDate(dateString);
	} else if(dateString.matches(DateTime.REGEX_DATETIME)){
	    return DateTime.makeDateTime(dateString);
	} else{
	    return storage.getDate(dateString);
	}
    }
}
