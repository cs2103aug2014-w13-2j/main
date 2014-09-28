package edu.dynamic.dynamiz.storage;

import java.io.IOException;
import java.util.ArrayList;

import edu.dynamic.dynamiz.logic.FileHandler;
import edu.dynamic.dynamiz.structure.ToDoItem;

public class StorageStub extends Storage {
    private ArrayList<ToDoItem> mainList;
    
    public void initialise(){
	mainList = FileHandler.getListFromFile();
    }
    
    public boolean addItem(ToDoItem item){
	boolean success = mainList.add(item);
	try {
	    FileHandler.writeListToFile(mainList, "temp_output.txt");
	} catch (IOException e) {
	    success =  false;
	}
	return success;
    }
    
    public ArrayList<ToDoItem> getList(){
	return mainList;
    }
}
