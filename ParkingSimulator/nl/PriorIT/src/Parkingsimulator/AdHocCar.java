package nl.PriorIT.src.Parkingsimulator;

import java.util.Random;
import java.awt.*;

/**
 * @author Arjen
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
    }
    
    public Color getColor(){
    	return COLOR;
    }
}