/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.State;

/**
 * This command handles the trans TextField's modification
 * 
 * @author Delta Group
 */

public class TextTransChangeCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private float prevTrans;
	private float trans;
	private boolean prevModified;
	
	public TextTransChangeCommand(Controller c, State st, Dessin ds, String cnt, float t) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		prevTrans = Integer.parseInt(cnt) / 100.0f;
		trans = t;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		dessin.setTransparent(trans);
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
		dessin.setTransparent(prevTrans);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
