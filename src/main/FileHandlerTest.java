package main;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileHandlerTest {
    
    @Test
    public void testGetListFromFile() {
	ArrayList<ToDoItem> list = FileHandler.getListFromFile();
	assertEquals("list has 3 items", 3, list.size());
    }
    
    @Test
    public void testWriteToFile() {
	try{
	    ArrayList<ToDoItem> list = FileHandler.getListFromFile();
	    //FileHandler.writeListToFile(list, "output.txt");
	    FileHandler.writeListToFile(list);
	    list = FileHandler.getListFromFile();
	    assertEquals("list has 3 items", 3, list.size());
	} catch(IOException ioe){
	    
	}
    }
    
}
