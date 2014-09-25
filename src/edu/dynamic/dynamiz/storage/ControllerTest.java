package edu.dynamic.dynamiz.storage;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {
    
    @Test
    public void testExecuteCommand() {
	Controller controller = new Controller();
	controller.setup();
	controller.executeCommand("list");
    }
    
}
