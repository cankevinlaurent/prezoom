/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import controller.Controller;
import model.State;

/**
 * The main window, also one of the important View, is separated into five
 * areas: the Menu Bar, the Tool Bar, the Thumbnail Panel, the Canvas, and
 * the Edit Panel. All events are handled by the Controller.
 * 
 * @author Delta Group
 */

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 788933L;
	private ThumbnailPanel tp;     // Thumbnail panel on the left
	private Canvas cv;             // Canvas in the middle
	private EditPanel ep;          // Edit Panel on the right

/**
 * The constructor, arrange the main window into the border layout. It
 * binds itself to the controller and panels to the main window. It calls
 * creates a default blank state.
 * 
 * @param title - shown on the title bar of the main window
 */

	public MainWindow(String title, Controller ctr) {
		super(title);

		cv = new Canvas(ctr);
		JScrollPane jspMiddle = new JScrollPane(cv);
		jspMiddle.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jspMiddle.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		ep = new EditPanel(ctr);

		tp = new ThumbnailPanel(ctr);
		JScrollPane jspLeft = new JScrollPane(tp);
        jspLeft.setVerticalScrollBarPolicy(
        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(new ToolBar(ctr), BorderLayout.PAGE_START);
		add(jspLeft, BorderLayout.LINE_START);
		add(jspMiddle, BorderLayout.CENTER);
		add(ep, BorderLayout.LINE_END);
		
		setJMenuBar(new MenuBar(ctr));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);                 // center the window
		setVisible(true);
		
		// create the default state
		State st = new State(System.currentTimeMillis());
		ctr.appendState(st);
		ctr.setFocusedID(st.getID());
		getThumbnailPanel().display();

		addWindowListener(new WindowAdapter() {      // prompt before exit
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				ctr.onExitClicked();
			}
		});
	}

/**
 * Get Thumbnail Panel
 * 
 * @return tp - the thumbnail panel
 */

	public ThumbnailPanel getThumbnailPanel() { return tp; }

/**
 * Get the Canvas
 * 
 * @return cv - the Canvas
 */

	public Canvas getCanvas() { return cv; }

/**
 * Get the Edit panel
 * 
 * @return ep - the edit panel
 */

	public EditPanel getEditPanel() { return ep; }
}
