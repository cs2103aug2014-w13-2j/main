package edu.dynamic.dynamiz.controller;

import java.util.List;

import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.DateTime;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the Command object that adds a new ToDoItem object with the given information into
 * the given storage object.
 * Currently, the execute() method's implementation is incomplete, so this class is only able to 
 * add ToDoItem with only description and default priority level.
 * 
 * Constructor
 * CommandAdd(String description, Options options, Storage storage)	//Creates a CommandAdd instance
 * 							//with the given description, the Options list,
 * 							//and the storage object to add into.
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * void undo()		//Undoes this command's execute method.
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItem instances added by this command.
 * String getCommandName()	//Gets the string representation of this command's type.
 * Options extractOptions(Options options)	//Gets the Options list that is applicable to this command
 * 						//from the given Options list.
 * 
 * Private Methods
 * Date makeDate(String dateString)	//Creates a Date instance from the given date string.
 * 
 * @author zixian
 * */
public class CommandAdd extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "add";
    
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
    
    //Error messages
    private static final String MSG_EMPTYDESCRIPTION = "Empty description string";
    private static final String MSG_INVALIDENDDATE = "Invalid end date/deadline value";
    private static final String MSG_INVALIDSTARTDATE = "Invalid start date value";
    private static final String MSG_INVALIDDATES = "Invalid start/end date(s)";
    
    //Main data members
    private Options options;	//The list of options for this command.
    private ToDoItem addedItem;	//The item being added by this command.
    
    //Data members for ToDoItem
    private String description;	//The description of the item to be added.
    private int priority = 0;	//Priority level.
    private String start = null, end = null;	//Start and end/deadline dates.
    
    /**
     * Creates a new Command object that adds a new entry into the given storage.
     * @param description The description of the entry to be added.
     * @param options The list of options specifying extra information associated with the entry to be added.
     * @param storage The storage object to add the new entry into.
     * @throws IllegalArgumentException if description is an empty string.
     * */
    public CommandAdd(String description, Options options, Storage storage) {
	assert options!=null && description!=null && storage!=null;
	
	if(description.isEmpty()){
	    throw new IllegalArgumentException(MSG_EMPTYDESCRIPTION);
	}
	
	this.description = description.trim();
	this.options = extractOptions(options);
	this.storage = storage;
	
	//All conflicting option objects/values are resolved by taking the 1st object/value.
	//Checks for any priority value specified in options.
	if(this.options.hasOption(KEYWORD_PRIORITY)){
	    List<String> priorityList = this.options.getOptions(KEYWORD_PRIORITY).get(0).getValues();
	    if(!priorityList.isEmpty()){
		priority = Integer.parseInt(priorityList.get(0));
	    }
	}
	
	//Checks for any start date value specified in options.
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
	
	//Checks for any end date value specified in options.
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
    
    /**
     * Gets the list of applicable options for this command from the given options list.
     * @param options The list of options to extract from.
     * @return An Options object containing the list of applicable options for this command.
     */
    public Options extractOptions(Options options) {
	Options opts = new Options();
	List<Option> list;
	
	for (OptionType optType: CommandType.ADD.getApplicableOptions()) {
	    //Best effort attempt to resolve conflicting values for same option type.
	    list = options.getOptions(optType);
	    if(list!=null){
		opts.add(list.get(INDEX_FIRSTOPTIONOBJECT));
	    }
	}
	return opts;
    }

    @Override
    /**
     * Executes this command. Also used for redo operation.
     * @throws IllegalArgumentException if any of the dates provided by the user is invalid.
     */
    public void execute() {
	if(start==null && end==null){
	    addedItem = new ToDoItem(description, priority);
	} else if(start==null && end!=null){
	    try{
		Date endDate = makeDate(end);
		addedItem = new TaskItem(description, priority, endDate);
	    } catch(IllegalArgumentException e){
		throw new IllegalArgumentException(MSG_INVALIDENDDATE);
	    } 
	} else if(start!=null && end==null){
	    try{
		Date startDate = makeDate(start);
		addedItem = new EventItem(description, priority, startDate);
	    } catch(IllegalArgumentException e){
		throw new IllegalArgumentException(MSG_INVALIDSTARTDATE);
	    }
	} else{
	    try{
		Date startDate = makeDate(start);
		Date endDate = makeDate(end);
		addedItem = new EventItem(description, priority, startDate, endDate);
	    } catch(IllegalArgumentException e){
		throw new IllegalArgumentException(MSG_INVALIDDATES);
	    }
	}
	storage.addItem(addedItem);
    }
    
    @Override
    /**
     * Undoes this command's execute method.
     * Can only be called after calling this command's execute method.
     */
    public void undo() {
	assert addedItem!=null;
	
	storage.removeItem(addedItem.getId());
    }
    
    @Override
    /**
     * Gets the String representation of this command's type.
     */
    public String getCommandName() {
	return COMMAND_TYPE;
    }
    
    /**
     * Gets the list of items affected by the execution of this command.
     * Can only be called after calling this command's execute method.
     * @return A list of ToDoItem added by this command.
     */
    public ToDoItem[] getAffectedItems(){
	//Checks the assertion that addedItem is not null
	assert addedItem!=null;
	
	ToDoItem[] list = new ToDoItem[1];
	list[0] = addedItem;
	return list;
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
