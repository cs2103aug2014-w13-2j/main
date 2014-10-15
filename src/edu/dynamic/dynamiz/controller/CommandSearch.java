package edu.dynamic.dynamiz.controller;

import java.util.List;

import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the command to search for item with the given keyword in their description, 
 * filtered by the specified options, in the given storage.
 * 
 * Constructor
 * CommandSearch(String keyword, Options options)	//Creates an instance of this command object.
 * 
 * Public Methods
 * Options extractOptions(Options options)	//Extracts the options in this list that are applicable to this command.
 * void execute()	//Executes this command.
 * ToDoItem[] getAffectItems()	//Gets the list of items with the keyword in their description.
 * String getCommandName()	//Gets the string representation of this command's type.
 * 
 * @author zixian
 */
public class CommandSearch extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "search";
    
    //ExtractOptions constant
    private static final int INDEX_FIRSTOPTIONOBJECT = 0;
    
    //Error message
    private static final String MSG_EMPTYSEARCHSTRING = "Empty search string";
    
    //Main data members
    private String searchKey;
    private Options options;
    private ToDoItem[] searchList = null;
    
    /**
     * Creates an instance of this search command.
     * */
    public CommandSearch(String searchKey, Options options){
	assert searchKey!=null && options!=null;
	
	if(searchKey.isEmpty()){
	    throw new IllegalArgumentException(MSG_EMPTYSEARCHSTRING);
	}
	
	this.searchKey = searchKey.trim();
	this.options = extractOptions(options);
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
     * Executes this command.
     */
    public void execute() {
	searchList = storage.searchByKeyword(searchKey);
    }

    @Override
    /**
     * Gets the string representation of this command's type.
     * @return The string representation of this command's type.
     */
    public String getCommandName() {
	return COMMAND_TYPE;
    }
    
    @Override
    /**
     * Gets the list of items obtained by this command.
     * Must be called only after calling the execute() method.
     * @return An array of ToDoItem objects resulting from this command's execution or
     * 		null if the list is empty.
     */
    public ToDoItem[] getAffectedItems() {
	return searchList;
    }

}
