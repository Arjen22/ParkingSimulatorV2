/**
 * 
 */
package nl.PriorIT.src.Parkingsimulator.controller;

import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.logic.Model;



/**
 * @author Arjen
 *
 */
public abstract class AbstractController extends JPanel {
    
    private static final long serialVersionUID = 10801;
    protected Model simulatormodel;
	
	public AbstractController(Model simulatormodel) {
		this.simulatormodel=simulatormodel;
	}
}
