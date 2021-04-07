/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;

import controller.Controller;

/**
 * This is the class of the graphical dessins.
 * 
 * @author Delta Group
 */

abstract public class Dessin implements Serializable {
	private static final long serialVersionUID = 110601L;
	private long dID;
    protected Color color;
    protected float transparent;
    protected int X;
    protected int Y;

	// Graphical shapes
    public static final int UNKNOWN = 0;
	public static final int LINE = 1;
	public static final int OVAL = 2;
	public static final int RECT = 4;
	public static final int TEXT = 8;

/**
 * The Constructor.
 * 
 * @param c - the color
 * @param x - the position x
 * @param y - the position y
 */

	public Dessin(long id, Color c, int x, int y) {
		dID = id;
		color = c;
		transparent = 1.0f;
		X = x;
		Y = y;
	}

/**
 * Copy constructor
 * 
 * @param ds - the dessin
 */

	public Dessin(Dessin ds) {
		if (ds == null) return;
		dID = ds.getDID();
		color = ds.getColor();
		transparent = ds.getTransparent();
		X = ds.getX();
		Y = ds.getY();
	}

/**
 * Get ID
 * 
 * @return id - the unique id of a dessin
 */

    public long getDID() { return dID; }

/**
 * Get color
 * 
 * @return color - the foreground color of the graphical object
 */

	public Color getColor() { return color; }
	
/**
 * Set color
 * 
 * @param c - the foreground color of the graphical object
 */

	public void setColor(Color c) { color = c; }

/**
 * Get transparent
 * 
 * @return transparent - the transparency of the shape
 */

	public float getTransparent() { return transparent; }

/**
 * Set transparent
 * 
 * @param trans - the transparency of the shape
 */

	public void setTransparent(float trans) { transparent = trans; }

/**
 * Get X
 * 
 * @return X - the dessin's position X of (X, Y)
 */

	public int getX() { return X; }

/**
 * Set X
 * 
 * @param x - the dessin's position X of (X, Y)
 */

	public void setX(int x) { X = x; }
	
/**
 * Get Y
 * 
 * @return Y - the dessin's position X of (X, Y)
 */

	public int getY() { return Y; }

/**
 * Set Y
 * 
 * @param y - the dessin's position X of (X, Y)
 */

	public void setY(int y) { Y = y; }

/**
 * Create its shadow in the memory.
 * 
 * @param ctr - Controller
 * @return Shape - the shape
 */

	abstract public Shape getShape(Controller ctr);
	
/**
 * Determine if position (x, y) is in the shape
 * 
 * @param x - position x
 * @param y - position y
 * @param ctr - Controller
 */

    public boolean isContain(int x, int y, Controller ctr) {
	    Shape sp = getShape(ctr);
	    return sp.contains(x, y);
    }


}
