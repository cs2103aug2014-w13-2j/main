package edu.dynamic.dynamiz.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
 * 
 * @author zixian
 */
public class CommandHelp extends Command {
    //String representation of this command's type
    private static final String COMMAND_TYPE = "help";
    
    //Error messages
    private static final String MSG_FILENOTFOUND = "Requested help file missing.";
    private static final String MSG_IOEXCEPTION = "An IO Exception occurred.";
    
    //Defines help content line delimiter
    private static final char LINE_TERMINATOR = '\n';
    
    //Defines the help page files
    private static final String HELPFILE_MAIN = "help/help.txt";
    private static final String HELPFILE_ADD = "help/help_add.txt";
    private static final String HELPFILE_DELETE = "help/help_delete.txt";
    private static final String HELPFILE_DO = "help/help_do.txt";
    private static final String HELPFILE_LIST = "help/help_list.txt";
    private static final String HELPFILE_REDO = "help/help_redo.txt";
    private static final String HELPFILE_SEARCH = "help/help_search.txt";
    private static final String HELPFILE_UNDO = "help/help_undo.txt";
    private static final String HELPFILE_UPDATE = "help/help_update.txt";
    
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
	File helpFile;
	if(command==null){
	    helpFile = new File(HELPFILE_MAIN);
	} else{
	    switch(command){
		case ADD: helpFile = new File(HELPFILE_ADD);
			break;
		case DELETE: helpFile = new File(HELPFILE_DELETE);
			break;
		case DO: helpFile = new File(HELPFILE_DO);
			break;
		case LIST: helpFile = new File(HELPFILE_LIST);
			break;
		case REDO: helpFile = new File(HELPFILE_REDO);
			break;
		case SEARCH: helpFile = new File(HELPFILE_SEARCH);
			break;
		case UNDO: helpFile = new File(HELPFILE_UNDO);
			break;
		case UPDATE: helpFile = new File(HELPFILE_UPDATE);
			break;
		default: helpFile = new File(HELPFILE_MAIN);
	    }
	}
	
	try{
	    BufferedReader reader = new BufferedReader(new FileReader(helpFile));
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
