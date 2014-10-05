package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.storage.Storage;
import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines the Command object that adds a new ToDoItem object with the given information into
 * the given storage object.
 * Currently, the execute() method's implementation is incomplete, so this class is only able to 
 * add ToDoItem with only description and default priority level.
 * 
 * Constructor
 * CommandAdd(Options options, String description, Storage storage)	//Creates a CommandAdd instance
 * 							//with the given Options list, the description,
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
 * @author zixian
 * */
public class CommandAdd extends Command {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "add";
    
    //Main data members
    private String param;	//The description of the item to be added.
    private Options options;	//The list of options for this command.
    private ToDoItem addedItem;	//The item being added by this command.
    
    /**
     * Creates a new Command object that adds a new entry into the given storage.
     * @param options The list of options specifying extra information associated with the entry to be added.
     * @param description The description of the entry to be added.
     * @param storage The storage object to add the new entry into.
     * @throws IllegalArgumentException if description is an empty string or if any of the parameters is null.
     * */
    public CommandAdd(Options options, String description, Storage storage) {
	assert options!=null && description!=null && !description.isEmpty() && storage!=null;
	
	this.param = param.trim();
	//this.options = extractOptions(options);
	this.options = options;
	this.storage = storage;
    }
    
    /**
     * Gets the list of applicable options for this command from the given options list.
     * @param options The list of options to extract from.
     * @return An Options object containing the list of applicable options for this command.
     */
    public Options extractOptions(Options options) {
	Options opts = new Options();
	
	for (OptionType optType: CommandType.ADD.getApplicableOptions()) {
	    Option opt = options.getOptions(optType).get(0); // Assume that there is one Option per OptionType
	    opts.add(opt);
	}
	return opts;
    }

    @Override
    /**
     * Executes this command. Also used for redo operation.
     * Note: Currently only supports adding of items with only description.
     * 		Implementation to be updated in the near future.
     */
    public void execute() {
	addedItem = new ToDoItem(param);
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
}
