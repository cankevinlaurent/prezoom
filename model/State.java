/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;
import java.util.ArrayList;

import controller.Controller;

/**
 * The state, containing all the graphics (dessins) of a state, listing
 * in an ArrayList<Dessin> format. It has a unique ID auto-generated from the
 * UNIX time stamp.
 * 
 * @author Delta Group
 */

public class State implements Serializable {
	private static final long serialVersionUID = 115829L;
	private long sID;                     // unique ID of a state
	private ArrayList<Dessin> dessins;    // all dessins to this state
	private Dessin selectedDessin;        // indicating which is selected
	
/**
 * The constructor.
 * 
 * @param id - the unique ID
 */

	public State(long id) {
		sID = id;
		dessins = new ArrayList<Dessin>();
		selectedDessin = null;
	}

/**
 * Copy constructor.
 */

	public State(State src) {
		sID = src.getID();
		dessins = new ArrayList<Dessin>();
		selectedDessin = null;
		if (src.getDessins().isEmpty()) return;

		Dessin d = null;
		for (Dessin ds : src.getDessins()) {
			if (ds instanceof Line) d = new Line((Line)ds);
			else if(ds instanceof Oval) d = new Oval((Oval)ds);
			else if (ds instanceof Rect) d = new Rect((Rect)ds);
			else if (ds instanceof Text) d = new Text((Text)ds);
			dessins.add(d);
			if (src.getSelectedDessin() == ds) selectedDessin = d;
		}
	}

/**
 * Get sID
 * 
 * @return sID - the state ID
 */

	public long getID() { return sID; }

/**
 * Get a dessin by its ID
 * 
 * @param dID - the dessin id
 * @return ds - the dessin
 */

	public Dessin getDessin(long dID) {
		if (dessins.isEmpty()) return null;
		for (Dessin ds : dessins)
			if (ds.getDID() == dID) return ds;
		return null;
	}

/**
 * Get a list of shapes
 * 
 * @return shapes - an ArrayList<Shape> of all the shapes in a list.
 */

	public ArrayList<Dessin> getDessins() { return dessins; }

/**
 * Get selectedDessin
 * 
 * @return sd - current selected dessin
 */

	public Dessin getSelectedDessin() { return selectedDessin; }

/**
 * Set selectedDessin
 * 
 * @param sd - current selected dessin
 */

	public void setSelectedDessin(Dessin sd) { selectedDessin = sd; }

/**
 * Append a dessin to the dessins.
 * 
 * @param ds - the added dessin
 */

	public void appendDessin(Dessin ds) { dessins.add(ds); }

/**
 * paint all dessins within current state. If a shape has been selected, it
 * will bold its text or add a border in terms of its type.
 */

	public void display(Graphics g, State st, Controller ctr) {
		if (st == null) return; // no state
		if (st.getDessins().isEmpty()) return; // no shapes

		Graphics2D g2d = (Graphics2D) g;
		for (Dessin ds : st.getDessins()) {
			g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
			g2d.setStroke(new BasicStroke(1));

			if (ds == selectedDessin) {
				g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
				g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_BEVEL, 0, new float[]{16, 4}, 0));
			}
			
			Color color = new Color(ds.getColor().getRed()/255.0f,
					ds.getColor().getGreen()/255.0f,
					ds.getColor().getBlue()/255.0f, ds.getTransparent());
			g2d.setColor(color);
			int X = ds.getX();
			int Y = ds.getY();

			if (ds instanceof Text) {
				String title = ((Text) ds).getTitle();
				g2d.drawString(title, X, Y);
			} else {
				Shape shape = ds.getShape(ctr);
				if (ds instanceof Oval) {
					boolean isFilled = ((Oval) ds).getIsFilled();
					if (isFilled) g2d.fill(shape);
				} else if (ds instanceof Rect) {
					boolean isFilled = ((Rect) ds).getIsFilled();
					if (isFilled) g2d.fill(shape);
				}
				g2d.draw(shape);
			}
		}
	}
}
