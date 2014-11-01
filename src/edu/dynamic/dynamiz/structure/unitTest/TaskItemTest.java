package edu.dynamic.dynamiz.structure.unitTest;
import edu.dynamic.dynamiz.UI.*;
import edu.dynamic.dynamiz.controller.*;
import edu.dynamic.dynamiz.parser.*;
import edu.dynamic.dynamiz.storage.*;
import edu.dynamic.dynamiz.structure.*;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;
import edu.dynamic.dynamiz.structure.TaskItem;

/**
 * Defines the JUnit Test Case for TaskItem class.
 * @author zixian
 */
public class TaskItemTest {
    
    @Test
    public void testToFileString() {
	TaskItem task = new TaskItem("Return book", new MyDate(1, 10, 2014));
	assertEquals("Return book; 0; pending; --/--/---- --:--; 1/10/2014 --:--", task.toFileString());
	System.out.println(task.getFeedbackString());
	
	task.setDeadlineTime(10, 40);
	assertEquals("Return book; 0; pending; --/--/---- --:--; 1/10/2014 10:40", task.toFileString());
	System.out.println(task.getFeedbackString());
    }
    
    @Test
    public void testToString() {
	TaskItem task = new TaskItem("CS2106 homework", 4, new MyDateTime(26, 9, 2014, 23, 59));
	assertEquals("CS2106 homework; 4; pending; --/--/---- --:--; 26/9/2014 23:59", task.toFileString());
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
	assertEquals("27/9/2014", task.getDeadline().toString());
	
	task.setDeadlineDate(new MyDate(26, 9, 2014));
	assertEquals("26/9/2014", task.getDeadline().toString());
    }
    
    @Test
    public void testSetDeadlineTime() {
	TaskItem task = new TaskItem("CS2106 homework", new MyDate(26, 9, 2014));
	assertTrue("deadline is Date type", !(task.getDeadline() instanceof MyDateTime));
	
	task.setDeadlineTime(22, 60);
	assertTrue("deadline is Date type", !(task.getDeadline() instanceof MyDateTime));
	
	task.setDeadlineTime(23, 59);
	assertTrue("deadline is DateTime type", task.getDeadline() instanceof MyDateTime);
	
	task.setDeadlineTime(22, 60);
	assertEquals("CS2106 homework; 0; pending; --/--/---- --:--; 26/9/2014 23:59", task.toFileString());
    }
    
    @Test
    public void testSetStatus(){
	TaskItem task = new TaskItem("CS2106 homework", new MyDate(26, 9, 2014));
	assertEquals("initial status is 'pending'", TaskItem.STATUS_PENDING, task.getStatus());
	
	task.setStatus(TaskItem.STATUS_COMPLETED);
	assertEquals("new status is 'completed'", TaskItem.STATUS_COMPLETED, task.getStatus());
	System.out.println(task.getFeedbackString());
    }
    
    @Test
    public void testCopyConstructor(){
	TaskItem task = new TaskItem("Homework", 2, new MyDate(13, 10, 2014));
	TaskItem task2 = new TaskItem(task);
	assertFalse(task==task2);
	assertEquals(task.getId(), task2.getId());
	assertEquals(task, task2);
	task2.setPriority(5);
	assertFalse(task.getPriority()==task2.getPriority());
    }
}
