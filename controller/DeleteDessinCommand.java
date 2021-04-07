/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import model.Dessin;
import model.State;

/**
 * This command handles the deletion of a dessin in a state.
 * 
 * @author Delta Group
 */
public class DeleteDessinCommand implements Command {
	private Controller ctr;
	private State prevState;
	private Dessin prevDessin;
	private boolean prevModified;
	
	public DeleteDessinCommand(Controller c, State st, Dessin selectedDessin) {
		ctr = c;
		prevState = st;
		prevDessin = selectedDessin;
		prevModified = ctr.getModified();
	}

/**
 * Delete current selected dessin
 */

	@Override
	public void execute() {
		ctr.setFocusedState(prevState);
		prevState.getDessins().remove(prevDessin);
		prevState.setSelectedDessin(null);
		ctr.setModified(true);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

	@Override
	public void undo() {
		ctr.setFocusedState(prevState);
		prevState.appendDessin(prevDessin);
		prevState.setSelectedDessin(prevDessin);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
		ctr.getMainWindow().getEditPanel().display();
	}

}
