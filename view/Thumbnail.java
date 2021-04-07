/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.Controller;

/**
 * This is a button-like thumbnail on the thumbnail Panel. It is generated in
 * terms of the state information. It contains ID mapping to the state data.
 * The ID is an auto-generated from the UNIX time stamp, so it is unique. All
 * events are handled by the controller.
 * 
 * @author Delta Group
 */

public class Thumbnail extends JButton {
	private static final long serialVersionUID = 408472L;
	private long ID;              // unique ID, same as State's ID

/**
 * The constructor.
 * 
 * @param ctr - the Controller
 * @param id - its ID
 */

	public Thumbnail(Controller ctr, long id) {
		ID = id;

		setPreferredSize(new Dimension(80, 80));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onThumbnailClicked(
						(Thumbnail)e.getSource());
			}
		});
	}

/**
 * Get ID
 * 
 * @return long - ID
 */

	public long getID() { return ID; }
}
