/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Line;
import model.State;

/**
 * This command handles the EndY TextField's modification
 * 
 * @author Delta Group
 */

public class TextEndYChangeCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private int prevY;
	private int endY;
	private boolean prevModified;
	
	public TextEndYChangeCommand(Controller c, State st, Dessin ds, String cnt, int y) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		prevY = Integer.parseInt(cnt);
		endY = y;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		((Line) dessin).setY2(endY);
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
		((Line) dessin).setY2(prevY);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
