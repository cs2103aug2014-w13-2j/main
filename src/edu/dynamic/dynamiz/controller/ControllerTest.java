package edu.dynamic.dynamiz.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {
    
    @Test
    public void testExecuteCommand() throws Exception{
	Controller controller = new Controller();
	controller.executeCommand("add task");
    }
    
}
