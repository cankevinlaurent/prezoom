/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Oval;
import model.Rect;
import model.State;

/**
 * This command handles the height TextField's modification
 * 
 * @author Delta Group
 */

public class TextHeightChangeCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private int prevHeight;
	private int height;
	private boolean prevModified;
	
	public TextHeightChangeCommand(Controller c, State st, Dessin ds, String cnt, int h) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		prevHeight = Integer.parseInt(cnt);
		height = h;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		if (dessin instanceof Oval) ((Oval) dessin).setHeight(height);
		else if (dessin instanceof Rect) ((Rect) dessin).setHeight(height);
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
		if (dessin instanceof Oval) ((Oval) dessin).setHeight(prevHeight);
		else if (dessin instanceof Rect) ((Rect) dessin).setHeight(prevHeight);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
