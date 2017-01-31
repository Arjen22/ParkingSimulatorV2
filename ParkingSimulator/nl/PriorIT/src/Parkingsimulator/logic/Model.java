package nl.PriorIT.src.Parkingsimulator.logic;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.PriorIT.src.Parkingsimulator.controller.Controller;
import nl.PriorIT.src.Parkingsimulator.logic.GeneralModel;
import nl.PriorIT.src.Parkingsimulator.maths.AdHocCar;
import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.CarQueue;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
import nl.PriorIT.src.Parkingsimulator.maths.ParkingPassCar;
import nl.PriorIT.src.Parkingsimulator.view.CarparkView;
import nl.PriorIT.src.Parkingsimulator.view.*;

public class Model extends GeneralModel implements Runnable {
	private int aantal;
	private boolean run;
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	
	private CarQueue entranceCarQueue; // entrance object var for counting cars that want to enter
	private CarQueue entrancePassQueue; // entrance object var for cars that go through the queue and enter the garage
	private CarQueue paymentCarQueue; // entrance object var for cars that need to pay
	private CarQueue exitCarQueue; // exit object var for cars that want to exit the garage
	private Model SimulatorModel; // simulatormodel object var for the simulator model
	
	private static int day = 0;
	private static int hour = 0;
	private static int minute = 0;
        private int tickPause = 100;
	private static int numberOfFloors = 3;
	private static int numberOfRows = 6;
	private static int numberOfPlaces = 100;
	private static int abonnementsPlaatsen = 40;
	private static int numberOfOpenSpots;
	private int hoeveelheid;
	private Car[][][] cars;
	private CarparkView cpview;
	private Controller simcontroller;
	private JFrame guiframe;
	private JPanel screen;
	
	static int weekDayArrivals= 100; // average number of arriving cars per hour
	static int weekendArrivals = 200; // average number of arriving cars per hour
	static int weekDayPassArrivals= 50; // average number of arriving cars per hour
        static int weekendPassArrivals = 5; // average number of arriving cars per hour

	static int enterSpeed = 3; // number of cars that can enter per minute
	static int paymentSpeed = 7; // number of cars that can pay per minute
	static int exitSpeed = 5; // number of cars that can leave per minute

	
	public Model() {
	        cpview = new CarparkView(SimulatorModel);
	        simcontroller = new Controller(SimulatorModel);
	    	entranceCarQueue = new CarQueue();
	        entrancePassQueue = new CarQueue();
	        paymentCarQueue = new CarQueue();
	        exitCarQueue = new CarQueue();
	      
	        abonnementsPlaatsen = abonnementsPlaatsen < 0 ? 0 : abonnementsPlaatsen;
	        numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
	        hoeveelheid = abonnementsPlaatsen;
	        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
	        

		guiframe=new JFrame("Parkeergarage Simulator");
		screen.setSize(800, 500);
		screen.setLayout(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        Container Controlpanelview = getContentPane();
	        Controlpanelview.add(cpview, BorderLayout.CENTER);
	        Controlpanelview.add(simcontroller, BorderLayout.EAST);
	        pack();
	        screen.setVisible(true);

	        updateViews();
	}
	
	public int getAantal() {
		return aantal;
		
	}
	
	public void setAantal(int aantal) {
		if (aantal>=0 && aantal <=360) {
			this.aantal=aantal;
			notifyViews();
		}
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void stop() {
		run=false;
	}
	
	@Override
	public void run() {
		run=true;
		while(run) {
		    for (int i = 0; i < 10000; i++) {
		            tick();
		        }
			try {
				Thread.sleep(100);
			} catch (Exception e) {} 
		}
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
	    
	    
	    
	    private void updateViews() {
		cpview.tick();
	        // Update the car park view.
		cpview.updateView();
	    }
	    
	    private void carsArriving(){
	    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
	        addArrivingCars(numberOfCars, AD_HOC);    	
	    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
	        addArrivingCars(numberOfCars, PASS);    	
	    }

	    private void carsEntering(CarQueue queue){
	        int i=0;
	        // Remove car from the front of the queue and assign to a parking space.
	    	while (queue.carsInQueue()>0 && 
	    		cpview.getNumberOfOpenSpots()> 0 && 
	    			i<enterSpeed) {
	            Car car = queue.removeCar();
	            Location freeLocation = cpview.getFirstFreeLocation(car.getHasToPay());
	            cpview.setCarAt(freeLocation, car);
	            i++;
	        }
	    }
	    
	    private void carsReadyToLeave(){
	        // Add leaving cars to the payment queue.
	        Car car = cpview.getFirstLeavingCar();
	        while (car!=null) {
	        	if (car.getHasToPay()){
		            car.setIsPaying(true);
		            paymentCarQueue.addCar(car);
	        	}
	        	else {
	        		carLeavesSpot(car);
	        	}
	            car = cpview.getFirstLeavingCar();
	        }
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
	    
	    private void carsLeaving(){
	        // Let cars leave.
	    	int i=0;
	    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
	            exitCarQueue.removeCar();
	            i++;
	    	}	
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
	    
	    private void carLeavesSpot(Car car){
		
		cpview.removeCarAt(car.getLocation());
	        exitCarQueue.addCar(car);
	    }

	}

