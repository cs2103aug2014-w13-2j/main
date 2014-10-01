package edu.dynamic.dynamiz.UI;
/**
 * @author XYLau
 * 
 * Establish Screen for GUI
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JInternalFrame;


public class Screen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen window = new Screen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Screen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void screenPrint(String str){
		
	}
	public void screenPrintln(String str){
		
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblHeader = new JLabel("Dynamiz");
		frame.getContentPane().add(lblHeader, BorderLayout.NORTH);
		
		
		JTextPane txtpnVisualOutput = new JTextPane();
		txtpnVisualOutput.setBackground(Color.BLACK);
		txtpnVisualOutput.setForeground(Color.WHITE);
		txtpnVisualOutput.setText("Output Display");
		frame.getContentPane().add(txtpnVisualOutput, BorderLayout.CENTER);
		
		JTextPane txtpnCommandInput = new JTextPane();
		txtpnCommandInput.setText("Command Input");
		frame.getContentPane().add(txtpnCommandInput, BorderLayout.SOUTH);
		
		JScrollBar scrollBar = new JScrollBar();
		frame.getContentPane().add(scrollBar, BorderLayout.EAST);
	}

}
