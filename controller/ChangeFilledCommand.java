/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Oval;
import model.Rect;
import model.State;

/**
 * This command deals with changing the status of a filled shape.
 * 
 * @author Delta Group
 */

public class ChangeFilledCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;  // original dessin id
	private String selectedItem;
	private boolean prevModified;

/**
 * The constructor
 * 
 * @param ds - the original dessin
 * @param cb - the combo box
 * @param selectedIndex - the changed value
 */

	public ChangeFilledCommand(Controller c, State st, Dessin ds, String item) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		selectedItem = item;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		Dessin ds = ctr.getState(stateID).getDessin(dessinID);
		if (ds instanceof Oval) {
			if (selectedItem.equals("Yes")) ((Oval) ds).setIsFilled(true);
			else ((Oval) ds).setIsFilled(false);
		} else if (ds instanceof Rect) {
			if (selectedItem.equals("Yes")) ((Rect) ds).setIsFilled(true);
			else ((Rect) ds).setIsFilled(false);
		}
		ctr.setModified(true);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getCanvas().repaint();
		ctr.getMainWindow().getEditPanel().display();
	}

	@Override
	public void undo() {
		Dessin ds = ctr.getState(stateID).getDessin(dessinID);
		if (ds instanceof Oval) {
			if (selectedItem.equals("Yes")) ((Oval) ds).setIsFilled(false);
			else ((Oval) ds).setIsFilled(true);
		} else if (ds instanceof Rect) {
			if (selectedItem.equals("Yes")) ((Rect) ds).setIsFilled(false);
			else ((Rect) ds).setIsFilled(true);
		}
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}
}
