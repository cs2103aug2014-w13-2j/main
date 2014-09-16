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
	fail("Not yet implemented");
    }
    
    @Test
    public void testToString() {
	fail("Not yet implemented");
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
    
}
