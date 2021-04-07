/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package controller;

/**
 * The interface which supports Undo/Redo operations.
 * 
 * @author Delta Group
 */

public abstract interface Command {

/**
 * The command itself.
 */

	public abstract void execute();

/**
 * The Undo operation
 */

	public abstract void undo();
}
