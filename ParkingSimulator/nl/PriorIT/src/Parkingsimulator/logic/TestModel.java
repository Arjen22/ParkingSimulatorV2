package nl.PriorIT.src.Parkingsimulator.logic;


import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Random;

import nl.PriorIT.src.Parkingsimulator.controller.Controller;
import nl.PriorIT.src.Parkingsimulator.maths.AdHocCar;
import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.CarQueue;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
import nl.PriorIT.src.Parkingsimulator.maths.ParkingPassCar;
import nl.PriorIT.src.Parkingsimulator.view.GeneralView;
import nl.PriorIT.src.Parkingsimulator.view.TestView;

public class TestModel extends GeneralModel implements Runnable {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private int aantal;
	private boolean run;
	
	private GeneralView cpview;
	private int numberOfFloors;
	private int numberOfRows;
	private int numberOfPlaces;
	private int numberOfOpenSpots;
	private Car[][][] cars;
	private int abonnementsPlaatsen;
	private Location laatsteplek;
	private int hoeveelheidPlaatsen;
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private Controller controller;
    
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
	
	
	// private static int floornumber = 0; NOT BEING USED YET Has to be implemented for management information view
	/* private int aantalReserveringen;
	private int reservering;
	private int betalen; */
    	
    	public TestModel(int numberOfFloors,int numberOfRows,int numberOfPlaces, int abonnementsPlaatsen) {
    		this.numberOfFloors = numberOfFloors;
    		this.numberOfRows = numberOfRows;
    		this.numberOfPlaces = numberOfPlaces;
    		this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
    		this.abonnementsPlaatsen = abonnementsPlaatsen;
    		hoeveelheidPlaatsen = abonnementsPlaatsen;
    		cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
    		entranceCarQueue = new CarQueue();
    		entrancePassQueue = new CarQueue();
    		paymentCarQueue = new CarQueue();
    		exitCarQueue = new CarQueue();
    	}
	
	// returns the number of views.
	public int getAantal() {
		return aantal;
	}
	
	// sets the number of views and checks if the number of views is greater than 1 and smaller than 360 and if true will notify the views to update and repaint.
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
	
	    
	public void updateView() {
		cpview.updateView();
	}
	
	@Override
	public void run() {
		run=true;
		while(run) {
			setAantal(getAantal()+1);
			try {
				Thread.sleep(100);
			} catch (Exception e) {} 
		}
	}
	 
	public int getNumberOfFloors() {
	    return numberOfFloors;
	}

	public int getNumberOfRows() {
	    return numberOfRows;
	}

	public int getNumberOfPlaces() {
	    return numberOfPlaces;
	}

	public int getNumberOfOpenSpots(){
	    return numberOfOpenSpots;
	}

	public int getAbonnementsPlaatsen() {
	    return abonnementsPlaatsen;
	}
	
	   private void updateViews(){
	    	simulatorview.tick();
	        // Update the car park view.
	        simulatorview.updateView();	
	    }
	   
	public void tick() {
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
    
    public void advanceTime(){
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

    public void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    public void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        /**
         *  Remove car from the front of the queue and assign to a parking space.
         */
    	while (queue.carsInQueue()>0 && 
    			this.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
    	    		Car car = queue.removeCar();
    	    		Location freeLocation = this.getFirstFreeLocation();
    	    		this.setCarAt(freeLocation, car);
    	    		i++;
        }
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = this.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = this.getFirstLeavingCar();
        }
    }
    
    
    private void carLeavesSpot(Car car){
    	this.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            carLeavesSpot(car);
            i++;
    	}
    }
    
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < controller.getParkingGarageFloors(); floor++) {
            for (int row = 0; row < controller.getParkingGarageRow(); row++) {
                for (int place = 0; place < controller.getParkingGaragePlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (this.getCarAt(location) == null) {
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

	public Car getCarAt(Location location) {
	    if (!locationIsValid(location)) {
		return null;
	    }
	    return cars[location.getFloor()][location.getRow()][location.getPlace()];
	} 

	public boolean setCarAt(Location location, Car car) {
	    if (!locationIsValid(location)) {
		return false;
	    }
	    Car oldCar = getCarAt(location);
	    if (oldCar == null) {
		cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
		car.setLocation(location);
		numberOfOpenSpots--;
		return true;
	    }
	    return false;
	}

	public Car removeCarAt(Location location) {
	    if (!locationIsValid(location)) {
		return null;
	    }
	    Car car = getCarAt(location);
	    if (car == null) {
		return null;
	    }
	    cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
	    car.setLocation(null);
	    numberOfOpenSpots++;
	    return car;
	} 

	public Car getFirstLeavingCar() {
	    for (int floor = 0; floor < getNumberOfFloors(); floor++) {
		for (int row = 0; row < getNumberOfRows(); row++) {
		    for (int place = 0; place < getNumberOfPlaces(); place++) {
			Location location = new Location(floor, row, place);
			Car car = getCarAt(location);
			if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
			    return car;
			}
		    }
		}
	    }
	    return null;
	}

	private boolean locationIsValid(Location location) {
	int floor = location.getFloor();
	int row = location.getRow();
	int place = location.getPlace();
	if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
	   return false;
	}
	return true;
	} 

	public Location getFirstFreeLocation(boolean paying, boolean reservation) {
		
	    for (int floor = 0; floor < getNumberOfFloors(); floor++) {
	        for (int row = 0; row < getNumberOfRows(); row++) {
	            for (int place = 0; place < getNumberOfPlaces(); place++) {
	            	int pay = (paying) ? 1 : 0;
	            	int res = (reservation) ? 1 : 0;
	            	switch(pay) { //betalen: normal car and reservation
	            	case 1: //pay==true, reservation and normal car.
	            		switch(res) {
	            		case 1: /*if (reservation ==true) & paying==true*/ { //reservation car
	            			if(floor <= laatsteplek.getFloor()) { floor = laatsteplek.getFloor();
	                			if(row <= laatsteplek.getRow()) { row = laatsteplek.getRow() + 2;
	                				if(place<= laatsteplek.getPlace()) { place = laatsteplek.getPlace() + 1;
	                				}//place
	                				}//row
	            			}//floor
	            		}//einde case 1 res
	            		case 0: /* if (reservation ==false) & paying==true */{ //normal car
	            			if(floor <= laatsteplek.getFloor()) { floor = laatsteplek.getFloor();
	                			if(row <= laatsteplek.getRow()) { row = laatsteplek.getRow();
	                				if(place<= laatsteplek.getPlace()) { place = laatsteplek.getPlace() + 1;
	                				}//place
	                				}//row
	            			}//floor
	            		}//einde case 0 res
	            		}//einde switch res
	            	case 0: /* if (paying==false) */{ //abonnementshouders
	        			if(floor <= laatsteplek.getFloor()) { floor = laatsteplek.getFloor();
	            			if(row <= laatsteplek.getRow()) { row = laatsteplek.getRow();
	            				if(place<= laatsteplek.getPlace()) { place = laatsteplek.getPlace() + 1;
	            				}//place
	            				}//row
	        			}//floor	
	            	}//einde switch pay
	            	
	                Location location = new Location(floor, row, place);
	                Location check = getCarAt(location) == null ? location : null;
	                if(check != null) {
	                	return location;
	                }
	            }
	        }
	    }
	    }
	    return null;
	} 
	}

/*	if (paying == true) {
if(floor <= laatsteplek.getFloor()) {
	floor = laatsteplek.getFloor();
	if(row <= laatsteplek.getRow()) {
		row = laatsteplek.getRow();
		if(place<= laatsteplek.getPlace()) {
			place = laatsteplek.getPlace() + 1;
		}                				            				
	}                				
}
}
*/