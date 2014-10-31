package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * List of Options
 * 
 * @author nhan
 *
 */
public class Options implements Iterable<Option> {

	private Map<OptionType, List<Option>> optionTable;

	public Options() {
		optionTable = new HashMap<OptionType, List<Option>>();
	}
	
	public boolean add(Option opt) {
		OptionType optType = OptionType.fromOption(opt);
		
		List<Option> options = null;
		if (optionTable.containsKey(optType)) {
			options = optionTable.get(optType);
		} else {
			options = new ArrayList<Option>();
		}
		options.add(opt);
		optionTable.put(optType, options);
		
		return true;
	}
	
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
	public List<Option> getOptions(String optName) {
		OptionType optType = OptionType.fromString(optName);
		
		return getOptions(optType);
	}
	
	/**
	 * Retrieve the List of Option that is of the given type
	 * 
	 * @param optType OptionType to retrieve
	 * @return a List of all the options of that OptionType
	 */
	public List<Option> getOptions(OptionType optType) {
		return optionTable.get(optType);
	}

	public int getNumOfOptions() {
		return optionTable.size();
	}
	
	public boolean hasAmbiguity() {
		for (List<Option> c: optionTable.values()) {
			if (c.size() > 1) {
				return true;
			}
		}
		return false;
	}
	
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
	public Iterator<Option> iterator() {
		// TODO Auto-generated method stub
		return null;
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
