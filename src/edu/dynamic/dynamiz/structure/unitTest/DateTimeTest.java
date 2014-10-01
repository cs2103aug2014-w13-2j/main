package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.Date;
import edu.dynamic.dynamiz.structure.DateTime;

public class DateTimeTest {
    
    @Test
    public void testDateTime() {
	DateTime dt = new DateTime();
	assertEquals("Day of month is 1", 1, dt.getDayOfMonth());
	assertEquals("Month is 1", 1, dt.getMonth());
	assertEquals("Year is 1970", 1970, dt.getYear());
    }
    
    @Test
    public void testCopy(){
	DateTime dt1 = new DateTime();
	DateTime dt2 = new DateTime(dt1);
	assertFalse("the 2 objects are not the same", dt1==dt2);
	assertTrue("the 2 objects represent the same date and time", dt1.equals(dt2));
    }
    
    @Test
    public void testDateTimeIntIntIntIntInt() {
	try{
	    DateTime dt = new DateTime(2, 3, 1992, 9, 0);
	    assertEquals("Hour is 9", 9, dt.getHour());
	    assertEquals("Minute is 0", 0, dt.getMinute());
	    dt.setTime(9, 30);
	    assertEquals("Hour is 9", 9, dt.getHour());
	    assertEquals("Minute is 30", 30, dt.getMinute());
	} catch(IllegalArgumentException e){
	    System.out.println("Error");
	}
    }
    
    @Test
    public void testToString(){
	DateTime dt = new DateTime();
	assertEquals("1/1/1970 0:00", dt.toString());
	dt = new DateTime(2, 3, 1992, 3, 10);
	assertEquals("2/3/1992 3:10", dt.toString());
    }
    
    @Test
    public void testIsValidTime(){
	assertTrue("00:00 is valid time", DateTime.isValidTime(0, 0));
	assertFalse("-1:20 is invalid time", DateTime.isValidTime(-1, 20));
	assertFalse("20: 60 is invalid time", DateTime.isValidTime(20, 60));
	assertFalse("24:-3 is invalid time", DateTime.isValidTime(24, -3));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalDateTime(){
	DateTime dt = new DateTime(-1, -1, -1, -1, -1);
	dt = new DateTime(1, 1, 1970, 12, 70);
	dt.setDateTime(30, 2, 2014, 13, 45);
	dt.setTime(24, 0);
    }
    
    @Test
    public void testCompareTo(){
	Date date = new Date();
	DateTime dt1 = new DateTime();
	DateTime dt2 = new DateTime(5, 5, 1995, 22, 59);
	assertTrue("date==dt1", date.compareTo(dt1)==0);
	assertTrue("dt1<dt2", dt1.compareTo(dt2)<0);
	assertTrue("dt2>dt1", dt2.compareTo(dt1)>0);
	dt1.setDate(5, 5, 1995);
	assertTrue("dt1<dt2", dt1.compareTo(dt2)<0);
	assertTrue("date<dt1", date.compareTo(dt1)<0);
    }
}