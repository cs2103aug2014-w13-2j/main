package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskItemTest {
    
    @Test
    public void testToFileString() {
	TaskItem task = new TaskItem("Return book", new Date(1, 10, 2014));
	assertEquals("Return book; 0; pending; --/--/---- --:--; 1/10/2014 --:--", task.toFileString());
	
	task.setDeadlineTime(10, 40);
	assertEquals("Return book; 0; pending; --/--/---- --:--; 1/10/2014 10:40", task.toFileString());
    }
    
    @Test
    public void testToString() {
	TaskItem task = new TaskItem("CS2106 homework", 4, new DateTime(26, 9, 2014, 23, 59));
	assertEquals("A1 CS2106 homework 4 pending --/--/---- --:-- 26/9/2014 23:59", task.toString());
    }
    
    @Test
    public void testSetDeadline() {
	TaskItem task = new TaskItem("CS2106 homework", new Date(24, 9, 2014));
	assertEquals("24/9/2014", task.getDeadline().toString());
	
	task.setDeadline(new DateTime(26, 9, 2014, 23, 59));
	assertEquals("26/9/2014 23:59", task.getDeadline().toString());
    }
    
    @Test
    public void testSetDeadlineDate() {
	TaskItem task = new TaskItem("CS2106 homework", new Date(27, 9, 2014));
	assertEquals("27/9/2014", task.getDeadline().toString());
	
	task.setDeadlineDate(new Date(26, 9, 2014));
	assertEquals("26/9/2014", task.getDeadline().toString());
    }
    
    @Test
    public void testSetDeadlineTime() {
	TaskItem task = new TaskItem("CS2106 homework", new Date(26, 9, 2014));
	assertTrue("deadline is Date type", !(task.getDeadline() instanceof DateTime));
	
	task.setDeadlineTime(22, 60);
	assertTrue("deadline is Date type", !(task.getDeadline() instanceof DateTime));
	
	task.setDeadlineTime(23, 59);
	assertTrue("deadline is DateTime type", task.getDeadline() instanceof DateTime);
	
	task.setDeadlineTime(22, 60);
	assertEquals("A3 CS2106 homework 0 pending --/--/---- --:-- 26/9/2014 23:59", task.toString());
    }
    
    @Test
    public void testSetStatus(){
	TaskItem task = new TaskItem("CS2106 homework", new Date(26, 9, 2014));
	assertEquals("initial status is 'pending'", TaskItem.DEFAULT_STATUS, task.getStatus());
	
	task.setStatus(TaskItem.STATUS_INPROGRESS);
	assertEquals("new status is 'in progress'", TaskItem.STATUS_INPROGRESS, task.getStatus());
    }
}
