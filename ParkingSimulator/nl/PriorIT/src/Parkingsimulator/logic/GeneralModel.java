package nl.PriorIT.src.Parkingsimulator.logic;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import javax.swing.JFrame;

import nl.PriorIT.src.Parkingsimulator.view.*;

public abstract class GeneralModel extends JFrame {
=======
import nl.PriorIT.src.Parkingsimulator.view.*;

public abstract class GeneralModel {
>>>>>>> branch 'master' of https://github.com/Arjen22/ParkingSimulator
  
	private List<GeneralView> views;
	
	public GeneralModel() {
		views=new ArrayList<GeneralView>();
	}
	
	public void addView(GeneralView view) {
		views.add(view);
	}
	
	public void notifyViews() {
		for(GeneralView v: views) v.updateView();
	}
}

