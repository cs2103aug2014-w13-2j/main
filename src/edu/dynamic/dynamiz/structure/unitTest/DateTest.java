package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;

public class DateTest {
    
    @Test
    public void testDate() {
	MyDate date = new MyDate();
	assertEquals("Equals default date", "1/1/1970", date.toString());
    }
    
    @Test
    public void testCopy(){
	MyDate date1 = new MyDate();
	MyDate date2 = new MyDate(date1);
	assertFalse("the 2 dates are different objects", date1==date2);
	assertTrue(date1.equals(date2));
    }
    
    @Test
    public void testDateIntIntInt() {
	MyDate date = new MyDate(2, 3, 1992);
	assertEquals("Day is 2", 2, date.getDayOfMonth());
	assertEquals("Month is 3", 3, date.getMonth());
	assertEquals("Year is 1992", 1992, date.getYear());
    }
    
    @Test
    public void testDateIntIntIntIntInt() {
	MyDate date = new MyDate(2, 3, 1992);
	assertEquals("Day is 2", 2, date.getDayOfMonth());
	assertEquals("Month is 3", 3, date.getMonth());
	assertEquals("Year is 1992", 1992, date.getYear());
    }
    
    @Test
    public void testToString() {
	MyDate date = new MyDate();
	assertEquals("Default date", "1/1/1970", date.toString());
	date = new MyDate(2, 3, 1992);
	assertEquals("Date is 2nd March 1992", "2/3/1992", date.toString());
    }
    
    @Test
    public void testSetDateIntIntInt() {
	MyDate date = new MyDate();
	date.setDate(27, 9, 2014);
	assertEquals("Day of month is 27", 27, date.getDayOfMonth());
	assertEquals("Month is 9", 9, date.getMonth());
	assertEquals("Year is 2014", 2014, date.getYear());
	
	date.setDate(31, 2, 1992);
	assertEquals("27/9/2014", date.toString());
    }
    
    @Test
    public void testCompareTo() {
	MyDate date1 = new MyDate(2, 3, 1992);
	MyDate date2 = new MyDate(9, 8, 1965);
	assertTrue("date2<date1", date2.compareTo(date1)<0);
	assertTrue("date1>date2", date1.compareTo(date2)>0);
	date2.setDate(2, 3, 1992);
	assertTrue("date1==date2", date1.compareTo(date2)==0);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalDate(){
	MyDate date = new MyDate(-1, -1, -1);
	date = new MyDate(2, 13, 1992);
	date = new MyDate(3, 0, 1);
	date = new MyDate(3, 1, 2014);
	date = new MyDate(30, 2, 1995);
	date = new MyDate(29, 2, 2013);
	date = new MyDate(32, 12, 1996);
	date = new MyDate(29, 2, 2100);
    }
    
    @Test
    public void testIsValidDate(){
	assertTrue("29/2/2012 is valid date", MyDate.isValidDate(29, 2, 2012));
	assertTrue("31/10/2014 is valid date", MyDate.isValidDate(31, 10, 2014));
	assertTrue("2/3/1992 is valid date", MyDate.isValidDate(2, 3, 1992));
	assertFalse("0/0/0 is invalid date", MyDate.isValidDate(0, 0, 0));
	assertFalse("32/2/-1 is invalid date", MyDate.isValidDate(32, 2, -1));
	assertFalse("29/2/2100 is invalid date", MyDate.isValidDate(29, 2, 2100));
    }
    
    @Test
    public void testCopyConstructor(){
	MyDate date = new MyDate(2, 3, 1992);
	MyDate date2 = new MyDate(date);
	assertFalse(date==date2);
	assertEquals(date, date2);
	date2.setDate(1, 5, 2004);
	assertFalse(date.equals(date2));
    }
    
}
