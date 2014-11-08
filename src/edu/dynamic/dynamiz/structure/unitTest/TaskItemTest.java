package edu.dynamic.dynamiz.structure.unitTest;
import edu.dynamic.dynamiz.structure.*;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.TaskItem;

/**
 * Defines the JUnit Test Case for TaskItem class.
 */
//@author A0110781N
public class TaskItemTest {
    
    @Test
    public void testToFileString() {
	TaskItem task = new TaskItem("Return book", new MyDate(1, 10, 2014));
	assertEquals("Return book; 0; Pending; --/--/---- --:--; 1/10/2014 --:--", task.toFileString());
	
	task.setDeadlineTime(10, 40);
	assertEquals("Return book; 0; Pending; --/--/---- --:--; 1/10/2014 10:40", task.toFileString());
    }
    
    @Test
    public void test() {
	TaskItem task = new TaskItem("CS2106 homework", 4, new MyDateTime(26, 9, 2014, 23, 59));
	assertEquals("CS2106 homework", task.getDescription());
	assertEquals(ToDoItem.PRIORITY_HIGH, task.getPriority());
	assertEquals(new MyDateTime(26, 9, 2014, 23, 59), task.getDeadline());
    }
    
    @Test
    public void testSetDeadline() {
	TaskItem task = new TaskItem("CS2106 homework", new MyDate(24, 9, 2014));
	assertEquals("24/9/2014", task.getDeadline().toString());
	
	task.setDeadline(new MyDateTime(26, 9, 2014, 23, 59));
	assertEquals("26/9/2014 23:59", task.getDeadline().toString());
    }
    
    @Test
    public void testSetDeadlineDate() {
	TaskItem task = new TaskItem("CS2106 homework", new MyDate(27, 9, 2014));
	assertEquals(new MyDate(27, 9, 2014), task.getDeadline());
	
	task.setDeadlineDate(new MyDate(26, 9, 2014));
	assertEquals(new MyDate(26, 9, 2014), task.getDeadline());
    }
    
    @Test
    public void testSetDeadlineTime() {
	TaskItem task = new TaskItem("CS2106 homework", new MyDate(26, 9, 2014));
	assertTrue("deadline is Date type", !(task.getDeadline() instanceof MyDateTime));
	
	//This is supposed to end up with nothing done due to invalid value.
	task.setDeadlineTime(22, 60);
	assertTrue("deadline is Date type", !(task.getDeadline() instanceof MyDateTime));
	
	task.setDeadlineTime(23, 59);
	assertTrue("deadline is DateTime type", task.getDeadline() instanceof MyDateTime);
	assertEquals(new MyDateTime(26, 9, 2014, 23, 59), task.getDeadline());
    }
    
    @Test
    public void testSetStatus(){
	TaskItem task = new TaskItem("CS2106 homework", new MyDate(26, 9, 2014));
	assertEquals("initial status is 'pending'", TaskItem.STATUS_PENDING, task.getStatus());
	
	task.setStatus(TaskItem.STATUS_COMPLETED);
	assertEquals("new status is 'completed'", TaskItem.STATUS_COMPLETED, task.getStatus());
    }
    
    @Test
    //Tests the copy constructor
    public void testCopyConstructor(){
	TaskItem task = new TaskItem("Homework", 2, new MyDate(13, 10, 2014));
	TaskItem task2 = new TaskItem(task);
	assertFalse(task==task2);
	assertEquals(task.getId(), task2.getId());
	assertEquals(task, task2);
	task2.setPriority(5);
	assertFalse(task.getPriority()==task2.getPriority());
    }
    
    @Test
    //Tests comparison.
    //Natural ordering is in ascending order of status('pending'<'completed'), deadline, -priority.
    public void testCompareTo(){
	ToDoItem task1 = new TaskItem("A", 4, new MyDate(5, 11, 2014));
	ToDoItem task2 = new ToDoItem("B", 4);
	assertEquals(-1, task1.compareTo(task2));
	task2 = new TaskItem("B", 4, new MyDate(4, 11, 2014));
	assertEquals(1, task1.compareTo(task2));
	assertEquals(-1, task2.compareTo(task1));
	task2 = new EventItem("B", 4, new MyDate(4, 11, 2014), new MyDate(6, 11, 2014));
	assertEquals(1, task1.compareTo(task2));
	task2 = new EventItem("B", 2, new MyDate(5, 11, 2014), new MyDate(6, 11, 2014));
	assertTrue(task1.compareTo(task2)<0);
    }
}
