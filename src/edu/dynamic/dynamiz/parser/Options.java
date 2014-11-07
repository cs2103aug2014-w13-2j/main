//@author A0113855E
package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * List of Options
 * This is a container of {@link Option}. Multiple {@link Option} of the same {@link OptionType} will be 
 * grouped together.
 * <p>
 * Provide utility methods to check for ambiguity if one {@link OptionType} has more than one occurrence.
 * 
 * @author nhan
 *
 */
public class Options {

	private Map<OptionType, List<Option>> optionTable;

	public Options() {
		optionTable = new HashMap<OptionType, List<Option>>();
	}
	
	/**
	 * Add an option to the Options collection.
	 * 
	 * @param opt {@link Option} to add to Options
	 * @return true of the Option input is successfully added. False otherwise.
	 */
	public boolean add(Option opt) {
		OptionType optType = OptionType.fromOption(opt);
		
		List<Option> optionList = null;
		
		if (optionTable.containsKey(optType)) {
			optionList = optionTable.get(optType);
		} else {
			optionList = new ArrayList<Option>();
		}
		optionList.add(opt);
		optionTable.put(optType, optionList);
		
		return true;
	}
	
	/**
	 * Add a list of {@link Option}. The {@link OptionType} of the list will have to be the same for all 
	 * the elements. Otherwise, the operation will return false and nothing will be added to Options collection.
	 * 
	 * @param optList the list of {@link Option} to be added
	 * @return true if the list of {@link Option} is of the same {@link OptionType} and successfully added to the
	 * collection. False otherwise. 
	 */
	public boolean add(List<Option> optList) {
		assert(optList != null);
		
		if (optList.size() <= 0) {
			throw new IllegalArgumentException("Empty list");
		}
		
		OptionType optType = OptionType.fromOption(optList.get(0));
		for (Option op: optList) {
			if (OptionType.fromOption(op) != optType) {
				return false;
			}
		}
		
		optionTable.put(optType, optList);
		return true;
	}
	


	/**
	 * Query if this List contains a particular option
	 * 
	 * @param opt the Option to query
	 * @return boolean flag if the matching option exists in the List
	 */
	public boolean hasOption(Option opt) {
		OptionType optType = OptionType.fromOption(opt);
		
		if (optionTable.containsKey(optType)) {
			List<Option> options = optionTable.get(optType);
			return options.contains(opt);
		} else {
			return false;
		}
	}
	
	/**
	 * Query if this List contains an option name
	 * 
	 * @param optName String name for the option
	 * @return boolean flag if a certain option type exists.
	 */
	public boolean hasOption(String optName) {
		return optionTable.containsKey(OptionType.fromString(optName));
	}
	
	/**
	 * Query if this List contains an option name
	 * 
	 * @param optName String name for the option
	 * @return boolean flag if a certain option type exists.
	 */
	public boolean hasOption(OptionType optType) {
		return optionTable.containsKey(optType);
	}
	
	/**
	 * Retrieve a passed Option if it exists in this List
	 * 
	 * @param opt an Option to search for in the List
	 * @return an Option object that exists in the List
	 */
	public Option getOption(Option opt) {
		OptionType optType = OptionType.fromOption(opt);
		
		for (Option o: optionTable.get(optType)) {
			if (opt.equals(o)) {
				return o;
			}
		}
		return null;
	}
	
	/**
	 * Retrieve the List of Option that has the same type
	 * 
	 * @param optName an alias of the option to specify OptionType
	 * @return a List of all the options of that OptionType
	 */
	public List<Option> getOptionList(String optName) {
		OptionType optType = OptionType.fromString(optName);
		
		return getOptionList(optType);
	}
	
	/**
	 * Retrieve the List of Option that is of the given type
	 * 
	 * @param optType OptionType to retrieve
	 * @return a List of all the options of that OptionType
	 */
	public List<Option> getOptionList(OptionType optType) {
		return optionTable.get(optType);
	}

	/**
	 * Retrieve the number of different {@link OptionType} of {@link Option} present in the collection
	 * @return the number of different {@link OptionType}
	 */
	public int getNumOfOptions() {
		return optionTable.size();
	}
	
	/**
	 * Check if the collection contains cases of ambiguity. This is done by checking that for at least one
	 * {@link OptionType} of {@link Option}, there is more more than one {@link Option} present in the collection
	 *
	 * @return true if there is a case of ambiguity described above. False otherwise.
	 */
	public List<OptionType> getAmbiguousOptionTypes() {
		List<OptionType> list = new ArrayList<OptionType>();
		for (Entry<OptionType, List<Option>> e: optionTable.entrySet()) {
			if (e.getValue().size() > 1) { // if there are 2 more options for 1 type
				list.add(e.getKey());
			}
		}
		
		return list;
	}
	
	/**
	 * Check if the Options contains no Option. 
	 * @return True if the Options is empty, i.e. containing no Option
	 */
	public boolean isEmpty() {
		return optionTable.isEmpty();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Entry<OptionType, List<Option>> e: optionTable.entrySet()) {
			OptionType type = e.getKey();
			List<Option> opts = e.getValue();
			sb.append(type + ": ");
			
			for (Option opt: opts) {
				sb.append(opt.toString() + " ");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
