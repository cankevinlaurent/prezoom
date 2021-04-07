/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Line;
import model.State;

/**
 * This command handles the dessin dragging
 * 
 * @author Delta Group
 */

public class DragDessinCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private int X1, Y1;
	private int X2, Y2;
	private int oX, oY;
	private int oX2, oY2;
	private boolean prevModified;

	public DragDessinCommand(Controller c, State st, Dessin ds, int x1, int y1, int x2, int y2, int ox, int oy, int ox2, int oy2) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
		oX = ox;  // original position of a dessin
		oY = oy;
		oX2 = ox2;
		oY2 = oy2;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);

		if (dessin instanceof Line) {
			((Line) dessin).setX2(X2 + oX2 - X1);
			((Line) dessin).setY2(Y2 + oY2 - Y1);
		}
		dessin.setX(X2 + oX - X1);
		dessin.setY(Y2 + oY - Y1);
		ctr.setModified(true);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

	@Override
	public void undo() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);

		if (dessin instanceof Line) {
			((Line) dessin).setX2(oX2);
			((Line) dessin).setY2(oY2);
		}
		dessin.setX(oX);
		dessin.setY(oY);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
