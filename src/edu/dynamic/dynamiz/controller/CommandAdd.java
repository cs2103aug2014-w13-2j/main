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
 * void redo()		//Redo this command.
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
    private static final CommandType COMMAND_TYPE = CommandType.ADD;
    
    //Main data members
    private ToDoItem addedItem;	//The item being added by this command.
    
    public CommandAdd(ToDoItem item) {
		// TODO Auto-generated constructor stub
    	addedItem = item;
	}

    @Override
    /**
     * Executes this command. Also used for redo operation.
     * @throws IllegalArgumentException if any of the dates provided by the user is invalid.
     */
    public void execute() {
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
     * Re-execute this command.
     * Must only be called after calling this command's undo() method.
     */
    public void redo(){
	storage.addItem(addedItem);
    }
    
    @Override
    /**
     * Gets the String representation of this command's type.
     */
    public String getCommandName() {
	return COMMAND_TYPE.name();
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
    
    // Getters & Setters
	public ToDoItem getAddedItem() {
		return addedItem;
	}

	public void setAddedItem(ToDoItem addedItem) {
		this.addedItem = addedItem;
	}
}
