package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.List;

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

public class Option{
	/*=========================================================================
	 * Constants
	 * ========================================================================
	 */
	
	/** constant that specifies the default character for delimiter of argument values */
	public static final String DEFAULT_DELIMITER = ",";
	
	/*=========================================================================
	 * Class attributes
	 * ========================================================================
	 */
	/** option short name */
	private OptionType optionType;

	/** list of argument values */
	private List<String> values = new ArrayList<String>();
	
	/** delimiter character for multiple argument values */
	private char delimiter = ',';
	
	/*=========================================================================
	 * Constructors
	 * ======================================================================== 
	 */

	/**
	 * Construct an Option object that hold the OptionType and a list of argument value strings
	 * @param optionType Type of this Option object
	 * @param values List of value strings
	 */
	public Option(OptionType optionType, List<String> values) {
		this.optionType = optionType;
		this.values = values;
	}
	
	/**
	 * Construct an Option object with a given OptionType as String and a list of argument values.
	 * @param typeString OptionType in String
	 * @param values List of values
	 */
	public Option(String typeString, List<String> values) {
		this.optionType = OptionType.fromString(typeString);
		this.values = values;
	}
	
	/*=========================================================================
	 * Getter & Setter Methods
	 * ========================================================================
	 */

	/**
	 * Retrieve the OptionType of this Option object
	 * @return return an OptionType instance that this Option belongs to
	 */
	public OptionType getOptionType() {
		return optionType;
	}

	/**
	 * Set the OptionType of this Option object
	 * @param optionType the OptionType instance to set
	 */
	public void setOptionType(OptionType optionType) {
		this.optionType = optionType;
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


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<" + this.optionType.name() + ", " + this.values + ">");
		
		return sb.toString();
	}
}
