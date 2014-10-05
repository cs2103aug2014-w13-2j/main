package edu.dynamic.dynamiz.controller;

import java.util.List;

import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.storage.Storage;
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
 * CommandUpdate(String id, Options options, Storage storage)	//Creates a new instance of this Command
 * 
 * Public Methods
 * void execute()	//Executes this command.
 * void undo()		//Undoes this command's execute method.
 * String getCommandName()	//Gets the string representation of this command's type.
 * ToDoItem[] getAffectedItems()	//Gets the list of ToDoItem objects that are affected by this command.
 * Options extractOptions(Options options)	//Gets the list of options that are applicable to this command.
 * 
 * @author zixian*/
public class CommandUpdate extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "update";
    
    private static final String MSG_EMPTYID = "Empty id string";
    
  //List of options keywords
    private static final String KEYWORD_PRIORITY = "priority";
    private static final String KEYWORD_START = "from";
    private static final String KEYWORD_END = "to";
    
  //Error messages
    private static final String MSG_EMPTYDESCRIPTION = "Empty description string";
    private static final String MSG_INVALIDENDDATE = "Invalid end date/deadline value";
    private static final String MSG_INVALIDSTARTDATE = "Invalid start date value";
    private static final String MSG_INVALIDDATES = "Invalid start/end date(s)";
    
    //Main data members
    private String id;
    private ToDoItem[] updatedItem;
    private Options options;
    private int priority = -1;
    private String start = null, end = null;
    
    /**
     * Creates a new instance of this Command object that updates the ToDoItem object with the given id
     * in the given Storage object with the new information specified in the Options list.
     * @param id The id of the ToDoItem object to update.
     * @param options The list of options to apply to the target ToDoItem.
     * @param storage The Storage object in which the target object is contained.
     * @throws IllegalArgumentException if id is an empty string.
     */
    public CommandUpdate(String id, Options options, Storage storage){
	assert id!=null && options!=null && storage!=null;
	
	if(id.isEmpty()){
	    throw new IllegalArgumentException(MSG_EMPTYID);
	}
	
	this.id = id.trim();
	this.options = extractOptions(options);
	this.storage = storage;
	
	if(this.options.hasOption(KEYWORD_PRIORITY)){
	    priority = Integer.parseInt(this.options.getOptions(KEYWORD_PRIORITY).get(0).getValues().get(0));
	}
	if(this.options.hasOption(KEYWORD_START)){
	    List<String> startOptList = this.options.getOptions(KEYWORD_START).get(0).getValues();
	    if(startOptList.size()>1){
		start = startOptList.get(0)+" "+startOptList.get(1);
		if(!start.matches(DateTime.REGEX_DATETIME)){
		    start = startOptList.get(0);
		}
	    } else{
		assert !startOptList.isEmpty();
		start = startOptList.get(0);
	    }
	}
	if(this.options.hasOption(KEYWORD_END)){
	    List<String> endOptList = this.options.getOptions(KEYWORD_END).get(0).getValues();
	    if(endOptList.size()>1){
		end = endOptList.get(0)+" "+endOptList.get(1);
		if(!end.matches(DateTime.REGEX_DATETIME)){
		    end = endOptList.get(0);
		}
	    } else{
		assert !endOptList.isEmpty();
		end = endOptList.get(0);
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
	
	for (OptionType optType: CommandType.UPDATE.getApplicableOptions()) {
	    //Selects the 1st Option when there are more than 1 Option of the same OptionType.
	    list = options.getOptions(optType);
	    if(list!=null){
		opts.add(list.get(0));
	    }
	}
	return opts;
    }

    @Override
    /**
     * Executes this command.
     * @throws IllegalArgumentException if start and/or end represent invalid dates.
     */
    public void execute() {
	if(start==null && end==null){
	    updatedItem = storage.updateItem(id, priority, null, null);
	} else if(start==null && end!=null){
	    try{
		Date endDate = makeDate(end);
		updatedItem = storage.updateItem(id, priority, null, endDate);
	    } catch(IllegalArgumentException e){
		throw new IllegalArgumentException(MSG_INVALIDENDDATE);
	    }
	} else if(start!=null && end==null){
	    try{
		Date startDate = makeDate(start);
		updatedItem = storage.updateItem(id, priority, startDate, null);
	    } catch(IllegalArgumentException e){
		throw new IllegalArgumentException(MSG_INVALIDSTARTDATE);
	    }
	} else{
	    try{
		Date startDate = makeDate(start);
		Date endDate = makeDate(end);
		updatedItem = storage.updateItem(id, priority, startDate, endDate);
	    } catch(IllegalArgumentException e){
		throw new IllegalArgumentException(MSG_INVALIDDATES);
	    }
	}
    }
    
    @Override
    /**
     * Undoes this command's execute method.
     * Can only be called after calling this command's execute method.
     */
    public void undo() {
	assert updatedItem!=null;
	
	storage.removeItem(updatedItem[1].getId());
	storage.addItem(updatedItem[0]);
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
