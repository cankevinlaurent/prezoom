/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import controller.Controller;

/**
 * This is the rectangle shape
 * 
 * @author Group Delta
 */

public class Rect extends Dessin {
	private static final long serialVersionUID = 987430L;
	private int width;
    private int height;
    private boolean isFilled;

/**
 * The constructor.
 * 
 * @param c - the color
 * @param x - the starting position x
 * @param y - the ending position y
 * @param w - the width of the shape
 * @param h - the height of the shape
 * @param filled - true if it is filled
 */

    public Rect(long id, Color c, int x, int y, int w, int h, boolean filled) {
    	super(id, c, x, y);
    	width = w;
    	height = h;
    	isFilled = filled;
    }

/**
 * Copy constructor
 * 
 * @param Rect - the rect
 */

    public Rect(Rect re) {
    	super(re);
    	if (re == null) return;
    	width = re.getWidth();
    	height = re.getHeight();
    	isFilled = re.getIsFilled();
    }

/**
 * Get width
 * 
 * @return width - the width of the shape
 */

    public int getWidth() { return width; }

/**
 * Set width
 * @param w - the width
 */

    public void setWidth(int w) { width = w; }

/**
 * Get height
 * 
 * @return height - the height of the shape
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
 * @return isFilled - true if it is filled
 */

    public boolean getIsFilled() { return isFilled; }

/**
 * Set the status of the filling
 * 
 * @param filled - true if it is filled
 */

    public void setIsFilled(boolean filled) { isFilled = filled; }

/**
 * Get a shadow of the shape in memory.
 * 
 * @param ctr - Controller
 * @return Shape - the shadow of the shape
 */

	@Override
	public Shape getShape(Controller ctr) { return new Rectangle2D.Double(X, Y, width, height); }
}