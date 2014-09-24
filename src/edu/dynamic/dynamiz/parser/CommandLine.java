package edu.dynamic.dynamiz.parser;

import edu.dynamic.dynamiz.displayer.*;
import edu.dynamic.dynamiz.logic.*;
import edu.dynamic.dynamiz.parser.*;
import edu.dynamic.dynamiz.storage.*;
import edu.dynamic.dynamiz.structure.*;


/**
 * This is a class Object which stores the information of the parsed 
 * command line given by the user.
 * 
 * Represents a list of arguments parsed against an option argument.
 * 
 * Also represents a list of commands parsed (?! if it is necessary)
 * 
 * This allows querying of a boolean hasOption(String opt), 
 *  and retrieving value of the option getOptionValue(String opt)
 *  
 * @author nhan
 *
 */
public class CommandLine implements CommandLineInterface{
	private String commandType;
	private Options options;
	
}
