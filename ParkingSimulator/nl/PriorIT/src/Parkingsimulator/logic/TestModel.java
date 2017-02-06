package nl.PriorIT.src.Parkingsimulator.logic;


import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
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
	private Location laatsteplekAbbo;
	private int hoeveelheidPlaatsen;
	
	
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
    		cpview = new TestView(this);
    		
    	    updateView();
    	    
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
	
	   public void updateView() {
		cpview.updateView();
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
	
	public void tick() {
	for (int floor = 0; floor < getNumberOfFloors(); floor++) {
	   for (int row = 0; row < getNumberOfRows(); row++) {
	       for (int place = 0; place < getNumberOfPlaces(); place++) {
	           Location location = new Location(floor, row, place);
	           Car car = getCarAt(location);
	           if (car != null) {
	               car.tick();
	           }
	       }
	   }
	}
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



	public Location getFirstFreeLocation(boolean paying,boolean reservation) {
	
	    for (int floor = 0; floor < getNumberOfFloors(); floor++) {
		for (int row = 0; row < getNumberOfRows(); row++) {
		    for (int place = 0; place < getNumberOfPlaces(); place++) { 
			Location location = new Location(floor, row, place); /*HIER EEN LOCATIE AANMAKEN*/
			if (paying == false && reservation == false) { //ParkingPassCar
			    for (int floor1 = 0; floor < getNumberOfFloors(); floor++) {
            	            	for (int row1 = 0; row < getNumberOfRows(); row++) {
            	                for (int place1 = 0; place < getNumberOfPlaces(); place++) {
            	                    if(floor1 <= laatsteplekAbbo.getFloor() && row1 <= laatsteplekAbbo.getRow() && place1<= laatsteplekAbbo.getPlace()) {
            	                	floor = laatsteplekAbbo.getFloor();
            	                	row = laatsteplekAbbo.getRow();
            	                	place = laatsteplekAbbo.getPlace() + 1;
            	                	Location location1 = new Location(floor, row, place);
            	                	Location check1 = getCarAt(location) == null ? location : null;
            	                	if(check1 != null) {
            	                	    return location1;
            	                	}
            	                    }
            	                }
            	            }
            		 }
			} 
			else if (paying == true && reservation == true) { //ReservationCar
            			for (int floor2 = 0; floor < getNumberOfFloors(); floor++) {
            			    for (int row2 = 0; row < getNumberOfRows(); row++) {
            				for (int place2 = 0; place < getNumberOfPlaces(); place++) {
            				    if(floor2 <= laatsteplekAbbo.getFloor() && row2 <= laatsteplekAbbo.getRow() && place2 <= laatsteplekAbbo.getPlace()) {
            					floor = laatsteplekAbbo.getFloor();
            					row = laatsteplekAbbo.getRow() + 3;
            					place = laatsteplekAbbo.getPlace() + 1;
            					Location location2 = new Location(floor, row, place);
            					Location check2 = getCarAt(location) == null ? location : null;
            					if(check2 != null) {
            					    return location2;
            					}
            				    }
            				}
            			    }
            			} 
			} 
			else { //NORMAL CAR
			    for (int floor3 = 0; floor < getNumberOfFloors(); floor++) {
				for (int row3 = 0; row < getNumberOfRows(); row++) {
				    for (int place3 = 0; place < getNumberOfPlaces(); place++) {
					if(floor3 <= laatsteplekAbbo.getFloor() && row3 <= laatsteplekAbbo.getRow() && place3 <= laatsteplekAbbo.getPlace()) {
					    floor = laatsteplekAbbo.getFloor();
					    row = laatsteplekAbbo.getRow();
					    place = laatsteplekAbbo.getPlace() + 1;
					    Location location3 = new Location(floor, row, place);
					    Location check3 = getCarAt(location) == null ? location : null;
					    if(check3 != null) {
						return location3;
					    }
					}
				    }
				}
                        
            		}
            	}       
            }
                //Location location = new Location(floor, row, place);
                //Location check = getCarAt(location) == null ? location : null;
                //if(check != null) {
                //	return location;
		}
            }
    return null;
}
	
	


}


/*
nog niet af! met switch statement maar met if is beter^)      public Location getEersteVrijeLocatie(boolean paying, boolean reservation) {
betalen = (paying)? 1 : 0;
reservering= (reservation)? 3 : 2;

switch(betalen) {
case 1: //wel betalen
  switch(reservering) { //wel betalen, wel reservering
  case 1: //reservingCar
	  for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                	if (paying == true && reservation == false) {
                		if(floor <= laatsteplekAbbo.getFloor() && row <= laatsteplekAbbo.getRow() && place<= laatsteplekAbbo.getPlace()) {
                			floor = laatsteplekAbbo.getFloor();
                			row = laatsteplekAbbo.getRow();
                			place = laatsteplekAbbo.getPlace() + 1;
                		}        				
         		}
         	
                Location location = new Location(floor, row, place);
             Location check = getCarAt(location) == null ? location : null;
             if(check != null) {
             	return location;
             }
          }
            }
	  }
  break;
  }

break;	

case 0: //niet betalen
  switch(reservering) { //niet betalen, geen reservering
  case 2: //abonnementCar
	  for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                	if (paying == true&& reservation == false) {
                		if(floor <= laatsteplekAbbo.getFloor() && row <= laatsteplekAbbo.getRow() && place<= laatsteplekAbbo.getPlace()) {
                			floor = laatsteplekAbbo.getFloor();
                			row = laatsteplekAbbo.getRow();
                			place = laatsteplekAbbo.getPlace() + 1;
                		}        				
           		}
           	
                Location location = new Location(floor, row, place);
               Location check = getCarAt(location) == null ? location : null;
               if(check != null) {
               	return location;
               }
          }     
  break;
  }
break;
	  }
default:
  for (int floor = 0; floor < getNumberOfFloors(); floor++) {
       for (int row = 0; row < getNumberOfRows(); row++) {
           for (int place = 0; place < getNumberOfPlaces(); place++) {
           	if (paying == true&& reservation == false) {
           		if(floor <= laatsteplekAbbo.getFloor() && row <= laatsteplekAbbo.getRow() && place<= laatsteplekAbbo.getPlace()) {
           			floor = laatsteplekAbbo.getFloor();
           			row = laatsteplekAbbo.getRow();
           			place = laatsteplekAbbo.getPlace() + 1;
           		}        				
       		}
       	
           Location location = new Location(floor, row, place);
           Location check = getCarAt(location) == null ? location : null;
           if(check != null) {
           	return location;
           }
     }
  break;
  }

}
}
}
}
*/


    	
