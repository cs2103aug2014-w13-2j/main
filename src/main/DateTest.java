package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTest {
    
    @Test
    public void testDate() {
	Date date = new Date();
	assertEquals("Equals default date", "1/1/1970", date.toString());
    }
    
    @Test
    public void testDateIntIntInt() {
	Date date = new Date(2, 3, 1992);
	assertEquals("Day is 2", 2, date.getDate());
	assertEquals("Month is 3", 3, date.getMonth());
	assertEquals("Year is 1992", 1992, date.getYear());
    }
    
    @Test
    public void testDateIntIntIntIntInt() {
	Date date = new Date(2, 3, 1992);
	assertEquals("Day is 2", 2, date.getDate());
	assertEquals("Month is 3", 3, date.getMonth());
	assertEquals("Year is 1992", 1992, date.getYear());
    }
    
    @Test
    public void testToString() {
	Date date = new Date();
	assertEquals("Default date", "1/1/1970", date.toString());
	date = new Date(2, 3, 1992);
	assertEquals("Date is 2nd March 1992", "2/3/1992", date.toString());
    }
    
    @Test
    public void testToFileString() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testSetDateIntIntInt() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testSetDateIntIntIntIntInt() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testCompareTo() {
	fail("Not yet implemented");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalDate(){
	Date date = new Date(-1, -1, -1);
	date = new Date(2, 13, 1992);
	date = new Date(3, 0, 1);
	date = new Date(3, 1, 2014);
	date = new Date(30, 2, 1995);
	date = new Date(29, 2, 2013);
	date = new Date(32, 12, 1996);
	date = new Date(29, 2, 2100);
    }
    
    @Test
    public void testIsValidDate(){
	assertTrue("2/3/1992 is valid date", Date.isValidDate(2, 3, 1992));
	assertFalse("0/0/0 is invalid date", Date.isValidDate(0, 0, 0));
	assertFalse("32/2/-1 is invalid date", Date.isValidDate(32, 2, -1));
    }
    
    
}
