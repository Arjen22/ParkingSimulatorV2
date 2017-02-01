/**
 * @author PriorIT
 * @version 0.1 Early Alpha
 */
package nl.PriorIT.src.Parkingsimulator.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.logic.Model;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;


/**
 * 
 * This is the controller for the simulator view and implements the model into the panel.
 *
 */
public class Controller extends AbstractController implements ActionListener {
    
    private static final long serialVersionUID = 10802;
    
    private JButton startbutton;
    private JButton stopbutton;
    
    public Controller(TestModel SimulatorModel) {
	  /**
	     * Final Variables that shape the GUI of the simulator
	     */
	    super(SimulatorModel);  
		startbutton=new JButton("Start Simulatie");
		startbutton.addActionListener(this);
		stopbutton=new JButton("Stop Simulatie");
		stopbutton.addActionListener(this);
	    /**
	     * The main constructor of the gui, determines the the screen layout of the buttons and screen properties and also the display through a height and width in pixels.
	     * @param width 
	     * @param height
	     * @return nothing
	     */
	    
	    /**
	     * Makes a new JFrame and calls it PsimGui
	     */
	    /*
	    JFrame guiframe = new JFrame("PsimGui");
	    guiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    guiframe.pack();
	    guiframe.setSize(wwidth, wheight);
	    guiframe.setVisible(true);
	    */

	    this.setLayout(null);
	    add(startbutton);
	    add(stopbutton);
	    startbutton.setBounds(229, 10, 70, 30);
            stopbutton.setBounds(319, 10, 70, 30);
	    setVisible(true);
	    //guiframe.setContentPane(buttonpanel);
	    
    }

    @Override
    public void actionPerformed(ActionEvent argPriorIT) {
	if (argPriorIT.getSource()==startbutton) {
		SimulatorModel.start();
	}
	
	if (argPriorIT.getSource()==stopbutton) {
		SimulatorModel.stop();
	}
	
    }
}
