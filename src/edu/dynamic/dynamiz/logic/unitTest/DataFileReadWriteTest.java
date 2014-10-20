package edu.dynamic.dynamiz.logic.unitTest;

import edu.dynamic.dynamiz.structure.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.controller.DataFileReadWrite;

public class DataFileReadWriteTest {
    
    @Test
    public void testGetListFromFile() {
	ArrayList<ToDoItem> list = DataFileReadWrite.getListFromFile();
	assertEquals("list has 3 items", 3, list.size());
    }
    
    @Test
    public void testWriteToFile() {
	try{
	    ArrayList<ToDoItem> list = DataFileReadWrite.getListFromFile();
	    //FileHandler.writeListToFile(list, "output.txt");
	    DataFileReadWrite.writeItemsToFile(list);
	    list = DataFileReadWrite.getListFromFile();
	    assertEquals("list has 3 items", 3, list.size());
	} catch(IOException ioe){
	    
	}
    }
    
}
