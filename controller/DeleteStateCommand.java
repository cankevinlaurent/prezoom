/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.State;

/**
 * Respond to the click on the Delete State menu item. It deletes current
 * selected state and set the selection to the first state. To support
 * Undo/Redo operation, it uses three parameters: targetID indicates the state
 * being added; prevFocusedID indicates the previous state which is in the
 * front of the target state; prevModified indicates the last value of
 * modified.
 * 
 * @author Delta Group
 */

public class DeleteStateCommand implements Command {
	private Controller ctr;       // the controller
	private State target;         // last value for Undo operation
	private long prevStateID;     // the previous state in front of the target
	private boolean prevModified; // last value for Undo operation

/**ctr = c;
 stateID = st.getID();
 dessinID = ds.getDID();
 prevWidth = Integer.parseInt(cnt);
 width = w;
 prevModified = ctr.getModified();
 * The constructor. prevStateID = -1 means no deletion at the last time;
 * prevStateID = 0 means the first state has been deleted. prevStateID = xxxxx
 * means the xxxx state's successor (the target) has been deleted.
 * 
 * @param c - the controller
 */

	public DeleteStateCommand(Controller c) {
		ctr = c;
		target = null;
		prevStateID = -1;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		ArrayList<State> states = ctr.getStates();
		if(states.size() == 1) {  // only one state
			JOptionPane.showMessageDialog(
			null, "At least one state should be included.");
			return;
		}

		for(int index=0; index < states.size(); index++) {
			if(states.get(index).getID() == ctr.getFocusedID()) {
				target = states.get(index);
				if (index == 0) prevStateID = 0;
				else prevStateID = states.get(index-1).getID();
				prevModified = ctr.getModified();
				states.remove(index);
				ctr.setFocusedID(states.get(0).getID());
				break;
			}
		}
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.setModified(true);
	}

	@Override
	public void undo() {
		if (prevStateID == -1) return;  // no deletion at the last time
		ArrayList<State> states = ctr.getStates();

		if (prevStateID == 0) {         // the first state has been deleted
			states.add(0, target);
		} else {
    		for(int index=0; index < states.size(); index++) {
	    		if(states.get(index).getID() == prevStateID) {
			    	states.add(index+1, target);
	    			break;
		    	}
	    	}
		}
		ctr.setFocusedID(target.getID());
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.setModified(prevModified);
	}
}
