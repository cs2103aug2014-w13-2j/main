package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskItemTest {
    
    @Test
    public void testToFileString() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testToString() {
	TaskItem task = new TaskItem("CS2106 homework", 4, new DateTime(26, 9, 2014, 23, 59));
	assertEquals("A1 CS2106 homework 4 pending --/--/---- --:-- 26/9/2014 23:59", task.toString());
    }
    
    @Test
    public void testSetDeadline() {
	fail("Not yet implemented");
    }
    
    @Test
    public void testSetDeadlineDate() {
	fail("Not yet implemented");
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
	assertEquals("A2 CS2106 homework 0 pending --/--/---- --:-- 26/9/2014 23:59", task.toString());
    }
    
    @Test
    public void testSetStatus(){
	TaskItem task = new TaskItem("CS2106 homework", new Date(26, 9, 2014));
	assertEquals("initial status is 'pending'", TaskItem.DEFAULT_STATUS, task.getStatus());
	
	task.setStatus(TaskItem.STATUS_INPROGRESS);
	assertEquals("new status is 'in progress'", TaskItem.STATUS_INPROGRESS, task.getStatus());
    }
}
