package edu.dynamic.dynamiz.logic.unitTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.logic.FileHandler;
import edu.dynamic.dynamiz.structure.TaskItem;

public class FileHandlerTest {
    
    @Test
    public void testGetListFromFile() {
	FileHandler testReadWrite = new FileHandler();
	try{
	ArrayList<TaskItem> list = testReadWrite.getListFromFile();
	assertEquals("list should have 1 element", 1, list.size());
	assertEquals("only element in list should have name Do CS2103T project", "Do CS2103T project", list.get(0).getDescription());
	} catch(IOException ioe){
	    System.out.println("Error");
	}
    }
    
    @Test
    public void testWriteToFile() {
	FileHandler testReadWrite = new FileHandler();
	try{
	    ArrayList<TaskItem> list = testReadWrite.getListFromFile();
	    list.get(0).setEndDate(30, 9, 2014);
	    testReadWrite.writeToFile(list);
	    list = testReadWrite.getListFromFile();
	    assertEquals("list has 1 item", 1, list.size());
	    assertEquals("item's endDate is 30/9/2014 --:--", "30/9/2014 --:--", list.get(0).getEndDate());
	} catch(IOException ioe){
	    System.out.println("Error");
	}
    }
    
}
