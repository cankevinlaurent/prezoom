/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import java.util.ArrayList;

import model.Dessin;
import model.Line;
import model.Oval;
import model.Rect;
import model.State;
import model.Text;

/**
 * It supports the Add State menu item. To support Undo/Redo operation, it
 * uses three parameters: targetID indicates the state being added;
 * prevFocusedID indicates last state where focus was; prevModified
 * indicates the last value of modified.
 * 
 * @author Delta Group
 */

public class InsertStateCommand implements Command {
	private Controller ctr;          // the controller
	private long prevStateID;        // last state
	private long targetID;           // last value for Undo operation
	private long prevFocusedID;      // last value for Undo operation
	private boolean prevModified;    // last value for Undo operation

/**
 * The constructor.
 * 
 * @param c - the controller
 */

	public InsertStateCommand(Controller c, long sid) {
		ctr = c;
		prevStateID = sid;
		targetID = 0;
		prevFocusedID = 0;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		long sID = ctr.getFocusedID();
		ArrayList<State> states = ctr.getStates();

		for (int index = 0; index < states.size(); index++)
			if(states.get(index).getID() == sID) {      // found
				State st = new State(prevStateID);
				
				// copy all the shapes from previous state
				State pst = states.get(index);
				if (!pst.getDessins().isEmpty()) {
					for (Dessin dessin : pst.getDessins()) {
						Dessin ds = null;
						if (dessin instanceof Line)
    						ds = new Line((Line) dessin);
						else if (dessin instanceof Oval)
							ds = new Oval((Oval) dessin);
						else if (dessin instanceof Rect)
							ds = new Rect((Rect) dessin);
						else if (dessin instanceof Text)
							ds = new Text((Text) dessin);
						st.appendDessin(ds);
					}
				}
				
				prevFocusedID = sID;
				targetID = st.getID();
				states.add(index+1, st);
				ctr.setFocusedID(st.getID());
				break;
			}
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.setModified(true);
	}

	@Override
	public void undo() {
		ArrayList<State> states = ctr.getStates();
		for (State st : states) {
			if (st.getID() == targetID) {
				prevStateID = st.getID();
				states.remove(st);
				ctr.setFocusedID(prevFocusedID);
				break;
			}
		}
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.setModified(prevModified);
	}
}
