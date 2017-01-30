/**
 * @author Prior IT
 * @version 0.1 Early Alpha
 */
package nl.PriorIT.src.Parkingsimulator.maths;

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car {
	private static final Color COLOR=Color.pink;
	
    public ReservationCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setReservation(true);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}