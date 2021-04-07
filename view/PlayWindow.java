/**
 *  THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import controller.Controller;

/**
 * The Play window dealing with the presentation through its camera JPanel.
 * It hides the main window after its show. When pressing ESC, it re-visibles
 * the main window and disposes itself. All events are handled by the
 * Controller.
 * 
 * @author Delta Group
 */

public class PlayWindow extends JFrame {
	private static final long serialVersionUID = 405105L;
	private Controller ctr;
	private Camera camera;
	
/**
 * The constructor. It will hide the Main Window when starting show, and
 * it will also re-visible the Main Window after being disposed.
 * @param ctr - the controller
 */

	public PlayWindow(Controller c) {
		ctr = c;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(false);
		
		camera = null;

/**
 * On window closing
 */

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) { // event before exit
				super.windowClosing(e);
				ctr.onPlayWindowClosing();
			}
		});

/**
 * Check keyboard if key event occurs
 */

		KeyboardFocusManager kfm =
		KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventPostProcessor(new KeyEventPostProcessor() {
			@Override
			public boolean postProcessKeyEvent(KeyEvent e) {
				return ctr.onPlayWindowKeyPerformed(e);
			}
		});

	}
	
/**
 * Get Camera JPanel
 * 
 * @return camera - the Camera
 */
	
	public Camera getCamera() { return camera; }
	
/**
 * Set Camera JPanel
 * 
 * @param cam - the Camera
 */

	public void setCamera(Camera cam) { camera = cam; }

}
