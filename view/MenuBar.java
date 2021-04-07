/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Command;
import controller.InsertStateCommand;
import controller.Controller;
import controller.DeleteDessinCommand;
import controller.DeleteStateCommand;
import controller.PasteDessinCommand;
import model.Dessin;
import model.State;

/**
 * The main menu on the top of main window. It supports accelerator keys. Only
 * the items under the Edit menu can perform undo/redo operations.
 * 
 * @author Delta Group
 */

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 800719L;
	private long stateID;   // used for copy/paste dessin to other state
	private long dessinID;  // used for copy/paste dessin to other state

/**
 * The constructor.
 * It creates the menus with their items
 * 
 * @param ctr - the Controller
 */

	public MenuBar(Controller ctr) {
		stateID = 0;
		dessinID = 0;

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem newItem = new JMenuItem("New");
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		file.add(newItem);
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onNewFileClicked();
			}
		});
		
		JMenuItem openItem = new JMenuItem("Open...");
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		file.add(openItem);
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onOpenFileClicked();
			}
		});

		file.addSeparator();
		
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		file.add(saveItem);
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onSaveFileClicked();
			}
		});

		file.addSeparator();
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
		file.add(exitItem);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onExitClicked();
			}
		});

		add(file);
		
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem insertItem = new JMenuItem("Insert State");
		insertItem.setAccelerator(KeyStroke.getKeyStroke("ctrl I"));
		edit.add(insertItem);
		insertItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long stateID = System.currentTimeMillis();
				Command cmd = new InsertStateCommand(ctr, stateID);
				cmd.execute();
				ctr.getCommandManager().storeCommand(cmd);
			}
		});
		
		JMenuItem delItem = new JMenuItem("Delete State");
		delItem.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
		edit.add(delItem);
		delItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Command cmd = new DeleteStateCommand(ctr);
				cmd.execute();
				ctr.getCommandManager().clearRedoCommand();
				ctr.getCommandManager().storeCommand(cmd);
			}
		});

		edit.addSeparator();

		JMenuItem copyDessinItem = new JMenuItem("Copy Dessin");
		copyDessinItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		edit.add(copyDessinItem);
		copyDessinItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				State st = ctr.getFocusedState();
				if (st == null) return;
				stateID = st.getID();
				Dessin ds = st.getSelectedDessin();
				if (ds == null) return;
				dessinID = ds.getDID();
			}
		});
		
		JMenuItem pasteDessinItem = new JMenuItem("Paste Dessin");
		pasteDessinItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		edit.add(pasteDessinItem);
		pasteDessinItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (ctr.getState(stateID) == null) return;
				if (ctr.getState(stateID).getDessin(dessinID) == null) return;

				State st = ctr.getFocusedState();
				if (st == null) return;
				if (st.getID() == stateID) {
					JOptionPane.showMessageDialog(null, "Cannot paste to the same state.");
					return;
				}
				if (st.getDessin(dessinID) != null) {
					JOptionPane.showMessageDialog(null, "Same dessin in the state.");
					return;
				}
				
				Command cmd = new PasteDessinCommand(ctr, stateID, dessinID, st.getID());
				cmd.execute();
				ctr.getCommandManager().storeCommand(cmd);
			}
		});
		
		JMenuItem delDessinItem = new JMenuItem("Delete Dessin");
		delDessinItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		edit.add(delDessinItem);
		delDessinItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				State st = ctr.getFocusedState();
				if (st == null) return;
				Dessin selectedDessin = st.getSelectedDessin();
				if (selectedDessin == null) return;
				Command cmd = new DeleteDessinCommand(ctr, st, selectedDessin);
				cmd.execute();
				ctr.getCommandManager().clearRedoCommand();
				ctr.getCommandManager().storeCommand(cmd);
			}
		});

		edit.addSeparator();

		JMenuItem undoItem = new JMenuItem("Undo");
		undoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		edit.add(undoItem);
		undoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ctr.getCommandManager().isUndoEmpty())
					JOptionPane.showMessageDialog(null, "Undo list is empty");
				else
					ctr.getCommandManager().undo();
			}
		});

		JMenuItem redoItem = new JMenuItem("Redo");
		redoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		edit.add(redoItem);
		redoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ctr.getCommandManager().isRedoEmpty())
					JOptionPane.showMessageDialog(null, "Redo list is empty");
				else
					ctr.getCommandManager().redo();
			}
		});

		add(edit);
		
		JMenu play = new JMenu("Show");
		play.setMnemonic(KeyEvent.VK_S);
		
		JMenuItem playItem = new JMenuItem("Play");
		playItem.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		play.add(playItem);
		playItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onPlayClicked();
			}
		});
		
		add(play);
		
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
		help.add(aboutItem);
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctr.onAboutClicked();
			}
		});

		add(help);
	}
}
