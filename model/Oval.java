/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import controller.Controller;

/**
 * This is the oval shape. Its attributes contain the color, the starting
 * point, the width, the height, and its status of filling.
 * 
 * @author Group Delta
 */

public class Oval extends Dessin {
	private static final long serialVersionUID = 101301L;
	private int width;
    private int height;
    private boolean isFilled;

/**
 * The constructor.
 * 
 * @param c - the color
 * @param x - the position x
 * @param y - the position y
 * @param w - the width
 * @param h - the height
 * @param filled - the status of filling, true if filled
 */

    public Oval(long id, Color c, int x, int y, int w, int h, boolean filled) {
    	super(id, c, x, y);
    	width = w;
    	height = h;
    	isFilled = filled;
    }

/**
 * Copy constructor
 * 
 * @param Oval - the oval
 */

    public Oval(Oval ov) {
    	super(ov);
    	if (ov == null) return;
    	width = ov.getWidth();
    	height = ov.getHeight();
    	isFilled = ov.getIsFilled();
    }

/**
 * Get width
 * 
 * @return width - the width
 */

    public int getWidth() { return width; }

/**
 * Set width
 * 
 * @param w - the width
 */

    public void setWidth(int w) { width = w; }

/**
 * Get height
 * 
 * @return - the height
 */

    public int getHeight() { return height; }

/**
 * Set height
 * 
 * @param h - the height
 */

    public void setHeight(int h) { height = h; }

/**
 * Determine if it is filled with color
 * 
 * @return isFilled - true if filled
 */

    public boolean getIsFilled() { return isFilled; }

/**
 * Set the status of filling
 * 
 * @param filled - true if filled
 */

    public void setIsFilled(boolean filled) { isFilled = filled; }

/**
 * Get a shadow of the shape in memory.
 * 
 * @param ctr - Controller
 * @return Shape - the shadow of the shape
 */

	@Override
	public Shape getShape(Controller ctr) { return new Ellipse2D.Double(X, Y, width, height); }

}
