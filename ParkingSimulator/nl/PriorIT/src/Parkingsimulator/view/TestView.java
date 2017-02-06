package nl.PriorIT.src.Parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Random;

import nl.PriorIT.src.Parkingsimulator.controller.Controller;
import nl.PriorIT.src.Parkingsimulator.core.Simulator;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;
import nl.PriorIT.src.Parkingsimulator.maths.AdHocCar;
import nl.PriorIT.src.Parkingsimulator.maths.Car;
import nl.PriorIT.src.Parkingsimulator.maths.CarQueue;
import nl.PriorIT.src.Parkingsimulator.maths.Location;
import nl.PriorIT.src.Parkingsimulator.maths.ParkingPassCar;

public class TestView extends GeneralView {
    
    // Waarden van de image van de parking garage en de grootte van het main frame voor de image van de parking garage.
    private Dimension mainframesize;
    private Image carparkimage; 
    private Controller controller;


    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    
    /**
     * Is the constructor of the testview and has the variable testmodel1 as it's reference to the object of testmodel.
     */
    public TestView(TestModel testmodel1) {
	super(testmodel1);
	setSize(800,600);
	mainframesize = new Dimension(0, 0);
    }
    
    public Dimension getPreferredSize() {
	return new Dimension(800,500);
    }
    
    
    /**
     * General methods that get the parking garage values.
     * 
     */
    
    
    
    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen. Paints the component to the screen. 
     */
    
    public void paintComponent(Graphics g) {
       if (carparkimage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (mainframesize.equals(currentSize)) {
            g.drawImage(carparkimage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carparkimage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }
    
    /**
     * Updates the TestView with the new values of the parking garage.
     */
    	public void updateView() {
        // Create a new car park image if the size has changed.
        if (!mainframesize.equals(getSize())) {
            mainframesize = getSize();
            carparkimage = createImage(mainframesize.width, mainframesize.height);
        }
        Graphics graphics = carparkimage.getGraphics();
        for(int floor = 0; floor < testmodel1.getNumberOfFloors(); floor++) {
            for(int row = 0; row < testmodel1.getNumberOfRows(); row++) {
                for(int place = 0; place < testmodel1.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = testmodel1.getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }
   
    /**
     * Graphical construction methods which construct the graphics of the parking garage.
     */

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
    
   public void tick() {
        for (int floor = 0; floor < controller.getParkingGarageFloors(); floor++) {
            for (int row = 0; row < controller.getParkingGarageRow(); row++) {
                for (int place = 0; place < controller.getParkingGaragePlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = testmodel1.getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
      }
    }