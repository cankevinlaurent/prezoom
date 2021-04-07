/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import controller.Controller;
import model.State;

/**
 * The Thumbnail panel on the left side of the Main Window. It is inside a
 * JScrollPane on the left side of the Main Window. It uses the states
 * information to draw all thumbnials, each of which reflects one state along
 * with its graphics.
 * 
 * @author Delta Group
 */

public class ThumbnailPanel extends JPanel {
    private static final long serialVersionUID = 375497L;
    private final Controller ctr;

/**
 * The constructor.
 * 
 * @param c - the controller
 */

    public ThumbnailPanel(final Controller c) {
    	ctr = c;
        setPreferredSize(new Dimension(80, 1000));
        setLayout(new FlowLayout());
    }
    
/**
 * Display all states in forms of thumbnails. Every time, it clears all current
 * Thumbnails on the Thumbnail panel, and then focuses one thumbnail. The
 * thumbnail is created via the BufferedImage.
 */

    public void display() {
        removeAll();

    	for (State st : ctr.getStates()) {
    		BufferedImage bImage = new BufferedImage(1920, 1920,
    				BufferedImage.TYPE_INT_RGB);
    		Graphics2D g2d = bImage.createGraphics();
    		g2d.setBackground(Color.WHITE);
    		g2d.fillRect(0, 0, 1920, 1920);
    		Thumbnail tn = new Thumbnail(ctr, st.getID());
    		st.display(g2d, st, ctr);
    		tn.setIcon(new ImageIcon(bImage.getScaledInstance(80, 80,
    				Image.SCALE_DEFAULT)));

    		if (ctr.getFocusedID() == tn.getID())
    			tn.setBorder(BorderFactory.createLineBorder(Color.RED));

    		add(tn, BorderLayout.PAGE_START);
    	}
    	repaint();
    	revalidate();
    }
}
