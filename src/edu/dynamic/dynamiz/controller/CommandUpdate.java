package edu.dynamic.dynamiz.controller;


import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.structure.MyDate;
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
 */
//@author A0110781N
public class CommandUpdate extends Command implements Undoable {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "update";
    
    //UpdatedItem indices
    private static final int UPDATEDINDEX_OLD = 0;
    private static final int UPDATEDINDEX_NEW = 1;
       
    //Main data members
    private String description = null;
    private ToDoItem[] updatedItem;
    private int id;
 
    private int priority = OptionType.PRIORITY_NONE;

    private MyDate startDate;
    private MyDate endDate;
    
     /**
     * Creates a new instance of this Command object that updates the ToDoItem object with the given id
     * with the given information.
     * @param id The id (and new description) of the ToDoItem object to update.
     * @param description The new description for the item, or null if it is not to be changed.
     * @param priority The new priority of the item, or -1 if it is not to be changed.
     * @param start The new start date of this item, or null if it is not to be changed.
     * @param end The new end date of this item, or null if it is not to be changed.
     */
	public CommandUpdate(int id, String newDescription, int newPriority, MyDate newStartDate, MyDate newEndDate) {
		this.id = id;
		this.description = newDescription;
		this.priority = newPriority;
		this.startDate = newStartDate;
		this.endDate = newEndDate;
	}
    
    @Override
    /**
     * Executes this command.
     * @throws IllegalArgumentException if start and/or end represent invalid dates.
     */
    public void execute() throws IllegalArgumentException {
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
	ToDoItem[] temp = new ToDoItem[2];
	temp[UPDATEDINDEX_OLD] = updatedItem[UPDATEDINDEX_NEW];
	temp[UPDATEDINDEX_NEW] = updatedItem[UPDATEDINDEX_OLD];
	updatedItem = temp;
    }
    
    @Override
    /**
     * Re-executes this command.
     * Must only be called after calling this command's undo() method.
     */
    public void redo() throws IllegalArgumentException {
	storage.removeItem(updatedItem[UPDATEDINDEX_OLD].getId());
	storage.addItem(updatedItem[UPDATEDINDEX_NEW]);
    }
    
    @Override
    /**
     * Gets the String representation of this command's type.
     * @return The String representation of this command's type.
     */
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
