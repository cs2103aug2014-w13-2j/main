package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeTest {
    
    @Test
    public void testDateTime() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testDateTimeIntIntIntIntInt() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testIsValidTime(){
	assertTrue("00:00 is valid time", DateTime.isValidTime(0, 0));
	assertFalse("-1:20 is invalid time", DateTime.isValidTime(-1, 20));
	assertFalse("20: 60 is invalid time", DateTime.isValidTime(20, 60));
	assertFalse("24:-3 is invalid time", DateTime.isValidTime(24, -3));
    }
}
