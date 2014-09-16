package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTest {
    
    @Test
    public void testDate() {
	Date date = new Date();
	assertEquals("Equals invalid date", "--/--/---- --:--", date.toString());
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
	Date date = new Date(2, 3, 1992, 12, 0);
	assertEquals("Day is 2", 2, date.getDate());
	assertEquals("Month is 3", 3, date.getMonth());
	assertEquals("Year is 1992", 1992, date.getYear());
	assertEquals("Hour is 12", 12, date.getHour());
	assertEquals("Minute is 0", 0, date.getMinute());
    }
    
    @Test
    public void testToString() {
	Date date = new Date();
	assertEquals("Date is unset", "--/--/---- --:--", date.toString());
	date = new Date(2, 3, 1992);
	assertEquals("Date is 2nd March 1992", "2/3/1992 --:--", date.toString());
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
	date = new Date(3, 1, 2014, -1, 3);
	date = new Date(30, 2, 1995);
	date = new Date(29, 2, 2013);
	date = new Date(32, 12, 1996);
	//fail("Not yet implemented");
    }
    
}
