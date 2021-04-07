/**
 *  THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package model;

import java.util.ArrayList;

/**
 * This is the Model of MVC.
 * It contains core variables (usually global variables) such as the states.
 * 
 * @author Delta Group
 */

public class Model {
	private ArrayList<State> states;         // all states that a program has
	private State focusedState;              // which state is focused
	private boolean modified;                // if something be modified

/**
 * The constructor.
 */

	public Model() {
		states = new ArrayList<State>();
		focusedState = null;
		modified = false;
	}

/**
 * Get States list
 * 
 * @return states - the ArrayList<State>
 */

	public ArrayList<State> getStates() { return states; }

/**
 * Set States list
 * 
 * @param st - the ArrayList<State>
 */

	public void setStates(ArrayList<State> st) { states = st; }

/**
 * Append one state to a states list
 * 
 * @param st - the state being appended
 */

    public void appendState(State st) { states.add(st); }

/**
 * Get focusedState
 * 
 * @return focusedState - the focused state
 */

	public State getFocusedState() { return focusedState; }

/**
 * Set focusedState
 * 
 * @param fs - the focused state
 */

	public void setFocusedState(State fs) { focusedState = fs; }
	
/**
 * Get modified
 * 
 * @return modified - true if modified
 */

	public boolean getModifed() { return modified; }

/**
 * Set modified
 * 
 * @param m - true if modified
 */

	public void setModified(boolean m) { modified = m; }

}