package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Collection of Options
 * 
 * @author nhan
 *
 */
public class Options implements Iterable<Option> {

	private Map<OptionType, Collection<Option>> optionTable;

	public boolean add(Option opt) {
		OptionType optType = OptionType.fromOption(opt);
		
		Collection<Option> options = null;
		if (optionTable.containsKey(optType)) {
			options = optionTable.get(optType);
		} else {
			options = new ArrayList<Option>();
		}
		options.add(opt);
		optionTable.put(optType, options);
		
		return true;
	}

	/**
	 * Query if this collection contains a particular option
	 * 
	 * @param opt the Option to query
	 * @return boolean flag if the matching option exists in the collection
	 */
	public boolean hasOption(Option opt) {
		OptionType optType = OptionType.fromOption(opt);
		
		if (optionTable.containsKey(optType)) {
			Collection<Option> options = optionTable.get(optType);
			return options.contains(opt);
		} else {
			return false;
		}
	}
	
	/**
	 * Query if this collection contains an option name
	 * 
	 * @param optName String name for the option
	 * @return boolean flag if a certain option type exists.
	 */
	public boolean hasOption(String optName) {
		return optionTable.containsKey(OptionType.fromString(optName));
	}
	
	/**
	 * Retrieve a passed Option if it exists in this collection
	 * 
	 * @param opt an Option to search for in the collection
	 * @return an Option object that exists in the collection
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
	 * Retrieve the collection of Option that has the same type
	 * 
	 * @param optName an alias of the option to specify OptionType
	 * @return a collection of all the options of that OptionType
	 */
	public Collection<Option> getOptions(String optName) {
		OptionType optType = OptionType.fromString(optName);
		
		return getOptions(optType);
	}
	
	/**
	 * Retrieve the collection of Option that is of the given type
	 * 
	 * @param optType OptionType to retrieve
	 * @return a collection of all the options of that OptionType
	 */
	public Collection<Option> getOptions(OptionType optType) {
		return optionTable.get(optType);
	}
	
	public boolean hasAmbiguity() {
		for (Collection<Option> c: optionTable.values()) {
			if (c.size() > 1) {
				return true;
			}
		}
		return false;
	}
	
	public Collection<OptionType> getAmbiguousOptionTypes() {
		Collection<OptionType> list = new ArrayList<OptionType>();
		for (Entry<OptionType, Collection<Option>> e: optionTable.entrySet()) {
			if (e.getValue().size() > 1) { // if there are 2 more options for 1 type
				list.add(e.getKey());
			}
		}
		
		return list;
	}
	
	@Override
	public Iterator<Option> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
