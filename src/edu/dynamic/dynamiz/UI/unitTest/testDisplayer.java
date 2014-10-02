package edu.dynamic.dynamiz.UI.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.UI.Displayer;

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

}
