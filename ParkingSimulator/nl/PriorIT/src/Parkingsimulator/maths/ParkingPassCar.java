/**
 * @author Prior IT
 * @version 0.1 Early Alpha
 */
package nl.PriorIT.src.Parkingsimulator.maths;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;
	
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
        this.setReservation(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
