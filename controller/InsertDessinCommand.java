/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.Dessin;
import model.Line;
import model.Oval;
import model.Rect;
import model.State;
import model.Text;

/**
 * It stores a newly drawn shape into one selected state. The selected state
 * is given by focusedID.
 * 
 * @author Delta Group
 */

public class InsertDessinCommand implements Command {
	private Controller ctr;        // the controller
	private int type;              // indicating the shape
	private long dessinID;         // the dessinID
	private long focusedID;        // the value used for Undo/Redo operation
	private boolean prevModified;  // the value used for Undo/Redo operation
	private Dessin prevDessin;     // the value used for Undo/Redo operation
	private int prevBrush;         // the value used for Undo/Redo operation

	private int X1;                // the mouse postion
	private int Y1;                // the mouse postion
	private int X2;                // the mouse postion
	private int Y2;                // the mouse postion

	private int prevX1;            // the value used for Undo/Redo operation
	private int prevY1;            // the value used for Undo/Redo operation
	private int prevX2;            // the value used for Undo/Redo operation
	private int prevY2;            // the value used for Undo/Redo operation
	
/**
 * The constructor.
 * 
 * @param c - the controller
 * @param fID - the focused state's id
 * @param t - the brush
 * @param id - the dessin id
 * @param x1 - the starting position x of the mouse
 * @param y1 - the starting position y of the mouse
 * @param x2 - the ending position x of the mouse
 * @param y2 - the ending position y of the mouse
 */

	public InsertDessinCommand(Controller c, long fID, int t, long id, int x1,
			int y1, int x2, int y2) {
		ctr = c;
		focusedID = fID;
		type = t;
		dessinID = id;
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
		prevModified = ctr.getModified();
	}

/**
 * Store a new graphical shape to current selected state.
 */

	@Override
	public void execute() {
		
		ctr.getMainWindow().getCanvas().repaint();
		ctr.getMainWindow().getCanvas().revalidate();

		Dessin ds;
		switch (type) {
		case Dessin.LINE:
			ds = new Line(dessinID, Color.RED, X1, Y1, X2, Y2);
			break;
		case Dessin.OVAL:
			ds = new Oval(dessinID, Color.GREEN, X1, Y1, X2-X1, Y2-Y1, true);
			break;
		case Dessin.RECT:
			ds = new Rect(dessinID, Color.BLUE, X1, Y1, X2-X1, Y2-Y1, true);
			break;
		case Dessin.TEXT:
			ds = new Text(dessinID, Color.PINK, X1, Y1, "hello, world");
			break;
		default:
			return;
		}

		prevDessin = ds;
		prevX1 = X1;
		prevY1 = Y1;
		prevX2 = X2;
		prevY2 = Y2;

		ctr.appendShape(ds);
		ctr.getFocusedState().setSelectedDessin(ds);

		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
		ctr.getMainWindow().getCanvas().repaint();
		ctr.getMainWindow().getCanvas().revalidate();
		ctr.setModified(true);
	}

/**
 * Undo the insert dessin operation. It removes the current selected dessin,
 * stores mouse starting and ending points, sets selected dessin to null.
 */

	@Override
	public void undo() {
		ArrayList<State> states = ctr.getStates();
		for(State st : states) {
			if(st.getID() == focusedID) {
				st.getDessins().remove(prevDessin);
				break;
			}
		}

		X1 = prevX1;
		Y1 = prevY1;
		X2 = prevX2;
		Y2 = prevY2;

		ctr.getMainWindow().getCanvas().setType(prevBrush);
		ctr.setModified(prevModified);
		ctr.setFocusedID(focusedID);
		ctr.getFocusedState().setSelectedDessin(null);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
		ctr.getMainWindow().getCanvas().repaint();
		ctr.getMainWindow().getCanvas().revalidate();
	}
}
