package nl.PriorIT.src.Parkingsimulator.maths;


import java.util.Random;
import java.awt.*;

/**
 * @author Prior IT
 * @see #tick() 
 * 
 */
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setReservation(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
