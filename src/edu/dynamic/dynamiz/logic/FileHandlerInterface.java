package edu.dynamic.dynamiz.logic;

import java.io.IOException;
import java.util.ArrayList;

import edu.dynamic.dynamiz.structure.ToDoItem;

public interface FileHandlerInterface {
	 public ArrayList<ToDoItem> getListFromFile();
	 public  ArrayList<ToDoItem> getListFromFile(String filename);
	 public void writeListToFile(ArrayList<ToDoItem> list) throws IOException;
	 public void writeListToFile(ArrayList<ToDoItem> list, String filename) throws IOException;
	 
}
