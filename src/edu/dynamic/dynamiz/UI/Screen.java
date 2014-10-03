package edu.dynamic.dynamiz.UI;

/**
 * @author XYLau
 * 
 * Establish Screen for GUI
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener {
	protected JTextField inputScreen;
	protected JTextArea displayScreen;
	private final static String newline = "\n";

	public Screen() {
		super(new GridBagLayout());
		displayScreen = new JTextArea(40, 100);
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
	}

	/**
	 * Event Handler for each event, where event refers to the entry of a single
	 * command into the Screen interface
	 */
	public void actionPerformed(ActionEvent evt) {
		String text = inputScreen.getText();

		displayln(text);
		inputScreen.selectAll();

		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	/**
	 * Displays a string onto the Screen with newline
	 * 
	 * @param text
	 */
	public void displayln(String text) {
		displayScreen.append(text + newline);
	}

	/**
	 * Displays a string onto the Screen without a newline
	 * 
	 * @param text
	 */
	public void display(String text) {
		displayScreen.append(text);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void Screen() {
		// Create and set up the window.
		JFrame frame = new JFrame("Dynamiz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new Screen());

		// Display the window.
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