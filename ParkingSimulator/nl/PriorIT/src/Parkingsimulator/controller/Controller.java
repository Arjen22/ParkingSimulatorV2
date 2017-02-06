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
    
    /**
     * Updates the views so that the views are up to date with the latest information
     */
    private void updateViews(){
    	simulatorview.tick();
        // Update the car park view.
        simulatorview.updateView();	
    }
    
    private void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }
    
    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    		getParkingGarageOpenSpots()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = getFirstFreeLocation();
            testmodel1.setCarAt(freeLocation, car);
            i++;
        }}
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = testmodel1.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = testmodel1.getFirstLeavingCar();
        }
    }
    
    
    private void carLeavesSpot(Car car){
    	testmodel1.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getParkingGarageFloors(); floor++) {
            for (int row = 0; row < getParkingGarageRow(); row++) {
                for (int place = 0; place < getParkingGaragePlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (testmodel1.getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
    
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);    	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;	            
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    

    @Override
    public void actionPerformed(ActionEvent argPriorIT) {
	if (argPriorIT.getSource()==startbutton) {
		testmodel1.start();
	}
	
	if (argPriorIT.getSource()==stopbutton) {
		testmodel1.stop();
	}
	
    }
}
