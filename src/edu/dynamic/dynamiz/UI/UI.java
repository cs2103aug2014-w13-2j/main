package edu.dynamic.dynamiz.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.dynamic.dynamiz.UI.DisplayerInterface;
import edu.dynamic.dynamiz.controller.*;
import edu.dynamic.dynamiz.structure.Feedback;
/**
 * 
 * @author XYLau
 *
 */
public class UI extends JPanel implements ActionListener {
	protected JTextField inputScreen;
	protected JTextArea displayScreen;
	private final static String newline = "\n";
	public static Displayer disp = new Displayer();
	public static Controller cont = new Controller();
	public static Font font = new Font("Arial",Font.PLAIN,16);
	
	public UI() {
		super(new GridBagLayout());
		displayScreen = new JTextArea(20, 50);
		displayScreen.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(displayScreen);

		inputScreen = new JTextField(20);
		inputScreen.addActionListener(this);

		// Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		add(inputScreen, c);
		
		// Welcome message
		displayln(disp.displayWelcomeMessage());
		displayln(disp.displayPrompt(1));
	}
	
	public void run() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Screen();
			}
		});
	}

	/**
	 * Event Handler for each event, where event refers to the entry of a single
	 * command into the Screen interface
	 */
	public void actionPerformed(ActionEvent evt) {
		String text = inputScreen.getText();
		/*
		 * To be added once controller is completed (ZX) Feedback feedback =
		 * controller.executeCommand(text);
		 */

		display(disp.displayPrompt()); 
		displayln(text); 
		
		
		
		// TODO: Awaiting Nhan's exit feedback
		// if (feedback.getCommandType().equalsIgnoreCase("exit")) {
		if (text.equalsIgnoreCase("exit")) {
			System.exit(0);
		}
		
		Feedback feedback = cont.executeCommand(text);
		display(disp.displayFeedback(feedback));
		
		// Additional Feature: Retained Last-Entered Command
		inputScreen.selectAll();

		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	/**
	 * Displays a string onto the Screen with newline
	 * @param text
	 */
	public void displayln(String text) {
		displayScreen.setFont(font);
		displayScreen.setForeground(Color.BLUE);
		displayScreen.append(text + newline);
	}

	/**
	 * Displays a string onto the Screen without a newline
	 * 
	 * @param text
	 */
	public void display(String text) {
		displayScreen.setFont(font);
		displayScreen.setForeground(Color.BLUE);
		displayScreen.append(text);
	}

	public void displayRed(String text){
		
	}
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void Screen() {
		// Create and set up the window.
		JFrame frame = new JFrame("Dynamiz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new UI());

		displayScreen(frame);
		
	}

	/**
	 * Displays the screen
	 * 
	 * @param frame
	 */
	private static void displayScreen(JFrame frame) {
		frame.pack();
		frame.setVisible(true);
		
	}


	
	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Screen();
			}
		});
	}
}
