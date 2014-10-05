package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;
import edu.dynamic.dynamiz.storage.Storage;
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
    
    //Main data members
    private String id;
    private ToDoItem[] updatedItem;
    private Options options;
    
    /**
     * Creates a new instance of this Command object that updates the ToDoItem object with the given id
     * in the given Storage object with the new information specified in the Options list.
     * @param id The id of the ToDoItem object to update.
     * @param options The list of options to apply to the target ToDoItem.
     * @param storage The Storage object in which the target object is contained.
     */
    public CommandUpdate(String id, Options options, Storage storage){
	assert id!=null && !id.isEmpty() && options!=null && storage!=null;
	
	this.id = id.trim();
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
	
	for (OptionType optType: CommandType.UPDATE.getApplicableOptions()) {
	    Option opt = options.getOptions(optType).get(0); // Assume that there is one Option per OptionType
	    opts.add(opt);
	}
	return opts;
    }

    @Override
    /**
     * Executes this command.
     * Note: Exact implementation to be updated.
     */
    public void execute() {
	updatedItem = storage.updateItem(id);
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

}
