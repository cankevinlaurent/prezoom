/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import controller.Command;
import controller.Controller;
import controller.DragDessinCommand;
import controller.InsertDessinCommand;
import model.Dessin;
import model.Line;
import model.State;

/**
 * This is the canvas inside the Draw panel which supports drag-to-draw.
 * Editors are advised to draw from left-top to right-bottom directions, since
 * I have no time to make sure (later maybe) end position > start position.
 * The Canvas will be re-drawn when its repaint() function is called.
 * 
 * @author Delta Group
 */

public class Canvas extends JPanel {
	private static final long serialVersionUID = 103916L;
	private Controller ctr;
	private int type;                // indicating the type of the shape
	private boolean isDragging;      // if mouse is dragging
	private boolean dragShape;       // if dragging a shape
	private int X1, Y1;              // the mouse's starting position
	private int X2, Y2;              // the mouse's ending position
	
	private int oX, oY;              // the position of a shape before dragging
	private int oX2, oY2;            // the end point of a Line before dragging

/**
 * The constructor.
 * 
 * @param c - the controller
 */

	public Canvas(Controller c) {
		ctr = c;
		type = Dessin.UNKNOWN;
		isDragging = false;
		dragShape = false;
		X1 = Y1 = 0;
		X2 = Y2 = 0;
		oX = oY = 0;
		oX2 = oY2 = 0;

		setPreferredSize(new Dimension(1920, 1920));
		setBackground(Color.WHITE);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int X = me.getX();
				int Y = me.getY();
				
				type = Dessin.UNKNOWN;

				ctr.onCanvasClicked(X, Y);
			}

			@Override
			public void mouseEntered(MouseEvent me) {}

			@Override
			public void mouseExited(MouseEvent me) {}

			@Override
			public void mousePressed(MouseEvent me) {
				X1 = X2 = me.getX();
				Y1 = Y2 = me.getY();
				
				Dessin ds = ctr.getFocusedState().getSelectedDessin();
				if (ds != null && ds.isContain(X1, Y1, ctr)) {
					dragShape = true;
					if (ds instanceof Line) {
						oX2 = ((Line) ds).getX2();
						oY2 = ((Line) ds).getY2();
					}
					oX = ctr.getFocusedState().getSelectedDessin().getX();
					oY = ctr.getFocusedState().getSelectedDessin().getY();
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				X2 = me.getX();
				Y2 = me.getY();

				isDragging = false;

				if (dragShape) {
					State st = ctr.getFocusedState();
					Dessin ds = st.getSelectedDessin();
					Command cd = new DragDessinCommand(ctr, st, ds, X1, Y1, X2, Y2, oX, oY, oX2, oY2);
					cd.execute();
					ctr.getCommandManager().storeCommand(cd);
				}

				dragShape = false;

				long dessinID = System.currentTimeMillis();
				
				ctr.getMainWindow().getThumbnailPanel().display();

				if (type == Dessin.UNKNOWN) return;
				if (X1 == X2 && Y1 == Y2) return;
				Command cmd = new InsertDessinCommand(ctr, ctr.getFocusedID(),
						type, dessinID, X1, Y1, X2, Y2);
				cmd.execute();
				ctr.getCommandManager().clearRedoCommand();
				ctr.getCommandManager().storeCommand(cmd);
				type = Dessin.UNKNOWN;
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				isDragging = true;
				X2 = me.getX();
				Y2 = me.getY();

				if (dragShape == true) {
					Dessin ds = ctr.getFocusedState().getSelectedDessin();
					if (ds == null) return;  // occurs when dragging while deleting

					if (ds instanceof Line) {
						((Line) ds).setX2(X2 + oX2 - X1);
						((Line) ds).setY2(Y2 + oY2 - Y1);
					}
					ds.setX(X2 + oX - X1);
					ds.setY(Y2 + oY - Y1);
				}

				repaint();
				ctr.getMainWindow().getThumbnailPanel().display();
				ctr.getMainWindow().getEditPanel().display();
			}

			@Override
			public void mouseMoved(MouseEvent me) {}
		});
	}

/**
 * The paint function. It first display all current dessins in a state; then,
 * if it is under dragging, it will dynamically paint a dessin as the mouse
 * drags.
 * 
 * @param g - the Graphics
 */

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ctr.getFocusedState().display(g, ctr.getFocusedState(), ctr);

		if (isDragging) {
			Graphics2D g2d = (Graphics2D) g;

			switch(type) {
			case Dessin.LINE:
				g2d.setColor(Color.RED);
				g2d.draw(new Line2D.Double(X1, Y1, X2, Y2));
				break;
			case Dessin.OVAL:
				g2d.setColor(Color.GREEN);
				Ellipse2D oval = new Ellipse2D.Double(X1, Y1, X2-X1, Y2-Y1);
				g2d.fill(oval);
				g2d.draw(oval);
				break;
			case Dessin.RECT:
				g2d.setColor(Color.BLUE);
				Rectangle2D rt = new Rectangle2D.Double(X1, Y1, X2-X1, Y2-Y1);
				g2d.fill(rt);
				g2d.draw(rt);
				break;
			case Dessin.TEXT:
				g2d.setColor(Color.PINK);
				g2d.drawString("hello, world", X2, Y2);
			default:
				break;
			}
		}
		repaint();
	}

/**
 * get type
 * 
 * @return type - the type indicating the dessin
 */

	public int getType() { return type; }
		
/**
 * set type
 * 
 * @param t - the type
 */

	public void setType(int t) { type = t; }

}
