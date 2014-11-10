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
 * Displayer and StyleUI before outputting the feedback onto the UI for the user
 * to see.
 */

public class UI extends JPanel implements ActionListener {
	/** UI screen components */
	protected JTextField inputScreen;
	protected JTextPane displayScreen;

	/** Establish relations between UI, Displayer, Controller & StyleUI */
	public static DisplayerFormatter displayer = new DisplayerFormatter();
	public static Controller controller = new Controller();
	private StyleUI style = new StyleUI();


	/** UI display components */
	private StyledDocument document;
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

				Feedback feedback = controller.executeCommand(input);
				ArrayList<StrIntPair> returnResult = displayer
						.displayFeedback(feedback);
				assert (returnResult != null);

				/** Displays Feedback */
				document.insertString(document.getLength(), DIVIDER + NEWLINE,
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
	 * Retains last entered command from inputScreen(JTextField). The command is
	 * highlighted for easy editing & re-entering.
	 */
	private void retainLastEnteredCommand() {
		inputScreen.selectAll();
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
	}

	/**
	 * Flushes the inputScreen(JTextField) when the user input comes in any
	 * variations of CommandType.FLUSH
	 * 
	 * @param input
	 * @throws BadLocationException
	 */
	private void flushUI(String input) throws BadLocationException {
		// clear document screen
		document.remove(0, document.getLength());
		displayCommandPrompt(input);

		// Feedback
		document.insertString(document.getLength(), DIVIDER + NEWLINE,
				style.getStyleDefault());
		document.insertString(document.getLength(),
				"Cleared screen successfully!" + NEWLINE,
				style.getStyleDefault());
	}

	public static void main(String[] args) {
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
	 * Intialises the UI JTextPane. JTextPane creation, specification of Tab
	 * Settings, JTextField creation, scrollPane constraints and setting up
	 * StyledDocument to format output styles.
	 */
	private void intialiseUIScreen() {
		displayScreen = new JTextPane();
		displayScreen.setEditable(false);
		displayScreen.setFont(font);
		displayScreen.setBackground(Color.BLACK);
		displayScreen.setForeground(Color.WHITE);

		setTabSettings();

		JScrollPane scrollPane = new JScrollPane(displayScreen);
		scrollPane.getViewport().setPreferredSize(new Dimension(1080, 600));

		inputScreen = new JTextField();
		inputScreen.addActionListener(this);
		inputScreen.setForeground(Color.BLUE);
		inputScreen.setFont(fontInput);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		add(scrollPane, constraints);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(inputScreen, constraints);

		document = displayScreen.getStyledDocument();
	}

	/**
	 * Defines the tab size specifications for JTextPane
	 */
	private void setTabSettings() {
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

	/**
	 * References StyleUI to return the appropriate getStyleType based on the
	 * Priority Value (OptionType) or the Status Settings
	 * (StyleUI.STATUS_PENDING or StyleUI.STATUS_COMPLETED).
	 * 
	 * @param returnResult
	 * @param styleValue
	 * @throws BadLocationException
	 */
	private void printWithStyle(ArrayList<StrIntPair> returnResult,
			int styleValue) throws BadLocationException {
		document.insertString(document.getLength(), returnResult
				.get(styleValue).getString(), style.getStyleType(returnResult
				.get(styleValue).getInt()));
	}

	/**
	 * Displays the welcome message onto displayScreen (JTextPane) in the following manner:
	 * 
	 * Welcome to Dynamiz!
	 * Type 'help' in the space below for more information!
	 * Please enter command in the space below:
	 * 
	 * @throws BadLocationException
	 */
	private void displayWelcomeMessage() throws BadLocationException {
		document.insertString(0, displayer.displayWelcomeMessage() + NEWLINE,
				style.getStyleWhite());
		document.insertString(document.getLength(), displayer.displayPrompt(1)
				+ NEWLINE, style.getStyleWhite());
	}

	/**
	 * Displays the command prompt onto displayScreen (JTextPane) in the following manner:
	 * 
	 * ================================================================================================
	 * command: <user-entered command>
	 * 
	 * @param input
	 * @throws BadLocationException
	 */
	private void displayCommandPrompt(String input) throws BadLocationException {
		document.insertString(document.getLength(), DIVIDER + NEWLINE,
				style.getStyleDefault());
		document.insertString(document.getLength(), displayer.displayPrompt(),
				style.getStyleYellow());
		document.insertString(document.getLength(), input + NEWLINE,
				style.getStyleYellow());

	}

	/**
	 * Creates the Frame (JFrame) for the UI
	 */
	public static void Screen() {
		JFrame frame = new JFrame("Dynamiz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new UI());

		frame.pack();
		frame.setVisible(true);
		
		LoggerUI.info("displayScreen visible");
	}

}
