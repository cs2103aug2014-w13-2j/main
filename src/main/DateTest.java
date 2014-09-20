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
	assertEquals("Day is 2", 2, date.getDayOfMonth());
	assertEquals("Month is 3", 3, date.getMonth());
	assertEquals("Year is 1992", 1992, date.getYear());
    }
    
    @Test
    public void testDateIntIntIntIntInt() {
	Date date = new Date(2, 3, 1992);
	assertEquals("Day is 2", 2, date.getDayOfMonth());
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
    public void testSetDateIntIntInt() {
	try{
	    Date date = new Date();
	    date.setDate(27, 9, 2014);
	    assertEquals("Day of month is 27", 27, date.getDayOfMonth());
	    assertEquals("Month is 9", 9, date.getMonth());
	    assertEquals("Year is 2014", 2014, date.getYear());
	} catch(IllegalArgumentException e){
	    System.out.println("error");
	}
    }
    
    @Test
    public void testCompareTo() {
	Date date1 = new Date(2, 3, 1992);
	Date date2 = new Date(9, 8, 1965);
	assertTrue("date2<date1", date2.compareTo(date1)<0);
	assertTrue("date1>date2", date1.compareTo(date2)>0);
	date2.setDate(2, 3, 1992);
	assertTrue("date1==date2", date1.compareTo(date2)==0);
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
	date.setDate(29, 2, 2100);
	date.setDate(-1, -1, -1);
	date.setDate(2, 13, 1992);
	date.setDate(3, 0, 1);
	date.setDate(30, 2, 1995);
	date.setDate(29, 2, 2013);
	date.setDate(32, 12, 1996);
    }
    
    @Test
    public void testIsValidDate(){
	assertTrue("29/2/2012 is valid date", Date.isValidDate(29, 2, 2012));
	assertTrue("31/10/2014 is valid date", Date.isValidDate(31, 10, 2014));
	assertTrue("2/3/1992 is valid date", Date.isValidDate(2, 3, 1992));
	assertFalse("0/0/0 is invalid date", Date.isValidDate(0, 0, 0));
	assertFalse("32/2/-1 is invalid date", Date.isValidDate(32, 2, -1));
	assertFalse("29/2/2100 is invalid date", Date.isValidDate(29, 2, 2100));
    }
    
}
