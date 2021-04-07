/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import java.awt.Color;
import javax.swing.JButton;
import model.Dessin;
import model.State;

/**
 * This command handles the changing color operation
 * 
 * @author Delta Group
 */

public class ChangeColorCommand implements Command {
	private Controller ctr;
	private long stateID;
	private long dessinID;  // original dessin id
	private JButton button;
	private Color prevColor;
	private Color changed;
	private boolean prevModified;
	
/**
 * The constructor
 * 
 * @param ds - the original dessin
 * @param btn - the color button
 * @param c - the picked color
 */

	public ChangeColorCommand(Controller c, State st, Dessin ds, JButton btn, Color clr) {
		ctr = c;
		stateID = st.getID();
		dessinID = ds.getDID();
		button = btn;
		prevColor = ds.getColor();
		changed = clr;
		prevModified = ctr.getModified();
	}

	@Override
	public void execute() {
		button.setBackground(changed);
		Dessin ds = ctr.getState(stateID).getDessin(dessinID);
		ds.setColor(changed);
		ctr.setModified(true);
		ctr.getMainWindow().getThumbnailPanel().display();
	}

	@Override
	public void undo() {
		Dessin ds = ctr.getState(stateID).getDessin(dessinID);
		ds.setColor(prevColor);
		button.setBackground(prevColor);
		ctr.setModified(prevModified);
		ctr.getMainWindow().getThumbnailPanel().display();
	}
}
