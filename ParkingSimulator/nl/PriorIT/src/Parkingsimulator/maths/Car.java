package nl.PriorIT.src.Parkingsimulator.maths;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private boolean hasReservation;

    /**
     * Constructor for objects of class Car
     * @author Prior IT
     * @category Feature 
     * 
     */
    public Car() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public void setReservation(boolean hasReservation) {
    	this.hasReservation = hasReservation;
    }
    
    public boolean getReservation() {
    	return hasReservation;
    }
    public abstract Color getColor();
}