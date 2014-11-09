//@author A0114573J
package edu.dynamic.dynamiz.UI;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.dynamic.dynamiz.parser.OptionType;

/**
 * This is the StyleUI class that will set the styling of the Styled Document.
 * It recevies the styling settings from Displayer to UI to StyleUI before
 * returning the style settings to UI
 */

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

	private final int STATUS_COMPLETED = 10;
	private final int STATUS_PENDING = 11;

	/**
	 * Defines the stylesheet for displaying (Hightlight & Priority)
	 */
	public StyleUI() {
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

	/**
	 * Returns SimpleAttributeSet styleWhite
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleWhite() {
		return styleWhite;
	}

	/**
	 * Sets SimpleAttributeSet styleWhite
	 * 
	 * @return
	 */
	public void setStyleWhite(SimpleAttributeSet styleWhite) {
		this.styleWhite = styleWhite;
	}

	/**
	 * Returns SimpleAttributeSet styleDefault
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleDefault() {
		return styleDefault;
	}

	/**
	 * Sets SimpleAttributeSet styleDefault
	 * 
	 * @return
	 */
	public void setStyleDefault(SimpleAttributeSet styleDefault) {
		this.styleDefault = styleDefault;
	}

	/**
	 * Returns SimpleAttributeSet styleGreen
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleGreen() {
		return styleGreen;
	}

	/**
	 * Sets SimpleAttributeSet styleGreen
	 * 
	 * @return
	 */
	public void setStyleGreen(SimpleAttributeSet styleGreen) {
		this.styleGreen = styleGreen;
	}

	/**
	 * Returns SimpleAttributeSet styleOrange
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleOrange() {
		return styleOrange;
	}

	/**
	 * Sets SimpleAttributeSet styleOrange
	 * 
	 * @return
	 */
	public void setStyleOrange(SimpleAttributeSet styleOrange) {
		this.styleOrange = styleOrange;
	}

	/**
	 * Returns SimpleAttributeSet styleMagenta
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleMagenta() {
		return styleMagenta;
	}

	/**
	 * Sets SimpleAttributeSet styleMagenta
	 * 
	 * @return
	 */
	public void setStyleMagenta(SimpleAttributeSet styleMagenta) {
		this.styleMagenta = styleMagenta;
	}

	/**
	 * Returns SimpleAttributeSet styleRed
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleRed() {
		return styleRed;
	}

	/**
	 * Sets SimpleAttributeSet styleRed
	 * 
	 * @return
	 */
	public void setStyleRed(SimpleAttributeSet styleRed) {
		this.styleRed = styleRed;
	}

	/**
	 * Returns SimpleAttributeSet styleBlue
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleBlue() {
		return styleBlue;
	}

	/**
	 * Sets SimpleAttributeSet styleBlue
	 * 
	 * @return
	 */
	public void setStyleBlue(SimpleAttributeSet styleBlue) {
		this.styleBlue = styleBlue;
	}

	/**
	 * Returns SimpleAttributeSet styleCyan
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleCyan() {
		return styleCyan;
	}

	/**
	 * Sets SimpleAttributeSet styleCyan
	 * 
	 * @return
	 */
	public void setStyleCyan(SimpleAttributeSet styleCyan) {
		this.styleCyan = styleCyan;
	}

	/**
	 * Returns SimpleAttributeSet styleYellow
	 * 
	 * @return
	 */
	public SimpleAttributeSet getStyleYellow() {
		return styleYellow;
	}

	/**
	 * Sets SimpleAttributeSet styleYellow
	 * 
	 * @return
	 */
	public void setStyleYellow(SimpleAttributeSet styleYellow) {
		this.styleYellow = styleYellow;
	}

	/**
	 * Returns the SimpleAttributeSet for the corresponding styleValue (Priority
	 * Value or Status Value - 10 for completed, 11 for pending)
	 * 
	 * @param styleValue
	 * @return
	 */
	public SimpleAttributeSet getStyleType(int styleValue) {
		switch (styleValue) {
		case OptionType.PRIORITY_LOW:
			return getStyleGreen();
		case OptionType.PRIORITY_MEDIUM:
			return getStyleOrange();
		case OptionType.PRIORITY_HIGH:
			return getStyleMagenta();
		case OptionType.PRIORITY_URGENT:
			return getStyleRed();
		case STATUS_COMPLETED:
			return getStyleBlue();
		case STATUS_PENDING:
			return getStyleCyan();
		default:
			return getStyleDefault();
		}
	}
}
