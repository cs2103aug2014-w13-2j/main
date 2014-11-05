package edu.dynamic.dynamiz.structure;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test class to test correctness of StartDateComparator.
 * @author A0110781N
 */
public class StartDateComparatorTest {
    
    @Test
    public void test() {
	StartDateComparator comp = new StartDateComparator();
	ToDoItem todo = new ToDoItem("A");
	EventItem event = new EventItem("A", new MyDateTime(31, 12, 2014, 17, 17));
	EventItem event2 = new EventItem("B", new MyDate(25, 12, 2014));
	TaskItem task = new TaskItem("A", new MyDate(1, 1, 2015));
	TaskItem task2 = new TaskItem("B", new MyDateTime(14, 1, 2015, 19, 25));
	assertTrue(comp.compare(event, todo)<0);
	assertTrue(comp.compare(todo, event)>0);
	assertTrue(comp.compare(task, todo)<0);
	assertTrue(comp.compare(todo, task)>0);
	assertTrue(comp.compare(todo, todo)==0);
	assertTrue(comp.compare(event2, task2)<0);
	assertTrue(comp.compare(event, task)<0);
	assertTrue(comp.compare(event, event2)>0);
	assertTrue(comp.compare(task, task2)<0);
    }
    
}
