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

import edu.dynamic.dynamiz.UI.DisplayerInterface;
import edu.dynamic.dynamiz.controller.*;
import edu.dynamic.dynamiz.structure.Feedback;

/**
 * Defines the UI for Dynamiz using Java Swing
 * 
 * @author XYLau
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
	private StyledDocument newdoc;
	private static Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);
	private String divider = "================================================================================================";
	private final static String newline = "\n";
	private SimpleAttributeSet BoldBlack;
	private SimpleAttributeSet Default;
	private SimpleAttributeSet BoldGreen;
	private SimpleAttributeSet BoldOrange;
	private SimpleAttributeSet BoldMagenta;
	private SimpleAttributeSet BoldRed;

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
		scrollPane.getViewport().setPreferredSize(new Dimension(870, 600));

		// / Create Command Input
		inputScreen = new JTextField();
		inputScreen.addActionListener(this);
		inputScreen.setForeground(Color.BLUE);
		inputScreen.setFont(font);

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
			doc.insertString(0, disp.displayWelcomeMessage() + newline, BoldBlack);
			doc.insertString(doc.getLength(), disp.displayPrompt(1) + newline,
					BoldBlack);
		} catch (Exception e) {
			System.out.println(e);
		}

		// Logging: Creation
		LoggerUI.info("UI Created");
	}

	/**
	 * Defines the stylesheet for displaying (Hightlight & Priority)
	 */
	private void style() {
		BoldBlack = new SimpleAttributeSet();
		StyleConstants.setForeground(BoldBlack, Color.WHITE);
		StyleConstants.setBold(BoldBlack, true);

		Default = new SimpleAttributeSet();
		StyleConstants.setForeground(Default, Color.WHITE);
		// StyleConstants.setBold(Default, true);

		BoldGreen = new SimpleAttributeSet();
		StyleConstants.setForeground(BoldGreen, Color.GREEN);
		StyleConstants.setBold(BoldGreen, true);

		BoldOrange = new SimpleAttributeSet();
		StyleConstants.setForeground(BoldOrange, Color.ORANGE);
		StyleConstants.setBold(BoldOrange, true);

		BoldMagenta = new SimpleAttributeSet();
		StyleConstants.setForeground(BoldMagenta, Color.MAGENTA);
		StyleConstants.setBold(BoldMagenta, true);

		BoldRed = new SimpleAttributeSet();
		StyleConstants.setForeground(BoldRed, Color.RED);
		StyleConstants.setBold(BoldRed, true);
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
				doc.insertString(doc.getLength(), divider + newline, Default);
				doc.insertString(doc.getLength(),
						"Cleared screen successfully!" + newline, Default);

				LoggerUI.info("Flush Screen");
			} else {

				commandPromptDisplay(input);

				// Command Feedback
				Feedback feedback = cont.executeCommand(input);
				ArrayList<StrIntPair> returnResult = disp
						.displayFeedback(feedback);
				assert (returnResult != null);

				// Feedback Display
				doc.insertString(doc.getLength(), divider + newline, Default);
				// previously: doc.insertString(doc.getLength(), returnResult +
				// newline, null);

				// Display feedback
				for (int i = 0; i < returnResult.size(); i++) {
					switch (returnResult.get(i).getInt()) {
					case 1:
						doc.insertString(doc.getLength(), returnResult.get(i)
								.getString(), BoldGreen);
						break;
					case 2:
						doc.insertString(doc.getLength(), returnResult.get(i)
								.getString(), BoldOrange);
						break;
					case 4:
						doc.insertString(doc.getLength(), returnResult.get(i)
								.getString(), BoldMagenta);
						break;
					case 8:
						doc.insertString(doc.getLength(), returnResult.get(i)
								.getString(), BoldRed);
						break;

					default:
						doc.insertString(doc.getLength(), returnResult.get(i)
								.getString(), Default);
						break;
					}
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

	private void commandPromptDisplay(String input) throws BadLocationException {
		// Command Prompt Display
		doc.insertString(doc.getLength(), divider + newline, Default);
		doc.insertString(doc.getLength(), disp.displayPrompt(), BoldBlack);
		doc.insertString(doc.getLength(), input + newline, BoldBlack);

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

}
