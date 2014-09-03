/**
 * 
 */
package main;

import java.util.ArrayList;

/**
 * @author zixian
 *
 */

/*
 * Defines the object class of the main program.
 */
public class Dynamiz {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*TaskItem item = new TaskItem("Sample task");
		item.setEndDate(6, 9, 2014);
		System.out.println(item);*/
	    	FileHandler fileHandler = new FileHandler();
	    	ArrayList<TaskItem> list = fileHandler.getListFromFile();
	}

}
