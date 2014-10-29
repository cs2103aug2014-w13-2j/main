package edu.dynamic.dynamiz.UI;

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
}
