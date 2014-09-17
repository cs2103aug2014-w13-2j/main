package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A single command-line option. It keeps information of an option
 * such as 
 * 	- short name
 *  - long name (if any)
 *  - number of arguments required (0: none, 1: one, -1: none or many, -2: at least 1)
 *  - self documenting description string
 *  
 * An Option requires at least a short name or a long name.
 * 
 * @author nhan
 *
 * Modelled after CLI library
 */

public class Option {
	/*=========================================================================
	 * Constants
	 * ========================================================================
	 */
	/** constant that specifies if the number of required arguments has not been specified */
	private static final int ARGS_UNINITIALISED = -99;
	
	/** constant that specifies if the required number of arguments is 0 */
	public static final int ARGS_NONE = 0;
	
	/** constant that specifies if the required number of arguments is 1 */
	public static final int ARGS_ONE = 1;
	
	/** constant that specifies if the required number of arguments is 2 */
	public static final int ARGS_TWO = 2;
	
	/** constant that specifies if the number of arguments is at least 1 */
	public static final int ARGS_UNLIMITED = -2;
	
	/** constant that specifies if the number of arguments is none or many */
	public static final int ARGS_OPTIONAL = -1;
	
	/** constant that specifies the default character for delimiter of argument values */
	public static final char DEFAULT_DELIMITER = ',';
	
	/*=========================================================================
	 * Class attributes
	 * ========================================================================
	 */
	/** option short name */
	private String optName;
	
	/** option long name */
	private String longOptName;
	
	/** option required number of argument */
	private int numOfArgs = ARGS_UNINITIALISED;
	
	/** option argument name */
	private String argName;
	
	/** option boolean if this option is required in the command */
	private boolean isRequired = false;
	
	/** option boolean if this option has a fixed number of argument */
	private boolean hasFixedArgNo;
	
	/** list of argument values */
	private List<String> values = new ArrayList<String>();
	
	/** option self-documenting string description */
	private String description;

	/** delimiter character for multiple argument values */
	private char delimiter = ',';
	
	/*=========================================================================
	 * Constructors
	 * ======================================================================== 
	 */
	/**
	 * Create an Option object with the specified parameters
	 * 
	 * @param optName short name of the option
	 * @param description description string of the option
	 */
	public Option(String optName, String description) {
		this(optName, null, ARGS_UNINITIALISED, description, DEFAULT_DELIMITER);
	}
	
	/**
	 * Create an Option object with the specified parameters
	 * 
	 * @param optName short name of the option
	 * @param longOptName long name of the option
	 * @param description description string of the option
	 */
	public Option(String optName, String longOptName, String description) {
		this(optName, longOptName, ARGS_UNINITIALISED, description, DEFAULT_DELIMITER);
	}
	
	/**
	 * Create an Option object with the specified parameters
	 * 
	 * @param optName short name of the option
	 * @param longOptName long name of the option
	 * @param numOfArgs number of required arguments
	 * @param description description string of the option
	 */
	public Option(String optName, String longOptName, int numOfArgs, String description) {
		this(optName, longOptName, numOfArgs, description, DEFAULT_DELIMITER);
	}
	
	/**
	 * Create an Option object with the specified parameters
	 * 
	 * @param optName short name of the option
	 * @param longOptName long name of the option
	 * @param numOfArgs number of required arguments
	 * @param description description string of the option
	 * @param delimiter a specified delimiter character between argument values
	 */
	public Option(String optName, String longOptName, int numOfArgs, String description, char delimiter) {
		isValidOption(optName);
		this.optName = optName;
		this.longOptName = longOptName;
		
		this.numOfArgs = numOfArgs;
		setHasFixedArgNo();
		
		this.description = description;
		this.delimiter = delimiter;
	}
	
	/*=========================================================================
	 * Getter & Setter Methods
	 * ========================================================================
	 */
	/**
	 * Retrieve the short name of this option
	 * 
	 * @return the short name of this option
	 */
	public String getOptName() {
		return optName;
	}

	/**
	 * Set the short name of this option
	 * 
	 * @param optName short name of this option
	 */
	public void setOptName(String optName) {
		this.optName = optName;
	}

	/**
	 * Query if this option has a long name alias
	 * 
	 * @return a boolean flag indicating the existence of long name
	 */
	public boolean hasLongOptName() {
		return this.longOptName != null;
	}
	
	/**
	 * Retrieve the long name of this option
	 * 
	 * @return the long name of this option. Null if there is none
	 */
	public String getLongOptName() {
		return longOptName;
	}

	/**
	 * Set the long name of this option
	 * 
	 * @param longOptName the long name of this option
	 */
	public void setLongOptName(String longOptName) {
		this.longOptName = longOptName;
	}

	/**
	 * Retrieve the number of arguments that this option is having
	 * 
	 * @return number of arguments
	 */
	public int getNumOfArgs() {
		return numOfArgs;
	}

	/**
	 * Set the number of arguments of this option
	 * 
	 * @param numOfArgs number of arguments
	 */
	public void setNumOfArgs(int numOfArgs) {
		this.numOfArgs = numOfArgs;
	}

	/**
	 * Retrieve the common name of the argument of this option
	 * 
	 * @return the general name of the argument of this option
	 */
	public String getArgName() {
		return argName;
	}

	/**
	 * Set the common name of the argument of this option
	 * 
	 * @param argName the name of the argument
	 */
	public void setArgName(String argName) {
		this.argName = argName;
	}

	/**
	 * Query to see if this option is required for the command
	 * 
	 * @return boolean flag indicating requirement of this option
	 */
	public boolean isRequired() {
		return isRequired;
	}

	/**
	 * Set the requirement of this option for the command
	 *  
	 * @param isRequired boolean flag if this option is required
	 */
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Query if this option has a fixed number of required arguments
	 * 
	 * @return number of required arguments for this option
	 */
	public boolean hasFixedArgNo() {
		return hasFixedArgNo;
	}

	/**
	 * Set the boolean flag hasFixedArgNo by checking if the number
	 * of required argument is nonnegative.
	 */
	public void setHasFixedArgNo() {
		this.hasFixedArgNo = this.numOfArgs >= 0;
	}
	/**
	 * Retrieve an array list of argument values
	 * 
	 * @return an array list of argument values. Null if there is none
	 */
	public List<String> getValues() {
		return values;
	}
	
	/**
	 * Set the array of list of argument values 
	 * Should use with caution
	 * 
	 * @param values the array list of argument values
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	/**
	 * Retrieve the self-documenting description string
	 * 
	 * @return this option description string
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description string of this option
	 * 
	 * @param description the description string of this option
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieve the delimiter character
	 * 
	 * @return delimiter character. Null if there is none
	 */
	public char getDelimiter() {
		return delimiter;
	}

	/**
	 * Set the delimiter character for multiple argument values
	 * 
	 * @param delimiter the delimiter character
	 */
	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Validate if the short name of this option observe the validation rule
	 * 
	 * The short option name is only valid when it is an single alphabet.
	 * 
	 * @param optName short name of this option to validate
	 * 
	 * @throws IllegalArgumentException if the short name does not follow the
	 * validation rule
	 */
	public static void isValidOption(String optName) throws IllegalArgumentException {
		if (optName == null) {
			// No further check
			return;
		}
		
		if (optName.length() == 1) {
			char startChar = optName.charAt(0);
			if (!Character.isAlphabetic(startChar)) {
				throw new IllegalArgumentException("Illegal option name '" + optName + "'");
			}
		} else {
			throw new IllegalArgumentException("Invalid option name '" + optName + "'" + 
										"Short option name must have only 1 alphabet");
		}
	}
}
