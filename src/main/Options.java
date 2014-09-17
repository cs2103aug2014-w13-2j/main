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
public class Options implements Collection<Option>, Iterable<Option> {

	private Map<OptionType, Collection<Option>> optionTable;

	@Override
	public boolean add(Option opt) {
		// Here must determine which option group it falls to
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
	public boolean addAll(Collection<? extends Option> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Option> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
