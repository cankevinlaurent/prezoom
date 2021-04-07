/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.Line;
import model.Oval;
import model.Rect;
import model.State;
import model.Text;

/**
 * This command handles the paste dessin operation. A dessin can only be pasted
 * when it goes to different states and on that state there is no dessins with
 * the same dessinID.
 * 
 * @author Delta Group
 */

public class PasteDessinCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;
	private long destStateID;
	private boolean prevModified;
	
/**
 * The constructor.
 */

	public PasteDessinCommand(Controller c, long sid, long did, long dsid) {
		ctr = c;
		stateID = sid;
		dessinID = did;
		destStateID = dsid;
		prevModified = ctr.getModified();
		
	}

	@Override
	public void execute() {
		State sState = ctr.getState(stateID);
		Dessin sDessin = sState.getDessin(dessinID);
		
		Dessin dessin = null;
		if (sDessin instanceof Line)
    		dessin = new Line((Line)sDessin);
		else if (sDessin instanceof Oval)
			dessin = new Oval((Oval)sDessin);
		else if (sDessin instanceof Rect)
			dessin = new Rect((Rect)sDessin);
		else if (sDessin instanceof Text)
			dessin = new Text((Text)sDessin);

		State dState = ctr.getState(destStateID);
		dState.appendDessin(dessin);
		
		ctr.setFocusedState(dState);
		dState.setSelectedDessin(dessin);
		ctr.setModified(true);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

	@Override
	public void undo() {
		State dState = ctr.getState(destStateID);
		Dessin dessin = dState.getDessin(dessinID);
		dState.getDessins().remove(dessin);
		
		ctr.setFocusedState(dState);
		dState.setSelectedDessin(null);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
