/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Line;
import model.State;

/**
 * This command handles the EndX TextField's modification
 * 
 * @author Delta Group
 */

public class TextEndXChangeCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private int prevX;
	private int endX;
	private boolean prevModified;
	
	public TextEndXChangeCommand(Controller c, State st, Dessin ds, String cnt, int x) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		prevX = Integer.parseInt(cnt);
		endX = x;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		((Line) dessin).setX2(endX);
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
		((Line) dessin).setX2(prevX);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
