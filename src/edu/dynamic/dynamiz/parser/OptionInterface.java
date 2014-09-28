package edu.dynamic.dynamiz.parser;

import java.util.List;

public interface OptionInterface {
	public String getOptName();
	public void setOptName(String optName);
	public boolean hasLongOptName();
	public String getLongOptName();
	public void setLongOptName(String longOptName);
	public int getNumOfArgs();
	public void setNumOfArgs(int numOfArgs);
	public String getArgName();
	public void setArgName(String argName);
	public boolean isRequired();
	public void setRequired(boolean isRequired);
	public boolean hasFixedArgNo();
	public void setHasFixedArgNo();
	public List<String> getValues();
	public void setValues(List<String> values);
	public void isValidOption(String optName) throws IllegalArgumentException;
	
}
