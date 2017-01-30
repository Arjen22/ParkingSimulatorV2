/**
 * @author Prior IT
 * @version 0.1 Early Alpha 
 */
package nl.PriorIT.src.Parkingsimulator.view;

import javax.swing.*;

import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
import java.util.ArrayList;

import java.awt.*;

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private static int floornumber = 0;
    private Car[][][] cars;
    private ArrayList<Integer> Reservations;
    private int abonnementsPlaatsen;
    private int reserveringsPlaatsen;
    private Location laatsteplek;
    private Location reservedLocation;
    private Location LaatsteReserveringPlaats;
    private int hoeveelheid;
    private int hoeveelheidReserveringen;
    private int startPlekReserveringen;
    
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces
    		, int abonnementsPlaatsen, int reserveringsPlaatsen) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.abonnementsPlaatsen = abonnementsPlaatsen;
        this.reserveringsPlaatsen = reserveringsPlaatsen;
        abonnementsPlaatsen = abonnementsPlaatsen < 0 ? 0 : abonnementsPlaatsen;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        hoeveelheid = abonnementsPlaatsen;
        hoeveelheidReserveringen = reserveringsPlaatsen;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        ArrayList<Integer> Reservations = new ArrayList<Integer>(); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        carParkView = new CarParkView();

        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
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
    public int getReserveringsPlaatsen() {
    	return reserveringsPlaatsen;
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

    public Location getFirstFreeLocation(boolean paying,boolean reservation) {
    	
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                	if (paying == true&& reservation == false) {
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
                	else if(reservation == false && paying == false) {
                		if(floor <= LaatsteReserveringPlaats.getFloor()) {
                			floor = LaatsteReserveringPlaats.getFloor();
                			if(row <= LaatsteReserveringPlaats.getRow()) {
                				row = LaatsteReserveringPlaats.getRow();
                				if(place<= LaatsteReserveringPlaats.getPlace()) {
                					place = LaatsteReserveringPlaats.getPlace() + 1;
                				}                				            				
                			}                				
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
        return null;
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
    
    public ArrayList<Integer> getReservations() {
		return Reservations;
	}

	public void setReservations(ArrayList<Integer> reservations) {
		Reservations = reservations;
	}

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
            int openReserveringsPlaatsen = getReserveringsPlaatsen();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                    	Location location = new Location(floor, row, place);
                    	Location reservedLocation = new Location(floor, row, place);
                    	Car car = getCarAt(location);
                    	Color color = Color.white;
                    	if (openPlekken > 0){
                    		color = car == null ? Color.green : car.getColor();
                    	      if (hoeveelheid > 0){
                    	    	  laatsteplek = location;
                    	    	  hoeveelheid--;
                    	      }
                    	      openPlekken--;
                    	}
                    	else {
                    		color = car == null ? color : car.getColor();
                    	}
                    	if (openReserveringsPlaatsen > 0 ){
                    		startPlekReserveringen = abonnementsPlaatsen +1;
                    		color = car == null ? Color.black : car.getColor();
                    	      if (hoeveelheidReserveringen > 0){
                    	    	  LaatsteReserveringPlaats = reservedLocation;
                    	    	  hoeveelheidReserveringen--;
                    	      }
                    	      openReserveringsPlaatsen--;
                    	}
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
