package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.EventItem;
import edu.dynamic.dynamiz.structure.TaskItem;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class EventItemTest {
    
    @Test
    public void testToString() {
	EventItem item = new EventItem("Nana's concert", 5, new MyDate(27, 9, 2014));
	assertEquals("Nana's concert; 5; pending; 27/9/2014 --:--; 27/9/2014 --:--", item.toFileString());
	
	item = new EventItem("CS2105 midterms", new MyDateTime(4, 10, 2014, 17, 0));
	assertEquals("CS2105 midterms; 0; pending; 4/10/2014 17:00; 4/10/2014 17:00", item.toFileString());
    }
    
    @Test
    public void testToFileString(){
	EventItem item = new EventItem("Nana's concert", 5, new MyDateTime(27, 9, 2014, 17, 30), new MyDate(27, 9, 2014));
	assertEquals("Nana's concert; 5; pending; 27/9/2014 17:30; 27/9/2014 --:--", item.toFileString());
    }
    
    @Test
    public void testSetDate(){
	EventItem item = new EventItem("CS2010R lesson", new MyDateTime(23, 9, 2014, 18, 30));
	assertEquals(new MyDateTime(23, 9, 2014, 18, 30), item.getStartDate());
	assertEquals(new MyDateTime(23, 9, 2014, 18, 30), item.getEndDate());
	
	item.setStartDate(new MyDate(24, 9, 2014));
	assertEquals(new MyDate(24, 9, 2014), item.getStartDate());
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
    
    @Ignore
    public void testIdPool(){
	ToDoItem item1 = new ToDoItem("Task 1");
	EventItem item2 = new EventItem("Event 1", new MyDate(30, 9, 2014));
	TaskItem item3 = new TaskItem("Task 2", new MyDate(30, 9, 2014));
	System.out.println(item1.getId());
	System.out.println(item2.getId());
	System.out.println(item3.getId());
    }
    
    @Test
    public void testConvertFromTaskItem(){
	TaskItem task = new TaskItem("A", new MyDate(31, 12, 2014));
	EventItem event = new EventItem(task, new MyDate(30, 12, 2014));
	System.out.println(event);
	System.out.println();
    }
    
    @Test
    public void testCompareTo(){
	ToDoItem event1 = new EventItem("A", 4, new MyDate(4, 11, 2014), new MyDate(5, 11, 2014));
	ToDoItem event2 = new EventItem("B", 4, new MyDate(5, 11, 2014), new MyDate(5, 11, 2014));
	assertEquals(-1, event1.compareTo(event2));
	event2 = new ToDoItem("B", 4);
	assertEquals(-1, event1.compareTo(event2));
	event2 = new TaskItem("B", 8, new MyDate(4, 11, 2014));
	assertTrue(event1.compareTo(event2)>0);
	event2 = new EventItem("B", 4, new MyDate(4, 11, 2014), new MyDate(6, 11, 2014));
	assertTrue(event1.compareTo(event2)<0);
    }
}
