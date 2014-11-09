package edu.dynamic.dynamiz.UI;

//@author A0119397R
/**
 * Data structure designed for passing parameters between DisplayFormatter and UI
 * In our UI part, UI takes in StrIntPair where Str is the content to display 
 * and Int indicates the color tag of this String
 */
public class StrIntPair {
	static final int defaultTag = 0;
	private final String s;
	private final int intg;
	
	/**
	 * @param s
	 * @param i
	 * Construct a new StrIntPair Object with both String content and int tag 
	 */
	public StrIntPair(String s, int i){
		this.s = s;
		this.intg = i;
	}
	
	/**
	 * @param s
	 * Construct a new StrIntPair with String and default tag
	 */
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
