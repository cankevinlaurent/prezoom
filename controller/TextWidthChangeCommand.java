/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Oval;
import model.Rect;
import model.State;

/**
 * This command handles the width TextField's modification
 * 
 * @author Delta Group
 */

public class TextWidthChangeCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private int prevWidth;
	private int width;
	private boolean prevModified;
	
	public TextWidthChangeCommand(Controller c, State st, Dessin ds, String cnt, int w) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		prevWidth = Integer.parseInt(cnt);
		width = w;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		if (dessin instanceof Oval) ((Oval) dessin).setWidth(width);
		else if (dessin instanceof Rect) ((Rect) dessin).setWidth(width);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(true);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

	@Override
	public void undo() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		if (dessin instanceof Oval) ((Oval) dessin).setWidth(prevWidth);
		else if (dessin instanceof Rect) ((Rect) dessin).setWidth(prevWidth);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
