/**
 * 
 */
package nl.PriorIT.src.Parkingsimulator.controller;

import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.features.Model;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;


public abstract class AbstractController extends JPanel {
    
    private static final long serialVersionUID = 10801;
    protected TestModel testmodel1;
	
	public AbstractController(TestModel testmodel1) {
		this.testmodel1=testmodel1;
	}
}
