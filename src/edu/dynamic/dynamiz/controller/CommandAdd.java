package edu.dynamic.dynamiz.controller;

import edu.dynamic.dynamiz.structure.ToDoItem;

//@author A0110781N
/**
 * Defines the Command object that adds a new ToDoItem object with the given information into
 * the given storage object.
 * Currently, the execute() method's implementation is incomplete, so this class is only able to 
 * add ToDoItem with only description and default priority level.
 * 
 * Constructor
 * CommandAdd(String description, Options options)	//Creates a CommandAdd instance
 * 							//with the given description, the Options list
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
 * */
public class CommandAdd extends Command implements Undoable {
    //The string representation of this command's type.
    private static final String COMMAND_TYPE = "add";
    
    //Affected list-related constants.
    private static final int LISTSIZE = 1;
    private static final int ITEMINDEX = 0;
    
    //Main data members
    private ToDoItem addedItem;	//The item being added by this command.
    
    /**
     * Creates a new Command object that adds a new entry into the given storage.
     * @param description The description of the entry to be added.
     * @param options The list of options specifying extra information associated with the entry to be added.
     * @param storage The storage object to add the new entry into.
     * @throws IllegalArgumentException if description is an empty string.
     * */
    public CommandAdd(ToDoItem item) {
	assert item!=null;
	this.addedItem = item;
    }

    @Override
    /**
     * Executes this command. Also used for redo operation.
     */
    public void execute() {
	storage.addItem(addedItem);
    }
    
    @Override
    /**
     * Undoes this command's execute method.
     * Can only be called after calling this command's execute() method.
     */
    public void undo() {
	storage.removeItem(addedItem.getId());
    }
    
    @Override
    /**
     * Re-execute this command.
     * Must only be called after calling this command's undo() method.
     */
    public void redo(){
	execute();
    }
    
    @Override
    /**
     * Gets the String representation of this command's type.
     * @return The String representation of this command's type.
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
	assert addedItem!=null;
	
	ToDoItem[] list = new ToDoItem[LISTSIZE];
	list[ITEMINDEX] = addedItem;
	return list;
    }
}
