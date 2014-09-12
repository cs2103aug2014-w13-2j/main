package main;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

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
    
    @Ignore
    public void testWriteToFile() {
	fail("Not yet implemented");
    }
    
}
