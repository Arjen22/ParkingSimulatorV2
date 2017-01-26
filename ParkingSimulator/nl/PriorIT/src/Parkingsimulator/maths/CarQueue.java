package nl.PriorIT.src.Parkingsimulator.maths;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Constructor for objects of class Car
 * @author Prior IT
 * @category Core Feature
 * 
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue(){
    	return queue.size();
    }
}
