package edu.dynamic.dynamiz.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import edu.dynamic.dynamiz.structure.ToDoItem;

/**
 * Defines command to display help contents from help files.
 * 
 * Constructor
 * CommandHelp()	//Creates a command to get the main help page.
 * CommandHelp(CommandType command)	//Creates a command to get the specified command's help page.
 * 
 * Public Methods
 * String getCommandName()	//Gets the String representation of this command's type.
 * String getContent()	//Gets the contents of the help page.
 * void execute()	//Executes this command. 
 */
//@author A0110781N
public class CommandHelp extends Command {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "help";
    
    //Error messages
    private static final String MSG_FILENOTFOUND = "Requested help file missing.";
    private static final String MSG_IOEXCEPTION = "An IO Exception occurred.";
    
    //Defines help content line delimiter
    private static final char LINE_TERMINATOR = '\n';
   
    //Defines the help page files
    private static final String HELPFILE_MAIN = "help.txt";
    private static final String HELPFILE_ADD = "help_add.txt";
    private static final String HELPFILE_DELETE = "help_delete.txt";
    private static final String HELPFILE_DO = "help_mark.txt";
    private static final String HELPFILE_LIST = "help_list.txt";
    private static final String HELPFILE_REDO = "help_redo.txt";
    private static final String HELPFILE_SEARCH = "help_search.txt";
    private static final String HELPFILE_UNDO = "help_undo.txt";
    private static final String HELPFILE_UNMARK = "help_unmark.txt";
    private static final String HELPFILE_UPDATE = "help_update.txt";
    
    //Logger to log errors
    private static Logger logger = Logger.getLogger("edu.dynamic.dynamiz.controller");
    
    //Main data members
    private CommandType command = null;
    private String helpContent = MSG_FILENOTFOUND;
    
    /**
     * Creates a new instance of this command.
     */
    public CommandHelp(){
	
    }
    
    /**
     * Creates a new instance of this command.
     * @param command The command type to get help page on. Cannot be null.
     */
    public CommandHelp(CommandType command){
	assert command!=null;
	this.command = command;
    }
    
    @Override
    /**
     * Executes this command.
     */
    public void execute() {
	String helpFile;
	if(command==null){
	    helpFile = HELPFILE_MAIN;
	} else{
	    switch(command){
		case ADD: helpFile = HELPFILE_ADD;
			break;
		case DELETE: helpFile = HELPFILE_DELETE;
			break;
		case MARK: helpFile = HELPFILE_DO;
			break;
		case LIST: helpFile = HELPFILE_LIST;
			break;
		case REDO: helpFile = HELPFILE_REDO;
			break;
		case SEARCH: helpFile = HELPFILE_SEARCH;
			break;
		case UNDO: helpFile = HELPFILE_UNDO;
			break;
		case UPDATE: helpFile = HELPFILE_UPDATE;
			break;
		case UNMARK: helpFile = HELPFILE_UNMARK;
			break;
		default: helpFile = HELPFILE_MAIN;
	    }
	}
	
	try{
	    InputStream in = this.getClass().getResourceAsStream("/" + helpFile);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder str = new StringBuilder();
	    String input;
	    while((input = reader.readLine())!=null){
		str = str.append(input).append(LINE_TERMINATOR);
	    }
	    helpContent = str.toString();
	    reader.close();
	} catch(FileNotFoundException e){
	    logger.warning(MSG_FILENOTFOUND);
	} catch(IOException e){
	    logger.warning(MSG_IOEXCEPTION);
	}
	
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
     * Does nothing as it does not deal with Storage.
     */
    public ToDoItem[] getAffectedItems() {
	return null;
    }
    
    /**
     * Gets the contents of the help page being read from.
     * Must only be called after the execute() method.
     * @return The help content string.
     */
    public String getContent(){
	return helpContent;
    }
}
