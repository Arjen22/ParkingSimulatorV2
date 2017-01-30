/**
 * @author Prior IT
 * @version 0.1 Early Alpha 
 */
package nl.PriorIT.src.Parkingsimulator.view;

import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.logic.Model;

public abstract class GeneralView extends JPanel {
    private static final long serialVersionUID = 108803;
    protected Model SimulatorModel;

    
    public GeneralView(Model SimulatorModel) {
	this.SimulatorModel=SimulatorModel;
	SimulatorModel.addView(this);
}

public Model getModel() {
	return SimulatorModel;
}

public void updateView() {
	repaint();
}
}
