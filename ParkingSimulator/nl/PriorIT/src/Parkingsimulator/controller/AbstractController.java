/**
 * 
 */
package nl.PriorIT.src.Parkingsimulator.controller;

import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.logic.Model;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;



/**
 * @author Arjen
 *
 */
public abstract class AbstractController extends JPanel {
    
    private static final long serialVersionUID = 10801;
    protected TestModel SimulatorModel;
	
	public AbstractController(TestModel SimulatorModel) {
		this.SimulatorModel=SimulatorModel;
	}
}
