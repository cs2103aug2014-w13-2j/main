package edu.dynamic.dynamiz.UI;

/**
 * @author A0119397R
 * Data structure designed for passing parameters between DisplayFormatter and UI
 * In our UI part, UI takes in StrIntPair where Str is the content to display 
 * and Int indicates the color tag of this String
 */
public class StrIntPair {
	static final int defaultTag = 0;
	private final String s;
	private final int intg;
	public StrIntPair(String s, int i){
		this.s = s;
		this.intg = i;
	}
	public StrIntPair(String s){
		this.s = s;
		this.intg = defaultTag;
	}
	public String getString(){
		return s;
	}
	public int getInt(){
		return intg;
	}
}
