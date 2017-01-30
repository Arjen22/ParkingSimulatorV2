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


/**
 * 
 * This is the controller for the simulator view and implements the model into the panel.
 *
 */
public class Controller extends AbstractController implements ActionListener {
    
    private static final long serialVersionUID = 10802;
    
    public Controller(Model simulatormodel) {
	  /**
	     * Final Variables that shape the GUI of the simulator
	     */
	    super(simulatormodel);    
	    final int wwidth = 800;
	    final int wheight = 600;
	    
	    /**
	     * The main constructor of the gui, determines the the screen layout of the buttons and screen properties and also the display through a height and width in pixels.
	     * @param width 
	     * @param height
	     * @return nothing
	     */
	    
	    /**
	     * Makes a new JFrame and calls it PsimGui
	     */
	    JFrame guiframe = new JFrame("PsimGui");
	    guiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    guiframe.pack();
	    guiframe.setSize(wwidth, wheight);
	    guiframe.setVisible(true);
	    
	    JPanel buttonpanel = new JPanel(new BorderLayout());
	    JButton startbutton = new JButton("Start");
	    JButton advbutton = new JButton("Advance");
	    JButton stopbutton = new JButton("Stop");
	    buttonpanel.add(advbutton, BorderLayout.CENTER);
	    buttonpanel.add(startbutton, BorderLayout.EAST);
	    buttonpanel.add(stopbutton, BorderLayout.SOUTH);
	    guiframe.setContentPane(buttonpanel);
	    
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
    }
}
