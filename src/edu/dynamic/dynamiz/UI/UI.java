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
 * 
 * @author XYLau
 *
 */
public class UI extends JPanel implements ActionListener {
	protected JTextField inputScreen;
	protected JTextPane displayScreen;
	private final static String newline = "\n";
	public static DisplayFormatter disp = new DisplayFormatter();
	public static Controller cont = new Controller();
	public static Font font = new Font("Arial", Font.PLAIN, 12);
	public StyledDocument doc;
	private String divider = "------------------------------------------------------------------------------------";
	private SimpleAttributeSet keyWord;
	private final static Logger LoggerUI = Logger.getLogger(UI.class.getName());

	
	public UI() {
		super(new GridBagLayout());

		// Initialise Logger to alert above INFO level (Severe & Warning)
		LoggerUI.setLevel(Level.INFO);

		displayScreen = new JTextPane();
		displayScreen.setEditable(false);

		doc = displayScreen.getStyledDocument();

		keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.BLUE);
		StyleConstants.setBold(keyWord, true);

		JScrollPane scrollPane = new JScrollPane(displayScreen);
		
		// Set displayScreen to fixed size for viewing 
		scrollPane.getViewport().setPreferredSize(new Dimension(1000,500));
		
		inputScreen = new JTextField(100);
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
		try
		{
		    doc.insertString(0, disp.displayWelcomeMessage()+newline, null );
		    doc.insertString(doc.getLength(), disp.displayPrompt(1)+newline, null );
		}
		catch(Exception e) { System.out.println(e); }
		
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
		try
		{
			// Command Prompt Display 
		    doc.insertString(doc.getLength(), disp.displayPrompt(), keyWord );
		    doc.insertString(doc.getLength(), input+newline, keyWord);

		    if (input.equalsIgnoreCase("exit")) {
				LoggerUI.info("Exit Dynamiz");
				System.exit(0);
			}

		    // Command Feedback
			Feedback feedback = cont.executeCommand(input);
			String returnResult = disp.displayFeedback(feedback);
			assert (returnResult != null);
			
			// Feedback Display
			doc.insertString(doc.getLength(), divider + newline, null );
		    doc.insertString(doc.getLength(), returnResult+newline, null);
		    doc.insertString(doc.getLength(), divider+newline, null);

		}
		catch(Exception e) { System.out.println(e); }
		
				
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
