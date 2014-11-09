//@author A0114573J
package main;

import java.util.logging.LogManager;

/**
 * Main Driver Program for Dynamiz. Execute Dynmaiz from here.
 */

import edu.dynamic.dynamiz.UI.*;

public class Dynamiz {
	public static void main(String[] args) {
		// Turn of Logger
		LogManager.getLogManager().reset();
		UI ui = new UI();
		ui.run();
	}
}
