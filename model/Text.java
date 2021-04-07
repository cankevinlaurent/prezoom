/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import controller.Controller;

/**
 * This is the text shape. Its attributes contain the color, the starting
 * point, the title, and a graphics instance used for computing its bound.
 * 
 * @author Group Delta
 */

public class Text extends Dessin {
	private static final long serialVersionUID = 430152L;
	private String title;

/**
 * The constructor.
 * 
 * @param c - the color
 * @param x - the starting position x
 * @param y - the starting position y
 * @param str - the title
 */

    public Text(long id, Color c, int x, int y, String str) {
    	super(id, c, x, y);
    	title = str;
    }

/**
 * Copy constructor
 * 
 * @param tx - the Text
 */

	public Text(Text tx) {
		super(tx);
		if (tx == null) return;
		title = tx.getTitle();
	}

/**
 * Get title
 * 
 * @return title - the title
 */

    public String getTitle() { return title; }

/**
 * Set title
 * 
 * @param str - the title
 */

    public void setTitle(String str) { title = str; }

/**
 * Get a shadow of the shape in memory. It creates a rectangle for this string
 * which represents its bound.
 * 
 * @param ctr - Controller
 * @return Shape - the shadow of the shape
 */

	@Override
	public Shape getShape(Controller ctr) {
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
		FontMetrics fm = ctr.getMainWindow().getCanvas().getFontMetrics(font);
		int h = fm.getHeight();
		int w = fm.stringWidth(title);
		int ascent = fm.getAscent();
		return new Rectangle2D.Double(X, Y-ascent, w, h);
	}
}
