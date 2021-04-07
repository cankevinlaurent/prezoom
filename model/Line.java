/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;

import controller.Controller;

/**
 * This is the Line shape. It has attributes like the color, the starting
 * point and the ending point.
 * 
 * @author Group Delta
 */

public class Line extends Dessin {
	private static final long serialVersionUID = 201119L;
	private int X2;
	private int Y2;

/**
 * The Constructor.
 * 
 * @param c - the color
 * @param x - the starting point x
 * @param y - the starting point y
 * @param x2 - the ending point x
 * @param y2 - the ending point y
 */

	public Line(long id, Color c, int x, int y, int x2, int y2) {
		super(id, c, x, y);
		X2 = x2;
		Y2 = y2;
	}

/**
 * Copy constructor
 * 
 * @param Line - the line
 */

	public Line(Line ln) {
		super(ln);
		if (ln == null) return;
		X2 = ln.getX2();
		Y2 = ln.getY2();
	}

/**
 * Get the ending point x
 * 
 * @return X2 - the ending point x
 */

	public int getX2( ) { return X2; }

/**
 * Set the ending point x
 * 
 * @param x2 - the ending point x
 */

	public void setX2(int x2) { X2 = x2; }

/**
 * Get the ending point y
 * 
 * @return Y2 - the ending point y
 */

	public int getY2() { return Y2; }

/**
 * Set the ending point y
 * 
 * @param y2 - the ending point y
 */

	public void setY2(int y2) { Y2= y2; }

/**
 * Get a shadow of the shape in memory
 * 
 * @param ctr - Controller
 * @return Shape - the shadow of the shape
 */

	@Override
	public Shape getShape(Controller ctr) { return new Line2D.Double(X, Y, X2, Y2); }

/**
 * Determine if position (x, y) is in the shape
 * 
 * @param x - position x
 * @param y - position y
 * @param ctr - the controller
 */

    @Override
    public boolean isContain(int x, int y, Controller ctr) {
	    Shape sp = getShape(ctr);
	    return sp.intersects(x-2, y-2, 4, 4);
    }

}
