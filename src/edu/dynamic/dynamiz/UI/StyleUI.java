package edu.dynamic.dynamiz.UI;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class StyleUI {
	// Styling Constants
	private SimpleAttributeSet styleWhite;
	private SimpleAttributeSet styleDefault;
	private SimpleAttributeSet styleGreen;
	private SimpleAttributeSet styleOrange;
	private SimpleAttributeSet styleMagenta;
	private SimpleAttributeSet styleRed;
	private SimpleAttributeSet styleBlue;
	private SimpleAttributeSet styleCyan;
	private SimpleAttributeSet styleYellow;

	/**
	 * Defines the stylesheet for displaying (Hightlight & Priority)
	 */
	public StyleUI(){
		styleDefault = new SimpleAttributeSet();
		StyleConstants.setForeground(styleDefault, Color.WHITE);
	
		styleWhite = new SimpleAttributeSet();
		StyleConstants.setForeground(styleWhite, Color.WHITE);

		styleGreen = new SimpleAttributeSet();
		StyleConstants.setForeground(styleGreen, Color.GREEN);

		styleOrange = new SimpleAttributeSet();
		StyleConstants.setForeground(styleOrange, Color.ORANGE);

		styleMagenta = new SimpleAttributeSet();
		StyleConstants.setForeground(styleMagenta, Color.MAGENTA);

		styleRed = new SimpleAttributeSet();
		StyleConstants.setForeground(styleRed, Color.RED);

		styleBlue = new SimpleAttributeSet();
		StyleConstants.setForeground(styleBlue, Color.BLUE);

		styleCyan = new SimpleAttributeSet();
		StyleConstants.setForeground(styleCyan, Color.CYAN);

		styleYellow = new SimpleAttributeSet();
		StyleConstants.setForeground(styleYellow, Color.YELLOW);
	}

	public SimpleAttributeSet getStyleWhite() {
		return styleWhite;
	}

	public void setStyleWhite(SimpleAttributeSet styleWhite) {
		this.styleWhite = styleWhite;
	}

	public SimpleAttributeSet getStyleDefault() {
		return styleDefault;
	}

	public void setStyleDefault(SimpleAttributeSet styleDefault) {
		this.styleDefault = styleDefault;
	}

	public SimpleAttributeSet getStyleGreen() {
		return styleGreen;
	}

	public void setStyleGreen(SimpleAttributeSet styleGreen) {
		this.styleGreen = styleGreen;
	}

	public SimpleAttributeSet getStyleOrange() {
		return styleOrange;
	}

	public void setStyleOrange(SimpleAttributeSet styleOrange) {
		this.styleOrange = styleOrange;
	}

	public SimpleAttributeSet getStyleMagenta() {
		return styleMagenta;
	}

	public void setStyleMagenta(SimpleAttributeSet styleMagenta) {
		this.styleMagenta = styleMagenta;
	}

	public SimpleAttributeSet getStyleRed() {
		return styleRed;
	}

	public void setStyleRed(SimpleAttributeSet styleRed) {
		this.styleRed = styleRed;
	}

	public SimpleAttributeSet getStyleBlue() {
		return styleBlue;
	}

	public void setStyleBlue(SimpleAttributeSet styleBlue) {
		this.styleBlue = styleBlue;
	}

	public SimpleAttributeSet getStyleCyan() {
		return styleCyan;
	}

	public void setStyleCyan(SimpleAttributeSet styleCyan) {
		this.styleCyan = styleCyan;
	}

	public SimpleAttributeSet getStyleYellow() {
		return styleYellow;
	}

	public void setStyleYellow(SimpleAttributeSet styleYellow) {
		this.styleYellow = styleYellow;
	}
	
}
