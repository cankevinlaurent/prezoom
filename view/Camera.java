/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import controller.AnimeCompute;
import controller.Controller;
import model.State;

/**
 * This is the Camera JPanel attached to PlayWindow. It is used for drawing
 * shapes when performing animations.
 * 
 * @author Delta Group
 */

public class Camera extends JPanel {
	private static final long serialVersionUID = 111222L;
	private Controller ctr;
	private Timer timer;
	private int tick;  // how many tick passed
	private int current;
	private int next;
	private ArrayList<State> animeStates;
	public static final int INTERVAL = 50; // ms

/**
 * The constructor
 * 
 * @param c - the controller
 */

	public Camera(Controller c) {
		ctr = c;
		timer = new Timer(INTERVAL, new Animation());
		tick = 0;
		current = 0;
		if (ctr.getStates().size() == 1) next = -1;
		else next = 1;
		animeStates = new AnimeCompute(ctr, INTERVAL).getStates();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (next == -1) {
					ctr.getMainWindow().setVisible(true);
					ctr.getPlayWindow().setVisible(false);
					return;
				}
				tick = 0;
				timer.start();
			}
		});
	}

/**
 * Paint shapes on the screen
 * 
 * @param g - the graphics instance
 */

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		animeStates.get(current * (1000/INTERVAL+1) + tick).setSelectedDessin(null);
		animeStates.get(current * (1000/INTERVAL+1) + tick).display(g, animeStates.get(current * (1000/INTERVAL+1) + tick), ctr);
	}
	
/**
 * Display animations. It is driven by the Timer every INTERVAL ms and updates the
 * data through animeCompute(). It also advances the state after current state
 * played.
 */

	private class Animation implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			tick += 1;
			if (tick >= (1000/INTERVAL)) {
				timer.stop();
				tick = 0;
				if (next != -1) {
					ArrayList<State> states = ctr.getStates();
					current = next;
					if (next == states.size()-1) next = -1;
					else next ++;
				}
			}
			repaint();
		}
	}

}
