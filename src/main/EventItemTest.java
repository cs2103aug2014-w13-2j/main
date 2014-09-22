package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventItemTest {
    
    @Test
    public void testToString() {
	EventItem item = new EventItem("Nana's concert", 5, new Date(27, 9, 2014));
	assertEquals("A1 Nana's concert 5 pending 27/9/2014 --:-- 27/9/2014 --:--", item.toString());
	
	item.setStartTime(18, 30);
	assertEquals("A1 Nana's concert 5 pending 27/9/2014 18:30 27/9/2014 --:--", item.toString());
	
	item = new EventItem("CS2105 midterms", new DateTime(4, 10, 2014, 17, 0));
	assertEquals("A2 CS2105 midterms 0 pending 4/10/2014 17:00 4/10/2014 17:00", item.toString());
	item.setEndTime(18, 0);
	assertEquals("A2 CS2105 midterms 0 pending 4/10/2014 17:00 4/10/2014 18:00", item.toString());
    }
    
    @Test
    public void testToFileString(){
	EventItem item = new EventItem("Nana's concert", 5, new DateTime(27, 9, 2014, 17, 30), new Date(27, 9, 2014));
	assertEquals("Nana's concert; 5; pending; 27/9/2014 17:30; 27/9/2014 --:--", item.toFileString());
	
	item.setStartTime(18, 30);
	item.setEndTime(22, 0);
	assertEquals("Nana's concert; 5; pending; 27/9/2014 18:30; 27/9/2014 22:00", item.toFileString());
    }
    
    @Test
    public void testSetDate(){
	EventItem item = new EventItem("CS2010R lesson", new DateTime(23, 9, 2014, 18, 30));
	assertEquals("A4 CS2010R lesson 0 pending 23/9/2014 18:30 23/9/2014 18:30", item.toString());
	
	item.setStartDate(new Date(24, 9, 2014));
	assertEquals("A4 CS2010R lesson 0 pending 24/9/2014 18:30 23/9/2014 18:30", item.toString());
	
	item.setStartTime(24, 0);
	assertEquals("A4 CS2010R lesson 0 pending 24/9/2014 18:30 23/9/2014 18:30", item.toString());
    }
}
