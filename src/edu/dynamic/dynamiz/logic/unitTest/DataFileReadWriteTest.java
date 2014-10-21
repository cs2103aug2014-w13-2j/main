package edu.dynamic.dynamiz.logic.unitTest;

import edu.dynamic.dynamiz.structure.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import edu.dynamic.dynamiz.controller.DataFileReadWrite;

public class DataFileReadWriteTest {
    
    @Test
    public void testGetListFromFile() {
	ArrayList<ToDoItem> list = DataFileReadWrite.getListFromFile("todo.txt");
	assertEquals("list has 3 items", 3, list.size());
    }
    
    @Ignore
    public void testWriteToFile() {
	
    }
    
}
