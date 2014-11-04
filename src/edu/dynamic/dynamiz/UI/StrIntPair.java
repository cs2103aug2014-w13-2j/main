package edu.dynamic.dynamiz.UI;

/**
 * @author Hu Wenyan
 *
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
