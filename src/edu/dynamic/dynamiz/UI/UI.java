//@author A0114573J
package edu.dynamic.dynamiz.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import edu.dynamic.dynamiz.controller.*;
import edu.dynamic.dynamiz.structure.Feedback;

/**
 * Defines the UI for Dynamiz using Java Swing
 * 
 */

public class UI extends JPanel implements ActionListener {
	// Screen components
	protected JTextField inputScreen;
	protected JTextPane displayScreen;

	// Establish relations between UI & Display + Controller
	public static DisplayFormatter disp = new DisplayFormatter();
	public static Controller cont = new Controller();

	// Formatting Constants
	private StyledDocument doc;
	private static Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	private static Font fontInput = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	
	
	private String DIVIDER = "================================================================================================";
	private final static String NEWLINE = "\n";
//	private final static String HELP_PROMPT = "Please enter 'help' for more information about the available options";
	
	// Styling Constants
	private SimpleAttributeSet setStyleWhite;
	private SimpleAttributeSet setStyleDefault;
	private SimpleAttributeSet setStyleGreen;
	private SimpleAttributeSet setStyleOrange;
	private SimpleAttributeSet setStyleMagenta;
	private SimpleAttributeSet setStyleRed;
	private SimpleAttributeSet setStyleBlue;
	private SimpleAttributeSet setStyleCyan;
	private SimpleAttributeSet setStyleYellow;

	// Logger: Creating Logger
	private final static Logger LoggerUI = Logger.getLogger(UI.class.getName());

	public UI() {
		super(new GridBagLayout());

		// Logger: Set Level (Logger alert above Info level = Severe & Warning)
		LoggerUI.setLevel(Level.INFO);

		// Create Command Display - Screen
		displayScreen = new JTextPane();
		displayScreen.setEditable(false);
		displayScreen.setFont(font);
		displayScreen.setBackground(Color.BLACK);
		displayScreen.setForeground(Color.WHITE);

		// Set tab size
		TabStop[] tabs = new TabStop[1];
		tabs[0] = new TabStop(60, TabStop.ALIGN_RIGHT, TabStop.LEAD_NONE);
		TabSet tabset = new TabSet(tabs);

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.TabSet, tabset);
		displayScreen.setParagraphAttributes(aset, false);

		// Create Command Display - Scroll
		JScrollPane scrollPane = new JScrollPane(displayScreen);

		// Define size of Command Display - Screen
		scrollPane.getViewport().setPreferredSize(new Dimension(1080, 600));

		// / Create Command Input
		inputScreen = new JTextField();
		inputScreen.addActionListener(this);
		inputScreen.setForeground(Color.BLUE);
		inputScreen.setFont(fontInput);

		// Add Command Display - Scroll and Command Input into Panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		add(inputScreen, c);

		// Styling for Command Display - Screen
		doc = displayScreen.getStyledDocument();

		style();

		// Display: Welcome message
		try {
			doc.insertString(0, disp.displayWelcomeMessage() + NEWLINE,
					setStyleWhite);
			doc.insertString(doc.getLength(), disp.displayPrompt(1) + NEWLINE,
					setStyleWhite);
	//		doc.insertString(doc.getLength(), HELP_PROMPT + NEWLINE, setStyleWhite);
		} catch (Exception e) {
			System.out.println(e);
		}

		// Logging: Creation
		LoggerUI.info("UI Created");
	}

	/**
	 * Event Handler for each event, where event refers to the entry of a single
	 * command into the Screen interface
	 */
	public void actionPerformed(ActionEvent evt) {
		String input = inputScreen.getText();
		// Logging: Command Entered
		LoggerUI.info("Command Entered");

		try {

			// Command: Exit
			if (CommandType.fromString(input) == CommandType.EXIT) {
				LoggerUI.info("Exit Dynamiz");
				System.exit(0);
			}

			// Command: Flush
			if (CommandType.fromString(input) == CommandType.FLUSH) {

				// clear document screen
				doc.remove(0, doc.getLength());
				commandPromptDisplay(input);

				// Feedback
				doc.insertString(doc.getLength(), DIVIDER + NEWLINE, setStyleDefault);
				doc.insertString(doc.getLength(),
						"Cleared screen successfully!" + NEWLINE, setStyleDefault);

				// Logger: Flush screen
				LoggerUI.info("Flush Screen");
			} else {

				commandPromptDisplay(input);

				// Command Feedback
				Feedback feedback = cont.executeCommand(input);
				ArrayList<StrIntPair> returnResult = disp
						.displayFeedback(feedback);
				assert (returnResult != null);

				// Feedback Display
				doc.insertString(doc.getLength(), DIVIDER + NEWLINE, setStyleDefault);

				for (int i = 0; i < returnResult.size(); i++) {
					printWithStyle(returnResult, i);
				}

				// Logging: Return Command Feedback
				LoggerUI.info("Return Command Feedback");

			}
		} catch (Exception e) {
			// Logging: Exception from actionPerformed
			LoggerUI.warning("Exception @ actionPerformed()");
			System.out.println(e);
		}

		// Additional Feature: Retained Last-Entered Command
		inputScreen.selectAll();
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	private void printWithStyle(ArrayList<StrIntPair> returnResult, int i)
			throws BadLocationException {
		switch (returnResult.get(i).getInt()) {
		case 1:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleGreen);
			break;
		case 2:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleOrange);
			break;
		case 4:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleMagenta);
			break;
		case 8:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleRed);
			break;
		// Display Color (Status)
		// ----------------------------------------------------------
		case 10:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleBlue);
			break;
		case 11:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleCyan);
			break;

		default:
			doc.insertString(doc.getLength(), returnResult.get(i)
					.getString(), setStyleDefault);
			break;
		}
	}

	private void commandPromptDisplay(String input) throws BadLocationException {
		// Command Prompt Display
		doc.insertString(doc.getLength(), DIVIDER + NEWLINE, setStyleDefault);
		doc.insertString(doc.getLength(), disp.displayPrompt(), setStyleYellow);
		doc.insertString(doc.getLength(), input + NEWLINE, setStyleYellow);

	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * Creates the GUI and displays it
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
	 * Displays the Frame
	 * 
	 * @param frame
	 */
	private static void displayScreen(JFrame frame) {
		frame.pack();
		frame.setVisible(true);
		// Logging: Frame = visible
		LoggerUI.info("displayScreen visible");

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

	public void run() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Screen();
			}
		});
	}

	/**
	 * Defines the stylesheet for displaying (Hightlight & Priority)
	 */
	private void style() {
		setStyleDefault = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleDefault, Color.WHITE);

		setStyleWhite = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleWhite, Color.WHITE);

		setStyleGreen = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleGreen, Color.GREEN);

		setStyleOrange = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleOrange, Color.ORANGE);

		setStyleMagenta = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleMagenta, Color.MAGENTA);

		setStyleRed = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleRed, Color.RED);

		setStyleBlue = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleBlue, Color.BLUE);

		setStyleCyan = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleCyan, Color.CYAN);

		setStyleYellow = new SimpleAttributeSet();
		StyleConstants.setForeground(setStyleYellow, Color.YELLOW);
	}
}
