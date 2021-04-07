/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import controller.Controller;
import model.Dessin;

/**
 * The main toolbar on the top of the main window. It contains buttons for
 * selecting graphics you want to draw. After clicking on one button, you can
 * draw that graph on the canvas. One click can only allow drawing one graph
 * at a time. You can only draw the last graph if multiple buttons are clicked
 * at a time. All events will be delivered to the Controller to handle.
 * 
 * @author Delta Group
 */

public class ToolBar extends JToolBar {
    private static final long serialVersionUID = 821766L;
    
	// Tool bar images
    private static final String BT_LINE_IMG = "src\\view\\line.png";
    private static final String BT_OVAL_IMG = "src\\view\\oval.png";
    private static final String BT_RECT_IMG = "src\\view\\rect.png";
    private static final String BT_TEXT_IMG = "src\\view\\text.png";

/**
 * This is the constructor. It creates the toolbar, sets attributes, and
 * equips event listeners.
 * 
 * @param ctr - the Controller
 */

    public ToolBar(final Controller ctr) {
        setFloatable(false);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setPreferredSize(new Dimension(800, 50));

        final JButton btLine = new JButton(new ImageIcon(BT_LINE_IMG));
        btLine.setPreferredSize(new Dimension(32, 32));
        btLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ctr.onToolbarClicked(Dessin.LINE);
            }
        });

        add(btLine);

        final JButton btOval = new JButton(new ImageIcon(BT_OVAL_IMG));
        btOval.setPreferredSize(new Dimension(32, 32));
        btOval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ctr.onToolbarClicked(Dessin.OVAL);
            }
        });

        add(btOval);

        final JButton btRect = new JButton(new ImageIcon(BT_RECT_IMG));
        btRect.setPreferredSize(new Dimension(32, 32));
        btRect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ctr.onToolbarClicked(Dessin.RECT);
            }
        });

        add(btRect);

        final JButton btText = new JButton(new ImageIcon(BT_TEXT_IMG));
        btText.setPreferredSize(new Dimension(32, 32));
        btText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ctr.onToolbarClicked(Dessin.TEXT);
            }
        });

        add(btText);
    }
}
