package edu.dynamic.dynamiz.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.dynamic.dynamiz.UI.DisplayerInterface;
import edu.dynamic.dynamiz.controller.*;
import edu.dynamic.dynamiz.structure.Feedback;

/**
 * Defines the UI for Dynamiz using Java Swing
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
	private static Font font = new Font("Arial", Font.PLAIN, 18);
	private String divider = "------------------------------------------------------------------------------------";
	private final static String newline = "\n";
	private SimpleAttributeSet Header;
	private SimpleAttributeSet Highlight;
	private SimpleAttributeSet PriorityNone;
	private SimpleAttributeSet PriorityLow;
	private SimpleAttributeSet PriorityMedium;
	private SimpleAttributeSet PriorityHigh;
	private SimpleAttributeSet PriorityUrgent;

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

		// Create Command Display - Scroll
		JScrollPane scrollPane = new JScrollPane(displayScreen);

		// Define size of Command Display - Screen
		scrollPane.getViewport().setPreferredSize(new Dimension(750, 500));

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
			doc.insertString(0, disp.displayWelcomeMessage() + newline, null);
			doc.insertString(doc.getLength(), disp.displayPrompt(1) + newline,
					null);
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
		Header = new SimpleAttributeSet();
		StyleConstants.setForeground(Header, Color.BLACK);
		StyleConstants.setBold(Header, true);

		Highlight = new SimpleAttributeSet();
		StyleConstants.setForeground(Highlight, Color.BLACK);
		StyleConstants.setBold(Highlight, true);

		PriorityNone = new SimpleAttributeSet();
		StyleConstants.setForeground(PriorityNone, Color.BLUE);
		StyleConstants.setBold(PriorityNone, true);                                                                                                                                                                                                                      

		PriorityLow = new SimpleAttributeSet();
		StyleConstants.setForeground(PriorityLow, Color.GREEN);
		StyleConstants.setBold(PriorityLow, true);

		PriorityMedium = new SimpleAttributeSet();
		StyleConstants.setForeground(PriorityMedium, Color.YELLOW);
		StyleConstants.setBold(PriorityMedium, true);

		PriorityHigh = new SimpleAttributeSet();
		StyleConstants.setForeground(PriorityHigh, Color.ORANGE);
		StyleConstants.setBold(PriorityHigh, true);

		PriorityUrgent = new SimpleAttributeSet();
		StyleConstants.setForeground(PriorityUrgent, Color.RED);
		StyleConstants.setBold(PriorityUrgent, true);
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
			// Command Prompt Display
			doc.insertString(doc.getLength(), divider + newline, null);
			doc.insertString(doc.getLength(), disp.displayPrompt(), Highlight);
			doc.insertString(doc.getLength(), input + newline, Highlight);

			// Command: Exit
			if (input.equalsIgnoreCase("exit")) {
				LoggerUI.info("Exit Dynamiz");
				System.exit(0);
			}
			
			// Command: Clear (Flush screen)
			if (input.equalsIgnoreCase("clear")){
				LoggerUI.info("Exit Dynamiz");
				
			}
			// Command Feedback
			Feedback feedback = cont.executeCommand(input);
			String returnResult = disp.displayFeedback(feedback);
			assert (returnResult != null);

			// Feedback Display
			doc.insertString(doc.getLength(), divider + newline, null);
			doc.insertString(doc.getLength(), returnResult + newline, null);

			// Logging: Return Command Feedback
			LoggerUI.info("Return Command Feedback");
		} catch (Exception e) {
			// Logging: Exception from actionPerformed
			LoggerUI.warning("Exception @ actionPerformed()");
			System.out.println(e);
		}

		// Additional Feature: Retained Last-Entered Command
		inputScreen.selectAll();
		displayScreen.setCaretPosition(displayScreen.getDocument().getLength());
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
