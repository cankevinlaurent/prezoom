/**
 * THIS FILE WAS PREPARED BY DELTA GROUP. IT WAS COMPLETED BY US ALONG.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import controller.ChangeColorCommand;
import controller.ChangeFilledCommand;
import controller.Command;
import controller.Controller;
import controller.TextEndXChangeCommand;
import controller.TextEndYChangeCommand;
import controller.TextHeightChangeCommand;
import controller.TextTitleChangeCommand;
import controller.TextTransChangeCommand;
import controller.TextWidthChangeCommand;
import model.Dessin;
import model.Line;
import model.Oval;
import model.Rect;
import model.State;
import model.Text;

/**
 * The Edit Panel on the right side of the Main Window. It displays all the
 * attributes of a selected graphical object.
 * 
 * @author Delta Group
 */

public class EditPanel extends JPanel {
	private static final long serialVersionUID = 405343L;
	private boolean manChgComboFilled;
	private Controller ctr;
	private JLabel heading;
	private JLabel labelID;
	private JLabel labelColor;
	private JLabel labelTrans;
	private JLabel labelStartX;
	private JLabel labelStartY;
	private JLabel labelEndX;
	private JLabel labelEndY;
	private JLabel labelWidth;
	private JLabel labelHeight;
	private JLabel labelFilled;
	private JLabel labelTitle;
	private JLabel labelTransit;
	private JTextField textID;
	private JTextField textTrans;
	private JTextField textStartX;
	private JTextField textStartY;
	private JTextField textEndX;
	private JTextField textEndY;
	private JTextField textWidth;
	private JTextField textHeight;
	private JTextField textTitle;
	private JButton colorButton;
	private JComboBox<String> comboFilled;
	private JComboBox<String> comboTransit;

/**
 * The constructor.
 * 
 * @param ctr - the controller
 */

	public EditPanel(Controller c) {
		manChgComboFilled = true;
		ctr = c;
		setPreferredSize(new Dimension(200, 400));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		FlowLayout fl = new FlowLayout();
		fl.setHgap(10);
		fl.setVgap(10);
		setLayout(fl);

		// create the labels
		heading = createLabel("The Attributes", JLabel.CENTER, 200, 50);
		labelID = createLabel("Dessin ID:", JLabel.RIGHT, 60, 20);
		labelColor = createLabel("Color:", JLabel.RIGHT, 60, 20);
		labelTrans = createLabel("Transparent (1-100):", JLabel.RIGHT, 130, 20);
		labelStartX = createLabel("Start X:", JLabel.RIGHT, 60, 20);
		labelStartY = createLabel("Start Y:", JLabel.RIGHT, 60, 20);
		labelEndX = createLabel("End X:", JLabel.RIGHT, 60, 20);
		labelEndY = createLabel("End Y:", JLabel.RIGHT, 60 ,20);
		labelWidth = createLabel("Width:", JLabel.RIGHT, 60, 20);
		labelHeight = createLabel("Height:", JLabel.RIGHT, 60, 20);
		labelFilled = createLabel("Filled:", JLabel.RIGHT, 60, 20);
    	labelTitle = createLabel("Title:", JLabel.RIGHT, 60, 20);
    	labelTransit = createLabel("Transit:", JLabel.RIGHT, 60, 20);

    	// create the TextFields
		textID = createTextField(10, false);
		textTrans = createTextField(4, true);
		textStartX = createTextField(10, false);
		textStartY = createTextField(10, false);
		textEndX = createTextField(10, true);
		textEndY = createTextField(10, true);
		textWidth = createTextField(10, true);
		textHeight = createTextField(10, true);
		textTitle = createTextField(10, true);

		// create the button
		colorButton = createColorButton(114, 20);

    	// create the ComboBox
		String[] itemFilled = {"Yes", "No"};
    	comboFilled = createComboBox(itemFilled, 114, 20);
    	String[] itemTransit = {"None", "Linear"};
    	comboTransit = createComboBox(itemTransit, 114, 20);
    	
        // add event listener
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				State st = ctr.getFocusedState();
				Dessin ds = st.getSelectedDessin();
				Color pickedColor = JColorChooser.showDialog(null, "Choose a color", colorButton.getBackground());
				if (pickedColor == null) return;
				if (st == null || ds == null) return;
				Command cmd = new ChangeColorCommand(ctr, st, ds, colorButton, pickedColor);
				cmd.execute();
				ctr.getCommandManager().storeCommand(cmd);
			}
		});

		textTrans.addFocusListener(new FocusListener() {
			long fStateID = 0;
			long fDessinID = 0;
			String content = "";

			@Override
			public void focusGained(FocusEvent fe) {
				fStateID = ctr.getFocusedState().getID();
				fDessinID = ctr.getFocusedState().getSelectedDessin().getDID();
				content = textTrans.getText();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				State st = ctr.getState(fStateID);
				Dessin ds = st.getDessin(fDessinID);
				float trans = Integer.parseInt(textTrans.getText())/100.0f;
				if (trans < 0.01f || trans > 1.0f) {
					textTrans.setText(content);
					return;
				} else {
					Command cmd = new TextTransChangeCommand(ctr, st, ds, content, trans);
    				cmd.execute();
	    			ctr.getCommandManager().storeCommand(cmd);
				}
			}
		});
		
		textEndX.addFocusListener(new FocusListener() {
			long fStateID = 0;
			long fDessinID = 0;
			String content = "";

			@Override
			public void focusGained(FocusEvent fe) {
				fStateID = ctr.getFocusedState().getID();
				fDessinID = ctr.getFocusedState().getSelectedDessin().getDID();
				content = textEndX.getText();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				State st = ctr.getState(fStateID);
				Dessin ds = st.getDessin(fDessinID);
				int endx;
				try {
					endx = Integer.parseInt(textEndX.getText());
				}
				catch (Exception e) {
					endx = 0;
				}
				if (endx < 0 || endx > 1000) {
					textEndX.setText(content);
					return;
				} else {
					Command cmd = new TextEndXChangeCommand(ctr, st, ds, content, endx);
    				cmd.execute();
	    			ctr.getCommandManager().storeCommand(cmd);
				}
			}
		});
		
		textEndY.addFocusListener(new FocusListener() {
			long fStateID = 0;
			long fDessinID = 0;
			String content = "";

			@Override
			public void focusGained(FocusEvent fe) {
				fStateID = ctr.getFocusedState().getID();
				fDessinID = ctr.getFocusedState().getSelectedDessin().getDID();
				content = textEndY.getText();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				State st = ctr.getState(fStateID);
				Dessin ds = st.getDessin(fDessinID);
				int endy;
				try {
					endy = Integer.parseInt(textEndY.getText());
				}
				catch (Exception e) {
					endy = 0;
				}
				if (endy < 0 || endy > 1000) {
					textEndY.setText(content);
					return;
				} else {
					Command cmd = new TextEndYChangeCommand(ctr, st, ds, content, endy);
    				cmd.execute();
	    			ctr.getCommandManager().storeCommand(cmd);
				}
			}
		});
		
		textWidth.addFocusListener(new FocusListener() {
			long fStateID = 0;
			long fDessinID = 0;
			String content = "";

			@Override
			public void focusGained(FocusEvent fe) {
				fStateID = ctr.getFocusedState().getID();
				fDessinID = ctr.getFocusedState().getSelectedDessin().getDID();
				content = textWidth.getText();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				State st = ctr.getState(fStateID);
				Dessin ds = st.getDessin(fDessinID);
				int width;
				try {
					width = Integer.parseInt(textWidth.getText());
				}
				catch (Exception e) {
					width = 100;
				}
				if (width < 1 || width > 1000) {
					textWidth.setText(content);
					return;
				} else {
					Command cmd = new TextWidthChangeCommand(ctr, st, ds, content, width);
    				cmd.execute();
	    			ctr.getCommandManager().storeCommand(cmd);
				}
			}
		});

		textHeight.addFocusListener(new FocusListener() {
			long fStateID = 0;
			long fDessinID = 0;
			String content = "";

			@Override
			public void focusGained(FocusEvent fe) {
				fStateID = ctr.getFocusedState().getID();
				fDessinID = ctr.getFocusedState().getSelectedDessin().getDID();
				content = textHeight.getText();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				State st = ctr.getState(fStateID);
				Dessin ds = st.getDessin(fDessinID);
				int height;
				try {
					height = Integer.parseInt(textHeight.getText());
				}
				catch (Exception e) {
					height = 100;
				}
				if (height < 1 || height > 1000) {
					textHeight.setText(content);
					return;
				} else {
					Command cmd = new TextHeightChangeCommand(ctr, st, ds, content, height);
    				cmd.execute();
	    			ctr.getCommandManager().storeCommand(cmd);
				}
			}
		});

		textTitle.addFocusListener(new FocusListener() {
			long fStateID = 0;
			long fDessinID = 0;
			String content = "";

			@Override
			public void focusGained(FocusEvent fe) {
				fStateID = ctr.getFocusedState().getID();
				fDessinID = ctr.getFocusedState().getSelectedDessin().getDID();
				content = textTitle.getText();
			}

			@Override
			public void focusLost(FocusEvent fe) {
				State st = ctr.getState(fStateID);
				Dessin ds = st.getDessin(fDessinID);
				String str = textTitle.getText();
				if (str.isEmpty()) {
					textTitle.setText(content);
					return;
				} else {
					Command cmd = new TextTitleChangeCommand(ctr, st, ds, content, str);
    				cmd.execute();
	    			ctr.getCommandManager().storeCommand(cmd);
				}
			}
		});
		
		comboFilled.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuCanceled(PopupMenuEvent pme) {}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
				manChgComboFilled = true;
			}
		});
		
		comboFilled.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED && manChgComboFilled) {
					State st = ctr.getFocusedState();
					Dessin ds = st.getSelectedDessin();
				    Command cmd = new ChangeFilledCommand(ctr, st, ds,
				    		comboFilled.getSelectedItem().toString());
				    cmd.execute();
				    ctr.getCommandManager().storeCommand(cmd);
				    manChgComboFilled = false;
				}
			}
    	});
	}

/**
 * Display attributes when editors select a graphics on the canvas.
 * Also it can update attributes if editors change them.
 */

	public void display() {
		removeAll();
		repaint();
    	revalidate();

    	State st = ctr.getFocusedState();
		Dessin ds = st.getSelectedDessin();
		if (ds == null) return;

		// set TextFields with attributes
		textID.setText(String.valueOf(ds.getDID()));
		textTrans.setText(String.valueOf((int)(ds.getTransparent()*100)));
		textStartX.setText(String.valueOf(ds.getX()));
		textStartY.setText(String.valueOf(ds.getY()));
        colorButton.setBackground(ds.getColor());
        
        if (ds instanceof Line) {
        	textEndX.setText(String.valueOf(((Line) ds).getX2()));
        	textEndY.setText(String.valueOf(((Line) ds).getY2()));
        } else if (ds instanceof Oval) {
        	textWidth.setText(String.valueOf(((Oval) ds).getWidth()));
        	textHeight.setText(String.valueOf(((Oval) ds).getHeight()));
        	if (((Oval) ds).getIsFilled()) comboFilled.setSelectedIndex(0);
    		else comboFilled.setSelectedIndex(1);
        } else if (ds instanceof Rect) {
        	textWidth.setText(String.valueOf(((Rect) ds).getWidth()));
        	textHeight.setText(String.valueOf(((Rect) ds).getHeight()));
        	if (((Rect) ds).getIsFilled()) comboFilled.setSelectedIndex(0);
    		else comboFilled.setSelectedIndex(1);
        } else if (ds instanceof Text) {
        	textTitle.setText(((Text) ds).getTitle());
        }
		
		// add components to the panel
		add(heading);
		add(labelID);
		add(textID);
		add(labelColor);
		add(colorButton);
		add(labelTrans);
		add(textTrans);
		add(labelStartX);
		add(textStartX);
		add(labelStartY);
		add(textStartY);
		if (ds instanceof Line) {
	    	add(labelEndX);
	    	add(textEndX);
	    	add(labelEndY);
	    	add(textEndY);
		} else if (ds instanceof Oval) {
	    	add(labelWidth);
	    	add(textWidth);
	    	add(labelHeight);
	    	add(textHeight);
	    	add(labelFilled);
	    	add(comboFilled);
		} else if (ds instanceof Rect) {
			add(labelWidth);
	    	add(textWidth);
	    	add(labelHeight);
	    	add(textHeight);
	    	add(labelFilled);
	    	add(comboFilled);
		} else if (ds instanceof Text) {	
	    	add(labelTitle);
	    	add(textTitle);
		}
    	add(labelTransit);
    	add(comboTransit);

    	// flush
		repaint();
    	revalidate();
	}

	private JLabel createLabel(String str, int align, int w, int h) {
		JLabel label = new JLabel(str, align);
		label.setPreferredSize(new Dimension(w, h));
		return label;
	}
	
	private JTextField createTextField(int len, boolean enabled) {
		JTextField textField = new JTextField(len);
		textField.setEditable(enabled);
		return textField;
	}
	
	private JButton createColorButton(int w, int h) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(w, h));
		return button;
	}
	
	private JComboBox<String> createComboBox(String[] str, int w, int h) {
		JComboBox<String> comboBox = new JComboBox<String>(str);
		comboBox.setPreferredSize(new Dimension(w, h));
		comboBox.setSelectedIndex(1);
		return comboBox;
	}
}