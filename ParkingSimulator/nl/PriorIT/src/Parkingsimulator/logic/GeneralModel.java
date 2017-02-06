package nl.PriorIT.src.Parkingsimulator.logic;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import nl.PriorIT.src.Parkingsimulator.view.*;

public abstract class GeneralModel extends JFrame 

{

    	/**
     * 
     */
    private static final long serialVersionUID = -8517912210969521589L;
	/**
    	 *  Empty List value for General view objects.
    	 */
	private List<GeneralView> views;
	
	/**
	 *  The General Model creates an array list for the views.
	 */
	public GeneralModel() {
		views=new ArrayList<GeneralView>();
	}
	
	/**
	 * Adds a new view into the array.
	 * @param view
	 */
	public void addView(GeneralView view) {
		views.add(view);
	}
	
	/**
	 * Tells the views that they need to be updated. And updates the view pro view
	 */
	public void notifyViews() {
		for(GeneralView v: views) v.updateView();
	}
}

