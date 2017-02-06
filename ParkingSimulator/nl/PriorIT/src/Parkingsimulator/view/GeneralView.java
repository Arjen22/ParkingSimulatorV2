/**
 * @author Prior IT
 * @version 0.1 Early Alpha 
 */
package nl.PriorIT.src.Parkingsimulator.view;

import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.logic.*;

public abstract class GeneralView extends JPanel {
    private static final long serialVersionUID = 108803;

	protected TestModel testmodel1;

    
    public GeneralView(TestModel testmodel1) {
    this.testmodel1=testmodel1;
	testmodel1.addView(this);
}


public TestModel getModel() {
	return testmodel1;
}

public void updateView() {
	repaint();
}
}
