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
 * This is the UI class that will display the user interface to the user. It
 * also receives user's input command to the Controller through the UI subclass
 * Displayer and StyleUI before outputting the feedback onto the UI for the user to see.
 * 
 */

public class UI extends JPanel implements ActionListener {
	/** UI screen components */
	protected JTextField inputScreen;
	protected JTextPane displayScreen;

	/** Establish relations between UI, Displayer, Controller & StyleUI */
	public static DisplayFormatter disp = new DisplayFormatter();
	public static Controller cont = new Controller();
	private StyleUI style = new StyleUI();

	/** UI display components */
	private StyledDocument doc;
	private static Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	private static Font fontInput = new Font(Font.MONOSPACED, Font.PLAIN, 18);

	/** Constants for basic sectioning of display in the UI */
	private String DIVIDER = "================================================================================================";
	private final static String NEWLINE = "\n";

	/** A logger instance for this class */
	private final static Logger LoggerUI = Logger.getLogger(UI.class.getName());

	public UI() {
		super(new GridBagLayout());

		LoggerUI.setLevel(Level.INFO);

		intialiseUIScreen();

		// Display: Welcome message
		try {
			displayWelcomeMessage();
		} catch (Exception e) {
			System.out.println(e);
		}

		LoggerUI.info("UI Created");
	}

	/**
	 * Event Handler for each event, where event refers to the entry of a single
	 * command into the Screen interface
	 */
	public void actionPerformed(ActionEvent evt) {
		String input = inputScreen.getText();
		LoggerUI.info("Command Entered");

		try {
			if (CommandType.fromString(input) == CommandType.EXIT) {
				LoggerUI.info("Exit Dynamiz");
				System.exit(0);
			}

			if (CommandType.fromString(input) == CommandType.FLUSH) {
				flushUI(input);
				LoggerUI.info("Flush Screen");
			} else {

				displayCommandPrompt(input);

				Feedback feedback = cont.executeCommand(input);
				ArrayList<StrIntPair> returnResult = disp
						.displayFeedback(feedback);
				assert (returnResult != null);

				/** Displays Feedback */
				doc.insertString(doc.getLength(), DIVIDER + NEWLINE,
						style.getStyleDefault());

				for (int i = 0; i < returnResult.size(); i++) {
					printWithStyle(returnResult, i);
				}

				LoggerUI.info("Return Command Feedback");

			}
		} catch (Exception e) {
			System.out.println(e);
			LoggerUI.warning("Exception @ actionPerformed()");
		}

		retainLastEnteredCommand();
	}

	/**
	 * Retains last entered command from inputScreen(JTextField). 
	 * The command is highlighted for easy editing & re-entering.
	 */
	private void retainLastEnteredCommand() {
		inputScreen.selectAll();
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	/**
	 * Flushes the inputScreen(JTextField) when the user input comes in any variations of CommandType.FLUSH
	 * 
	 * @param input
	 * @throws BadLocationException
	 */
	private void flushUI(String input) throws BadLocationException {
		// clear document screen
		doc.remove(0, doc.getLength());
		displayCommandPrompt(input);

		// Feedback
		doc.insertString(doc.getLength(), DIVIDER + NEWLINE,
				style.getStyleDefault());
		doc.insertString(doc.getLength(), "Cleared screen successfully!"
				+ NEWLINE, style.getStyleDefault());
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
	 * Intialises the UI JTextPane. 
	 * JTextPane creation, specification of Tab Settings, JTextField creation, scrollPane constraints and setting up StyledDocument to format output styles.
	 */
	private void intialiseUIScreen() {
		displayScreen = new JTextPane();
		displayScreen.setEditable(false);
		displayScreen.setFont(font);
		displayScreen.setBackground(Color.BLACK);
		displayScreen.setForeground(Color.WHITE);

		setTabSettings();

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
	}

	private void setTabSettings() {
		// Set tab size
		TabStop[] tabs = new TabStop[4];
		tabs[0] = new TabStop(60, TabStop.ALIGN_RIGHT, TabStop.LEAD_NONE);
		tabs[1] = new TabStop(100, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
		tabs[2] = new TabStop(200, TabStop.ALIGN_CENTER, TabStop.LEAD_NONE);
		tabs[3] = new TabStop(300, TabStop.ALIGN_DECIMAL, TabStop.LEAD_NONE);
		TabSet tabset = new TabSet(tabs);

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.TabSet, tabset);
		displayScreen.setParagraphAttributes(aset, false);
	}

	private void printWithStyle(ArrayList<StrIntPair> returnResult, int i)
			throws BadLocationException {
		doc.insertString(doc.getLength(), returnResult.get(i).getString(),
				style.getStyleType(returnResult.get(i).getInt()));
	}

	// ---------------------------------------------------------------------------------
	private void displayWelcomeMessage() throws BadLocationException {
		doc.insertString(0, disp.displayWelcomeMessage() + NEWLINE,
				style.getStyleWhite());
		doc.insertString(doc.getLength(), disp.displayPrompt(1) + NEWLINE,
				style.getStyleWhite());
	}

	private void displayCommandPrompt(String input) throws BadLocationException {
		// Command Prompt Display
		doc.insertString(doc.getLength(), DIVIDER + NEWLINE,
				style.getStyleDefault());
		doc.insertString(doc.getLength(), disp.displayPrompt(),
				style.getStyleYellow());
		doc.insertString(doc.getLength(), input + NEWLINE,
				style.getStyleYellow());

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

}
