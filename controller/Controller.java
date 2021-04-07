/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Dessin;
import model.Model;
import model.State;
import view.MainWindow;
import view.PlayWindow;
import view.Camera;
import view.Thumbnail;

/**
 * The Controller handles all the interactive events in the program. It
 * connects to the Model and the View(s). Roughly, its functions can be parted
 * as: MenuBar & ToolBar, states & modified, drawing, edit
 * panel, and presentation functions.
 * 
 * @author Delta Group
 */

public class Controller {
	private final CommandManager cmr;
	private final Model mod;
	private final MainWindow mw;
	private final PlayWindow pw;

/**
 * The constructor.
 */

	public Controller() {
		cmr = new CommandManager();
		mod = new Model();
		mw = new MainWindow("Prezoom-Delta", this);
		pw = new PlayWindow(this);
	}

/**
 * Get Main Window.
 * 
 * @return mw - the main window
 */

	public MainWindow getMainWindow() { return mw; }

/**
 * Get Play Window.
 * 
 * @retuan pw - the play window
 */

	public PlayWindow getPlayWindow() { return pw; }

/**
 * Get command manager.
 * 
 * @return cmr - the command manager
 */

	public CommandManager getCommandManager() { return cmr; }

////////  States & modified  /////////////////////////////////////////////////

/**
 * get focusedID
 * 
 * @return focusedID - which is on focus
 */

	public long getFocusedID() { return mod.getFocusedState().getID(); }

/**
 * Set focusedID
 * 
 * @param fID - which is on focus
 */

	public void setFocusedID(long fID) { mod.setFocusedState(getState(fID)); }

/**
 * Get States list
 * 
 * @return states - a list of states
 */

	public ArrayList<State> getStates() { return mod.getStates(); }

/**
 * Set states list
 * 
 * @param states - a list of states
 */

	public void setStates(ArrayList<State> states) { mod.setStates(states); }

/**
 * Get modified
 * 
 * @return modified - the modified in boolean.
 */

	public boolean getModified() { return mod.getModifed(); }

/**
 * Set modified
 * 
 * @param modified - true if modified
 */

	public void setModified(boolean modified) { mod.setModified(modified); }

/**
 * Get a state by its ID
 * 
 * @param sID - state id
 * @return st - the state
 */

	public State getState(long sID) {
		for (State st : getStates()) {
			if (st.getID() == sID) return st;
		}
		return null;
	}

/**
 * Clear all states in lists.
 */

	public void clearAllStates() { mod.getStates().clear(); }

/**
 * Append a state to current list.
 * 
 * @param st - the st bing appended
 */

	public void appendState(State st) { mod.appendState(st); }

/**
 * Get focused state. It will get the state data which is currently selected
 * by editors on the thumbnail panel.
 * 
 * @return focusedState - the selected state; null if none selected.
 */

	public State getFocusedState() { return mod.getFocusedState(); }

/**
 * Set focused state.
 * 
 * @param st - the state which is selected.
 */

	public void setFocusedState(State st) { mod.setFocusedState(st); }

////////  Drawing  ///////////////////////////////////////////////////////////

/**
 * Append a shape to current selected state
 * 
 * @param sp - the shape
 */

	public void appendShape(Dessin sp) { getFocusedState().appendDessin(sp); }

////////  Presentation  //////////////////////////////////////////////////////

/**
 * On closing PlayWindow, it re-visibles the MainWindow.
 */

	public void onPlayWindowClosing() { mw.setVisible(true); }

/**
 * Key event in play window. After receiving ESC, It first set Main Window
 * visible, then dispose itself without exiting the whole program.
 */

	public boolean onPlayWindowKeyPerformed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			mw.setVisible(true);
			pw.dispose();
			return true;
		}
		return false;
	}

//// events //////////////////////////////////////////////////////////////////

/**
 * Create new environment with one blank state.
 */

	public void onNewFileClicked() {
		if (getModified()) {
			int val = JOptionPane.showConfirmDialog(null,
			"Wanna save current work first?");

			switch(val) {
			case 0:
				onSaveFileClicked();
				return;
			case 1:
				break;
			default:
				return;
			}
		}
		mw.getCanvas().setType(Dessin.UNKNOWN);
		clearAllStates();
		cmr.clearAllCommand();
		State st = new State(System.currentTimeMillis()); // the default state
		appendState(st);
		setFocusedID(st.getID());
		mw.getThumbnailPanel().display();
		setModified(false);
	}

/**
 * It opens a presentation file on the disk, read the data from it, and
 * then sets environment such as draw the Canvas in terms of the data.
 */

	@SuppressWarnings("unchecked")
	public void onOpenFileClicked() {
		if (getModified()) {
			int val = JOptionPane.showConfirmDialog(
			null, "Wanna save current work first?");

			switch(val) {
			case 0:
				onSaveFileClicked();
				return;
			case 1:
				break;
			default:
				return;
			}
		}
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter fnef =
		new FileNameExtensionFilter("DLT Files", "dlt");
		jfc.setFileFilter(fnef);
		int val = jfc.showOpenDialog(null);
		if(val == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(file));
				setStates((ArrayList<State>) ois.readObject());
				ois.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setFocusedID(getStates().get(0).getID());
			mw.getThumbnailPanel().display();
			cmr.clearAllCommand();
			setModified(false);
		}
	}

/**
 * When MenuBar: SaveFile clicked. It first check if save operation is
 * necessary, then it shows a dialogbox. If users choose to save, it will
 * write data on the disk.
 */

	public void onSaveFileClicked() {
		if (!getModified()) {
			JOptionPane.showMessageDialog(null,
				"No modifications need to save !");
			return;
		}
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter fnef = new FileNameExtensionFilter(
			"DLT Files", "dlt");
		jfc.setFileFilter(fnef);
		jfc.setDialogTitle("Where do you prefer to save ?");
		int val = jfc.showSaveDialog(null);
		if (val != JFileChooser.APPROVE_OPTION) return;  // unsaved

		File file = jfc.getSelectedFile();
		String filename = file.toString();
		if (!filename.endsWith(".dlt")) filename += ".dlt";
		file = new File(filename);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
			new FileOutputStream(file));
			oos.writeObject(getStates());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cmr.clearAllCommand();  // clear all Undos
		setModified(false);
	}

/**
 * On MenuBar: exit clicked. Before exiting, it checks if modifications have
 * been made and the save operation is needed.
 */

	public void onExitClicked() {
		if (!getModified()) System.exit(0);
		int val = JOptionPane.showConfirmDialog(null, "Save before exiting?");

		switch (val) {
		case 0:
			onSaveFileClicked();
			if (!getModified()) System.exit(0);
			else {
				JOptionPane.showMessageDialog(null, "Failed to save!");
				return;
			}
		case 1:
			System.exit(0);
		default:
			return;
		}
	}

/**
 * When MenuBar: Play clicked. It supports the Play menu item.
 */

	public void onPlayClicked() {
		if (pw.getCamera() != null) pw.remove(pw.getCamera());
		Camera cam = new Camera(this);
		cam.setBackground(Color.WHITE);
		cam.setPreferredSize(new Dimension(pw.getWidth(), pw.getHeight()));
		pw.setCamera(cam);
		pw.add(cam);
		
		pw.setVisible(true);
		mw.setVisible(false);
	}

/**
 * When MenuBar: About clicked. It will show a messagebox with authors,
 * copyright, and version.
 */

	public void onAboutClicked() {
		JOptionPane.showMessageDialog(null, "Prezoom v0.1a\n" +
				"Authors: Runze An, Tianyue Jiang, Wei Wang, Yu Cao.\n" +
				"2020 All rights reserved.",
				"About", JOptionPane.INFORMATION_MESSAGE);
	}

/**
 * It supports the event when Toolbar is clicked.
 */

	public void onToolbarClicked(int type) {
		mw.getCanvas().setType(type);
		getFocusedState().setSelectedDessin(null);
		mw.getEditPanel().display();
	}

/**
 * It handles the click event of a Thumbnail. When a thumbnail is clicked, it
 * notifies the Model to update focus data, then it re-displays the Thumbnail
 * panel.
 */

	public void onThumbnailClicked(Thumbnail tn) {
		getFocusedState().setSelectedDessin(null);
		setFocusedID(tn.getID());
		mw.getThumbnailPanel().display();
		mw.getEditPanel().display();
	}

/**
 * When canvas clicked
 */

	public void onCanvasClicked(int X, int Y) {
		mw.getCanvas().setType(Dessin.UNKNOWN);

		ArrayList<Dessin> dessins = getFocusedState().getDessins();
		if (dessins.isEmpty()) {    // contain no shape
			getFocusedState().setSelectedDessin(null);
			mw.getEditPanel().display();
			return;
		}

		for (Dessin ds : dessins) {
			if (ds.isContain(X, Y, this)) {
				getFocusedState().setSelectedDessin(ds);
				mw.getEditPanel().display();
				return;
			} else continue;
		}

		getFocusedState().setSelectedDessin(null); // click none

		mw.getCanvas().repaint();
		mw.getEditPanel().display();
	}

/**
 * The Main function.
 */

	public static void main(String[] args) { new Controller(); }

}
