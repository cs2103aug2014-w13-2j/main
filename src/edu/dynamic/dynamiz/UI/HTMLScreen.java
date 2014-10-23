package edu.dynamic.dynamiz.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import edu.dynamic.dynamiz.UI.DisplayerInterface;
import edu.dynamic.dynamiz.controller.*;
import edu.dynamic.dynamiz.structure.Feedback;

/**
 * 
 * @author XYLau
 *
 */
public class HTMLScreen extends JPanel implements ActionListener {
	protected JTextField inputScreen;
	protected JEditorPane displayScreen;
	protected HTMLEditorKit editor = new HTMLEditorKit();
	public static DisplayFormatter disp = new DisplayFormatter();

	public HTMLScreen() {
		super(new GridBagLayout());

		// Initialise Logger to alert above INFO level (Severe & Warning)

		displayScreen = new JEditorPane();
		displayScreen.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(displayScreen);
		displayScreen.setEditorKit(editor);

		// CSS Styles for HTML String
		// -----------------------------------------
		StyleSheet style = editor.getStyleSheet();
		style.addRule("body {font : 10px arial; color : black; }");
		style.addRule(".WARNING {font : 10px arial; color : red; }");
		style.addRule(".PRIORITY_HEADER {font : 10px arial; color : black; }");
		style.addRule(".PRIORITY_URGENT {font : 10px arial; color : purple; }");
		style.addRule(".PRIORITY_HIGH {font : 10px arial; color : orange; }");
		style.addRule(".PRIORITY_MEDIUM {font : 10px arial; color : green; }");
		style.addRule(".PRIORITY_LOW {font : 10px arial; color : grey; }");
		style.addRule(".PRIORITY_NONE {font : 10px arial; color : blue; }");

		// ----------------------------------------
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

		/*
		 * // Welcome message displayln(disp.displayWelcomeMessage());
		 * displayln(disp.displayPrompt(1));
		 */
		String welcome = "<html>\n" + "<body>\n"
				+ "<h1>Welcome to Dynamiz!</h1>\n"
				+ "<p> Enter command</py>"
				+ "<div class = WARNING><p>RED</p></div>\n"
				+ "<div class = PRIORITY_HEADER><p>BLACK</p></div>\n"
				+ "<div class = PRIORITY_URGENT><p>PURPLE</p></div>\n"
				+ "<div class = PRIORITY_HIGH><p>ORANGE</p></div>\n"
				+ "<div class = PRIORITY_MEDIUM><p>GREEN</p></div>\n"
				+ "<div class = PRIORITY_LOW><p>GREY</p></div>\n"
				+ "<div class = PRIORITY_NONE><p>BLUE</p></div>\n"
				+ "</body>\n";
		String command = "<html>\n" + "<body>"
				+ "<p>command</p>"
				+ "</body>\n";
		Document doc = editor.createDefaultDocument();
		displayScreen.setDocument(doc);
		display(welcome);
		
	}

	public void run() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HTMLScreen();
			}
		});
	}

	/**
	 * Event Handler for each event, where event refers to the entry of a single
	 * command into the Screen interface
	 */
	public void actionPerformed(ActionEvent evt) {
		String text = inputScreen.getText();

		display(disp.displayPrompt());
		display(text);

		if (text.equalsIgnoreCase("exit")) {
			System.exit(0);
		}

		// Feedback feedback = cont.executeCommand(text);
		String returnResult = disp.displayFeedback(feedback);
		assert (returnResult != null);
		display(returnResult);

		// Additional Feature: Retained Last-Entered Command
		inputScreen.selectAll();

		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	/*
	 * // Displays a string onto the Screen with newline public void
	 * displayln(String text) { displayColorSetDefault(); inputColorSet();
	 * displayScreen.setText(text + newline); LoggerUI.info("Display " + text);
	 * }
	 */
	/**
	 * Displays a html string onto the UI
	 * 
	 * @param text
	 */
	public void display(String text) {
		displayScreen.setText(text);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void HTMLScreen() {
		// Create and set up the window.
		JFrame frame = new JFrame("Dynamiz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new HTMLScreen());

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
				HTMLScreen();
			}
		});
	}
}
