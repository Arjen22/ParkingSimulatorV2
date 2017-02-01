/**
 * @author Prior IT
 * @version 0.1 Early Alpha 
 */
package nl.PriorIT.src.Parkingsimulator.view;

import javax.swing.*;

import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.Location;

import java.awt.*;

<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
public class SimulatorView extends JFrame { //the serializable class SimulatorView does not declare a static final serialVersionUID field of type long
=======
public class SimulatorView extends JFrame { 
											//the serializable class SimulatorView does not declare a static
											//final serialVersionUID field of type long
>>>>>>> 07d548c Poging tot abbonementshouders
=======
public class SimulatorView extends JFrame { //the serializable class SimulatorView does not declare a static final serialVersionUID field of type long
>>>>>>> 3ed0e6b bijna klaar
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
    // private static int floornumber = 0; NOT BEING USED
=======
    private static int floornumber = 0;
>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
    // private static int floornumber = 0; NOT BEING USED
>>>>>>> 07d548c Poging tot abbonementshouders
    private Car[][][] cars;
    private int abonnementsPlaatsen;
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
    private Location laatsteplekAbbo;
    private int hoeveelheidPlaatsen;
    /* private int aantalReserveringen;
    private int reservering;
    private int betalen; */
=======
    private int reserveringsPlaatsen;
=======
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee
    private Location laatsteplek;
    private int hoeveelheid;
<<<<<<< Upstream, based on origin/reservering
    private int hoeveelheidReserveringen;
    private int startPlekReserveringen;
>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee
=======
    private Location laatsteplekAbbo;
    private int hoeveelheidPlaatsen;
<<<<<<< Upstream, based on origin/reservering
    private int aantalReserveringen;
>>>>>>> e264262 Hallo jorick
=======
    /* private int aantalReserveringen;
    private int reservering;
    private int betalen; */
>>>>>>> 07d548c Poging tot abbonementshouders
    
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, int abonnementsPlaatsen) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.abonnementsPlaatsen = abonnementsPlaatsen;
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
=======
        this.reserveringsPlaatsen = reserveringsPlaatsen;
=======
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee
        abonnementsPlaatsen = abonnementsPlaatsen < 0 ? 0 : abonnementsPlaatsen;
>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
>>>>>>> e264262 Hallo jorick
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
        hoeveelheidPlaatsen = abonnementsPlaatsen;
=======
        hoeveelheid = abonnementsPlaatsen;
<<<<<<< Upstream, based on origin/reservering
        hoeveelheidReserveringen = reserveringsPlaatsen;
>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee
=======
        hoeveelheidPlaatsen = abonnementsPlaatsen;
>>>>>>> e264262 Hallo jorick
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        carParkView = new CarParkView();

        Container Controlpanelview = getContentPane();
        Controlpanelview.add(carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);

        updateView();
    }

    public void updateView() {
        carParkView.updateView();
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

<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
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


    
=======
>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
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


    
>>>>>>> 07d548c Poging tot abbonementshouders
    public Location getFirstFreeLocation(boolean paying,boolean reservation) {
    	
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
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
                		//Location location = new Location(floor, row, place);
                        Location check1 = getCarAt(location) == null ? location : null;
                        if(check1 != null) {
                        	return location;
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
=======
                for (int place = 0; place < getNumberOfPlaces(); place++) {
=======
                for (int place = 0; place < getNumberOfPlaces(); place++) { 
>>>>>>> 3ed0e6b bijna klaar
        
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
                		//Location location = new Location(floor, row, place);
                        Location check1 = getCarAt(location) == null ? location : null;
                        if(check1 != null) {
                        	return location;
                        } 
                	 else if (paying == true && reservation == true) { //ReservationCar
                		for (int floor2 = 0; floor < getNumberOfFloors(); floor++) {
                            for (int row2 = 0; row < getNumberOfRows(); row++) {
                                for (int place2 = 0; place < getNumberOfPlaces(); place++) {
                		if(floor2 <= laatsteplekAbbo.getFloor() && row2 <= laatsteplekAbbo.getRow() && place2 <= laatsteplekAbbo.getPlace()) {
                			floor = laatsteplekAbbo.getFloor();
                			row = laatsteplekAbbo.getRow() + 3;
                			place = laatsteplekAbbo.getPlace() + 1;
<<<<<<< Upstream, based on origin/reservering
                		}
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
                	}
                	else if(reservation == false && paying == false) {
                		if(floor <= LaatsteReserveringPlaats.getFloor()) {
                			floor = LaatsteReserveringPlaats.getFloor();
                			if(row <= LaatsteReserveringPlaats.getRow()) {
                				row = LaatsteReserveringPlaats.getRow();
                				if(place<= LaatsteReserveringPlaats.getPlace()) {
                					place = LaatsteReserveringPlaats.getPlace() + 1;
                				}                				            				
                			}                				
>>>>>>> 547dee9 reserveringen Henny en Jorick
                		}
                	}
                            }
                		}
                		} 
                    } else { //NORMAL CAR
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
=======
                	}                	
                    Location location = new Location(floor, row, place);
                    Location check = getCarAt(location) == null ? location : null;
                    if(check != null) {
                    	return location;
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee
=======
                	}
=======
                			Location location2 = new Location(floor, row, place);
>>>>>>> 3ed0e6b bijna klaar
                            Location check2 = getCarAt(location) == null ? location : null;
                            if(check2 != null) {
                            	return location2;
                		}
                	}
                            }
                		}
                		} 
                    } else { //NORMAL CAR
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
>>>>>>> 07d548c Poging tot abbonementshouders
                    }
                }
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
        return null;
    }

<<<<<<< Upstream, based on origin/reservering
    
<<<<<<< Upstream, based on origin/reservering
=======
    public ArrayList<Integer> getReservations() {
		return Reservations;
	}

	public void setReservations(ArrayList<Integer> reservations) {
		Reservations = reservations;
	}
=======
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
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee

>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
            //}
        //}
=======
>>>>>>> 3ed0e6b bijna klaar
        return null;
    }

    
>>>>>>> 07d548c Poging tot abbonementshouders
	private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overridden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            int openPlekken = getAbonnementsPlaatsen();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                    	Location location = new Location(floor, row, place);
                    	Car car = getCarAt(location);
                    	Color color = Color.white;
                    	if (openPlekken > 0){
                    		if(car == null) {
                    			color = Color.green;
                    		}
                    		else {
                    			color = car.getColor();
                    		}
                    	      if (hoeveelheidPlaatsen > 0){
                    	    	  laatsteplekAbbo = location;
                    	    	  hoeveelheidPlaatsen--;
                    	      }
                    	      openPlekken--;
                    	}
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
                    	if(car == null){
                    		color = color;
                    	}
<<<<<<< Upstream, based on origin/reservering
                    	else {
                    		color = car.getColor();
                    		}
=======
                    	if (openReserveringsPlaatsen > 0 ){
                    		startPlekReserveringen = abonnementsPlaatsen +1;
                    		color = car == null ? Color.black : car.getColor();
                    	      if (hoeveelheidReserveringen > 0){
                    	    	  LaatsteReserveringPlaats = reservedLocation;
                    	    	  hoeveelheidReserveringen--;
                    	      }
                    	      openReserveringsPlaatsen--;
=======
                    	else {
                    		color = car == null ? color : car.getColor();
>>>>>>> db31533 reservering verwijderd zodat we opnieuw kunnen beginnen daarmee
=======
                    	if(car == null){
                    		color = color;
>>>>>>> e264262 Hallo jorick
                    	}
<<<<<<< Upstream, based on origin/reservering
>>>>>>> 547dee9 reserveringen Henny en Jorick
=======
                    	else {
                    		color = car.getColor();
                    		}
>>>>>>> e264262 Hallo jorick
                    drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
<<<<<<< Upstream, based on origin/reservering
<<<<<<< Upstream, based on origin/reservering
    }
}
=======
>>>>>>> 07d548c Poging tot abbonementshouders
=======
    }
}
>>>>>>> 3ed0e6b bijna klaar

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
