/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.State;
import model.Text;

/**
 * This command handles the title TextField's modification
 * 
 * @author Delta Group
 */

public class TextTitleChangeCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private String prevStr;
	private String str;
	private boolean prevModified;
	
	public TextTitleChangeCommand(Controller c, State st, Dessin ds, String cnt, String s) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		prevStr = cnt;
		str = s;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		State st = ctr.getState(stateID);
		Dessin dessin = st.getDessin(dessinID);
		((Text) dessin).setTitle(str);
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
		((Text) dessin).setTitle(prevStr);
		ctr.setFocusedState(st);
		st.setSelectedDessin(dessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
