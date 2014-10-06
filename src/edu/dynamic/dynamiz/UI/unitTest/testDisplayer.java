package edu.dynamic.dynamiz.UI.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.UI.Displayer;
import edu.dynamic.dynamiz.structure.*;

public class testDisplayer {
	Displayer dp = new Displayer();
	public void setUp(){
		
		
	}
	@Test
	public void testDisplayString() {
		String t1 = "test String";
		dp.displayString(t1);
		assertEquals(dp.displayString(t1),t1);
	}
	
	@Test
	public void testDisplayPrompy(){
		String t1 = "Enter condition:";
		int promptTag = 2;
		assertEquals(dp.displayPrompt(),"Please Enter Command:");
		
		assertEquals(dp.displayPrompt(t1),"Enter condition:");
		
		assertEquals(dp.displayPrompt(promptTag),"Please Enter Task:");
		
	}
	
	@Test public void testDisplayTask(){
		
	}
	

}
