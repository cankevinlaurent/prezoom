/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

import java.util.Vector;

/**
 * It manages commands and supports undo/redo history operations.
 * 
 * @author Delta Group
 */

public class CommandManager {
	private Vector<Command> undoList = new Vector<Command>();
	private Vector<Command> redoList = new Vector<Command>();

/**
 * Store a command.
 * 
 * @param cmd - the command
 */

	public void storeCommand(Command cmd) { undoList.add(cmd); }
	
/**
 * Clear all stored commands in both Undo and Redo lists.
 */

	public void clearAllCommand() {
		undoList.clear();
		redoList.clear();
	}

/**
 * Clear all stored commands in Redo list.
 */

	public void clearRedoCommand() { redoList.clear(); }

/**
 * Determine if undoList is empty
 * 
 * @return status - true if empty
 */

	public boolean isUndoEmpty() { return undoList.isEmpty(); }

/**
 * Determine if redoList is empty
 * 
 * @return status - true if empty
 */

	public boolean isRedoEmpty() { return redoList.isEmpty(); }

/**
 * Undo a command. If the Undo list is not empty, it will remove the last
 * command from the Undo list, execute it, and add it to the Redo list.
 */

	public void undo() {
		if(undoList.isEmpty()) return;
		Command cmd = undoList.get(undoList.size()-1);
		cmd.undo();
		undoList.remove(cmd);
		redoList.add(cmd);
	}

/**
 * Redo a command. If the Redo list is not empty, it will remove the last
 * command from the Redo list, execute it, and add it to the Undo list.
 */

	public void redo() {
		if(redoList.isEmpty()) return;
		Command cmd = redoList.get(redoList.size()-1);
		cmd.execute();
		redoList.remove(cmd);
		undoList.add(cmd);
	}
}
