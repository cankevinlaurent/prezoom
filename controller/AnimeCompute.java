/**
 * 
 */
package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.Delta;
import model.Dessin;
import model.Line;
import model.Oval;
import model.Rect;
import model.State;
import model.Text;

/**
 * This is used for computing the difference between two states
 * 
 * @author Delta Group
 */

public class AnimeCompute {
	private Controller ctr;
	private ArrayList<State> states;
	private int interval;
	
	public AnimeCompute(Controller c, int itvl) {
		ctr = c;
		states = new ArrayList<State>();
		interval = itvl;
		createAllStates();
	}

	private void createAllStates(){
		for(State st : ctr.getStates()) {
			ArrayList<State> sts = createSeriesStates(st, getNextState(st));
			for (State s : sts) states.add(s);
		}
	}

	private ArrayList<State> createSeriesStates(State prev, State next){
		ArrayList<State> states = new ArrayList<State>();
		for (int i=0; i<(1000/interval+1); i++) states.add(new State(0L));
		for (Dessin ds : prev.getDessins()) {
			for (int i=0; i< (1000/interval+1); i++) {
				Dessin fDessin = findDessin(ds, next);
				Delta delta = computeDessin(ds, fDessin);
				Dessin tmpDessin = createDessin(ds, delta, i);
				states.get(i).appendDessin(tmpDessin);
			}
		}
		return states;
	}

	private Dessin findDessin(Dessin prev, State nextState) {
		if (nextState == null) return null;
		if (nextState.getDessins().isEmpty()) return null;
		for (Dessin ds : nextState.getDessins()) {
			if (prev.getDID() == ds.getDID()) return ds;
		}
		return null;
	}
	
	private State getNextState(State prev) {
		for(int i=0; i < ctr.getStates().size(); i ++) {
			if (prev.getID() == ctr.getStates().get(i).getID()) {
				if (i == ctr.getStates().size()-1) return null;
				else return ctr.getStates().get(i+1);
			}
		}
		return null;
	}
	
	private Delta computeDessin(Dessin prev, Dessin next) {
		Delta delta = new Delta();
		
		if (next == null) {
			delta.X = 0;
			delta.Y = 0;
			delta.red = 0;
			delta.green = 0;
			delta.blue = 0;
			delta.transparent = 0 - prev.getTransparent();
			if (prev instanceof Line) {
				delta.type = Dessin.LINE;
				delta.X2 = 0;
				delta.Y2 = 0;
			} else if (prev instanceof Oval) {
				delta.type = Dessin.OVAL;
				delta.width = 0;
				delta.height = 0;
				delta.filled = ((Oval) prev).getIsFilled();
			} else if (prev instanceof Rect) {
				delta.type = Dessin.RECT;
				delta.width = 0;
				delta.height = 0;
				delta.filled = ((Rect) prev).getIsFilled();
			} else if (prev instanceof Text) {
				delta.type = Dessin.TEXT;
				delta.title = ((Text) prev).getTitle();
			}
			return delta;
		} else {
			delta.X = next.getX() - prev.getX();
			delta.Y = next.getY() - prev.getY();
			delta.red = next.getColor().getRed() - prev.getColor().getRed();
			delta.green = next.getColor().getGreen() - prev.getColor().getGreen();
			delta.blue = next.getColor().getBlue() - prev.getColor().getBlue();
			delta.transparent = next.getTransparent() - prev.getTransparent();
			if (prev instanceof Line) {
				delta.type = Dessin.LINE;
				delta.X2 = ((Line) next).getX2() - ((Line) prev).getX2();
				delta.Y2 = ((Line) next).getY2() - ((Line) prev).getY2();
			} else if (prev instanceof Oval) {
				delta.type = Dessin.OVAL;
				delta.width = ((Oval) next).getWidth() - ((Oval) prev).getWidth();
				delta.height = ((Oval) next).getHeight() - ((Oval) prev).getHeight();
				delta.filled = ((Oval) prev).getIsFilled();
			} else if (prev instanceof Rect) {
				delta.type = Dessin.RECT;
				delta.width = ((Rect) next).getWidth() - ((Rect) prev).getWidth();
				delta.height = ((Rect) next).getHeight() - ((Rect) prev).getHeight();
				delta.filled = ((Rect) prev).getIsFilled();
			} else if (prev instanceof Text) {
				delta.type = Dessin.TEXT;
				delta.title = ((Text) prev).getTitle();
			}
			return delta;
		}
	}

    private Dessin createDessin(Dessin prev, Delta delta, int i) {
    	Dessin ds = null;
    	if (prev instanceof Line) {
    		ds = new Line(0L, Color.BLACK, 0, 0, 0, 0);
    		ds.setX(prev.getX() + delta.X/(1000/interval)*i);
    		ds.setY(prev.getY() + delta.Y/(1000/interval)*i);

    		float red = prev.getColor().getRed() + delta.red/(1000/interval)*i ;
    		float green = prev.getColor().getGreen() + delta.green/(1000/interval)*i;
    		float blue = prev.getColor().getBlue() + delta.blue/(1000/interval)*i;
    		ds.setTransparent(prev.getTransparent() + delta.transparent/(1000/interval)*i);
    		ds.setColor(new Color(red/255f, green/255f, blue/255f));

    		((Line) ds).setX2(((Line) prev).getX2() + delta.X2/(1000/interval)*i);
    		((Line) ds).setY2(((Line) prev).getY2() + delta.Y2/(1000/interval)*i);
    	} else if (prev instanceof Oval) {
    		ds = new Oval(0L, Color.BLACK, 0, 0, 0, 0, true);
    		ds.setX(prev.getX() + delta.X/(1000/interval)*i);
    		ds.setY(prev.getY() + delta.Y/(1000/interval)*i);

    		float red = prev.getColor().getRed() + delta.red/(1000/interval)*i ;
    		float green = prev.getColor().getGreen() + delta.green/(1000/interval)*i;
    		float blue = prev.getColor().getBlue() + delta.blue/(1000/interval)*i;
    		ds.setTransparent(prev.getTransparent() + delta.transparent/(1000/interval)*i);
    		ds.setColor(new Color(red/255f, green/255f, blue/255f));
    		
    		((Oval) ds).setWidth(((Oval) prev).getWidth() + delta.width/(1000/interval)*i);
    		((Oval) ds).setHeight(((Oval) prev).getHeight() + delta.height/(1000/interval)*i);
    		((Oval) ds).setIsFilled(delta.filled);
    	} else if (prev instanceof Rect) {
    		ds = new Rect(0L, Color.BLACK, 0, 0, 0, 0, true);
    		ds.setX(prev.getX() + delta.X/(1000/interval)*i);
    		ds.setY(prev.getY() + delta.Y/(1000/interval)*i);

    		float red = prev.getColor().getRed() + delta.red/(1000/interval)*i ;
    		float green = prev.getColor().getGreen() + delta.green/(1000/interval)*i;
    		float blue = prev.getColor().getBlue() + delta.blue/(1000/interval)*i;
    		ds.setTransparent(prev.getTransparent() + delta.transparent/(1000/interval)*i);
    		ds.setColor(new Color(red/255.0f, green/255.0f, blue/255.0f));
    		
    		((Rect) ds).setWidth(((Rect) prev).getWidth() + delta.width/(1000/interval)*i);
    		((Rect) ds).setHeight(((Rect) prev).getHeight() + delta.height/(1000/interval)*i);
    		((Rect) ds).setIsFilled(delta.filled);
    	} else if (prev instanceof Text) {
    		ds = new Text(0L, Color.BLACK, 0, 0, ((Text) prev).getTitle());
    		ds.setX(prev.getX() + delta.X/(1000/interval)*i);
    		ds.setY(prev.getY() + delta.Y/(1000/interval)*i);

    		float red = prev.getColor().getRed() + delta.red/(1000/interval)*i ;
    		float green = prev.getColor().getGreen() + delta.green/(1000/interval)*i;
    		float blue = prev.getColor().getBlue() + delta.blue/(1000/interval)*i;
    		ds.setTransparent(prev.getTransparent() + delta.transparent/(1000/interval)*i);
    		ds.setColor(new Color(red/255f, green/255f, blue/255f));
    		((Text) ds).setTitle(delta.title);
    	}
		return ds;
    }

    public ArrayList<State> getStates(){ return states; }
}
