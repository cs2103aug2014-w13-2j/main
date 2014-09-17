package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	
	@Override
	public Iterator<Option> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
