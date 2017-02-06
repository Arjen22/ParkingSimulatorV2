/**
 * @author PriorIT
 * @version 0.1 Early Alpha
 */
package nl.PriorIT.src.Parkingsimulator.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

import nl.PriorIT.src.Parkingsimulator.maths.AdHocCar;
import nl.PriorIT.src.Parkingsimulator.maths.ParkingPassCar;
import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;
import nl.PriorIT.src.Parkingsimulator.maths.CarQueue;
import nl.PriorIT.src.Parkingsimulator.view.TestView;


/**
 * 
 * This is the controller for the simulator view and implements the model into the panel.
 *
 */
public class Controller extends AbstractController implements ActionListener {
    
    private static final long serialVersionUID = 10802;
    
    /*
     * Variables for getting the size of the parking garage and it's spots for updating the view.
     */
    private static final String AD_HOC = "1";
    private static final String PASS = "2";
    
    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    
    private CarQueue entranceCarQueue; // entrance object var for counting cars that want to enter
    private CarQueue entrancePassQueue; // entrance object var for cars that go through the queue and enter the garage
    private CarQueue paymentCarQueue; // entrance object var for cars that need to pay
    private CarQueue exitCarQueue; // exit object var for cars that want to exit the garage
    private TestView simulatorview; // simulatorview object var for checking if the simulator view is true
    
    
    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;
    
    // Buttons for the Simulator program
    private JButton startbutton;
    private JButton stopbutton;
    
    public Controller(TestModel testmodel1) {
	super(testmodel1);
	//entranceCarQueue = new CarQueue();
        //entrancePassQueue = new CarQueue();
        //paymentCarQueue = new CarQueue();
        //exitCarQueue = new CarQueue();
	
	
	    /**
	     * Final Variables that shape the GUI of the simulator
	     */  
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
    
    private void updateViews(){
    	simulatorview.tick();
        // Update the car park view.
        simulatorview.updateView();	
    }

        private void tick() {
    	testmodel1.advanceTime();
    	testmodel1.handleExit();
    	updateViews();
    	
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	testmodel1.handleEntrance();
    }

    @Override
    public void actionPerformed(ActionEvent argPriorIT) {
	if (argPriorIT.getSource()==startbutton) {
		testmodel1.start();
		tick();
	}
	
	if (argPriorIT.getSource()==stopbutton) {
		testmodel1.stop();
	}
	
    }
}
