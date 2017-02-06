package nl.PriorIT.src.Parkingsimulator.features;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.EmptyStackException;

import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
import nl.PriorIT.src.Parkingsimulator.view.GeneralView;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;

public class CarparkView extends GeneralView {
    
    private static final long serialVersionUID = 108804;
    
    private Dimension size = getSize();
    private Image carParkImage;   
 /*
    private Car[][][] cars;
    private Location laatsteplek;
    private int hoeveelheid;
   */ 
    public CarparkView(TestModel testmodel1) {
	super(testmodel1);
	setSize(200, 200);
    }
    
    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }
    
    public void updateView() {
	    // Create a new car park image if the size has changed.
	    if (!size.equals(getSize())) {
	        size = getSize();
	        carParkImage = createImage(size.width, size.height);
	    }
	    else {
		repaint();
	    }
	    if(carParkImage == null) {
	        System.out.println("carpark image is leeg");
	        System.out.println("Poging tot creëren van nieuwe image...");
	        
	        try {
	            if(carParkImage == null)
	            {
	        	carParkImage = createImage(size.width, size.height);
	            }
	            
	           System.out.println("Successful at creating image (by checking first for null and instantiating carParkImage implementation)");
	                
	        }
	        catch (NullPointerException nullpointer) {
	            System.out.println("Carpark image is still null" + nullpointer);
	        }
	    } 
	      
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

public Location getFirstFreeLocation(boolean paying) {
	
    for (int floor = 0; floor < getNumberOfFloors(); floor++) {
        for (int row = 0; row < getNumberOfRows(); row++) {
            for (int place = 0; place < getNumberOfPlaces(); place++) {
            	if (paying == true) {
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



private boolean locationIsValid(Location location) {
    int floor = location.getFloor();
    int row = location.getRow();
    int place = location.getPlace();
    if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
        return false;
    }
    return true;
}   
   
