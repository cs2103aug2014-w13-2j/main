package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class EventItemTest {
    
    @Test
    public void testToString() {
	EventItem item = new EventItem("Nana's concert", 5, new MyDate(27, 9, 2014));
	assertEquals("A2 Nana's concert 5 pending 27/9/2014 --:-- 27/9/2014 --:--", item.toString());
	
	item.setStartTime(18, 30);
	assertEquals("A2 Nana's concert 5 pending 27/9/2014 18:30 27/9/2014 --:--", item.toString());
	
	item = new EventItem("CS2105 midterms", new MyDateTime(4, 10, 2014, 17, 0));
	assertEquals("A3 CS2105 midterms 0 pending 4/10/2014 17:00 4/10/2014 17:00", item.toString());
	item.setEndTime(18, 0);
	assertEquals("A3 CS2105 midterms 0 pending 4/10/2014 17:00 4/10/2014 18:00", item.toString());
	System.out.println(item.getFeedbackString());
    }
    
    @Test
    public void testToFileString(){
	EventItem item = new EventItem("Nana's concert", 5, new MyDateTime(27, 9, 2014, 17, 30), new MyDate(27, 9, 2014));
	assertEquals("Nana's concert; 5; pending; 27/9/2014 17:30; 27/9/2014 --:--", item.toFileString());
	
	item.setStartTime(18, 30);
	item.setEndTime(22, 0);
	assertEquals("Nana's concert; 5; pending; 27/9/2014 18:30; 27/9/2014 22:00", item.toFileString());
    }
    
    @Test
    public void testSetDate(){
	EventItem item = new EventItem("CS2010R lesson", new MyDateTime(23, 9, 2014, 18, 30));
	assertEquals("A5 CS2010R lesson 0 pending 23/9/2014 18:30 23/9/2014 18:30", item.toString());
	
	item.setStartDate(new MyDate(24, 9, 2014));
	assertEquals("A5 CS2010R lesson 0 pending 24/9/2014 18:30 23/9/2014 18:30", item.toString());
	
	item.setStartTime(24, 0);
	assertEquals("A5 CS2010R lesson 0 pending 24/9/2014 18:30 23/9/2014 18:30", item.toString());
    }
    
    @Test
    public void testCopyConstructor(){
	EventItem event =  new EventItem("Birthday", new MyDate(31, 10, 2014));
	EventItem event2 = new EventItem(event);
	assertFalse(event==event2);
	assertTrue(event.equals(event2));
	event2.setEndDate(new MyDate(1, 11, 2014));
	assertFalse(event.getEndDate().equals(event2.getEndDate()));
	assertEquals(event.getId(), event2.getId());
	event2.setStatus(ToDoItem.STATUS_COMPLETED);
	assertFalse(event.getStatus().equals(event2.getStatus()));
    }
    
    //@Test Test shows the pool of ID strings is shared.
    /*public void testIdPool(){
	ToDoItem item1 = new ToDoItem("Task 1");
	EventItem item2 = new EventItem("Event 1", new Date(30, 9, 2014));
	TaskItem item3 = new TaskItem("Task 2", new Date(30, 9, 2014));
	System.out.println(item1.getId());
	System.out.println(item2.getId());
	System.out.println(item3.getId());
    }*/
}
