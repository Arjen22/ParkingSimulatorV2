/**
 * @author PriorIT
 * @version 0.1 Early Alpha
 */
package nl.PriorIT.src.Parkingsimulator.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;

import nl.PriorIT.src.Parkingsimulator.logic.TestModel;


/**
 * 
 * This is the controller for the views and implements the models into the views.
 *
 */
public class Controller extends AbstractController implements ActionListener {
    
    private static final long serialVersionUID = 10802;
    
    // Buttons for the Simulator program
    private JButton startbutton;
    private JButton stopbutton;
    
    public Controller(TestModel testmodel1) {
	super(testmodel1);
	
	    /**
	     * Final Variables that shape the GUI of the simulator
	     */  
	startbutton=new JButton("Start Simulatie");
	startbutton.addActionListener(this);
	stopbutton=new JButton("Stop Simulatie");
	stopbutton.addActionListener(this);

	this.setLayout(null);
	add(startbutton);
	add(stopbutton);
	startbutton.setBounds(229, 10, 70, 30);
	stopbutton.setBounds(319, 10, 70, 30);
	setVisible(true);
	//guiframe.setContentPane(buttonpanel);
	    
    }
    
    /** The controller handles the data between the view and the model.
     * This controller handles all the data of the parking garage simulator.
     * Here are all methods of the controller described from most general to most specified.
     */
    
    // #1M Methods which get the values for the controller to update the view.
    
    public int getParkingGarageFloors() {
	return testmodel1.getNumberOfFloors();
    };
    
    public int getParkingGarageRow() {
	return testmodel1.getNumberOfRows();
    };
    
    public int getParkingGaragePlaces() {
	return testmodel1.getNumberOfPlaces();
    };
    
    public int getParkingGarageOpenSpots() {
	return testmodel1.getNumberOfOpenSpots();
    }
    
    public int getParkingGarageAbonnementsPlaatsen() {
	return testmodel1.getAbonnementsPlaatsen();
    }
    
    /**
     * Updates the views so that the views are up to date with the latest information
     */

    @Override
    public void actionPerformed(ActionEvent argPriorIT) {
	if (argPriorIT.getSource()==startbutton) {
		testmodel1.start();
		//testmodel1.tick();
	}
	
	if (argPriorIT.getSource()==stopbutton) {
		testmodel1.stop();
	}
	
    }
}
