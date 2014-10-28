package edu.dynamic.dynamiz.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	public static DisplayFormatter disp = new DisplayFormatter();
	public static Controller cont = new Controller();
	public static Font font = new Font("Arial", Font.PLAIN, 12);

	private final static Logger LoggerUI = Logger.getLogger(UI.class.getName());

	public UI() {
		super(new GridBagLayout());

		// Initialise Logger to alert above INFO level (Severe & Warning)
		LoggerUI.setLevel(Level.INFO);

		displayScreen = new JTextArea(20, 50);
		displayScreen.setEditable(false);
		displayScreen.setFont(font);
		displayScreen.setForeground(Color.BLACK);

		JScrollPane scrollPane = new JScrollPane(displayScreen);

		inputScreen = new JTextField(20);
		inputScreen.addActionListener(this);
		inputScreen.setForeground(Color.BLUE); 
		
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
		displayScreen.append(disp.displayWelcomeMessage()+newline);
		displayScreen.append(disp.displayPrompt(1)+newline);

		LoggerUI.info("UI Created");
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
		String input = inputScreen.getText();

		displayScreen.append(disp.displayPrompt());
		displayScreen.append(input + newline);
		
		if (input.equalsIgnoreCase("exit")) {
			LoggerUI.info("Exit Dynamiz");
			System.exit(0);
		}

		Feedback feedback = cont.executeCommand(input);
		String returnResult = disp.displayFeedback(feedback);
		assert (returnResult != null);
		
		displayScreen.append("------------------------------------------------------------------------------------" + newline);
		displayScreen.append(returnResult+newline);
		
		displayScreen.append("Execution complete."+ newline);
		displayScreen.append("------------------------------------------------------------------------------------" + newline);
		
		// Additional Feature: Retained Last-Entered Command
		inputScreen.selectAll();

		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	//-------------------------------------------------------------------------------------------------

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


	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Screen();
			}
		});
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

}
