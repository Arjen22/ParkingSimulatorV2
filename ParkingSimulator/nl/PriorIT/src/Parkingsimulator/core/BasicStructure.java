package nl.PriorIT.src.Parkingsimulator.core;

import javax.swing.JFrame;

import nl.PriorIT.src.Parkingsimulator.controller.Controller;
import nl.PriorIT.src.Parkingsimulator.core.Simulator;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;
import nl.PriorIT.src.Parkingsimulator.view.GeneralView;
import nl.PriorIT.src.Parkingsimulator.view.TestView;

public class BasicStructure {
	private TestModel testmodel1;
    	private JFrame screen;
    	private TestView cpview;
	private Controller controller;
	
	public BasicStructure() {
	
	testmodel1 = new TestModel(3,6,30,100);
	controller = new Controller(testmodel1);
	cpview = new TestView(testmodel1,controller);
	screen=new JFrame("Parking Garage Simulator");
	screen.setSize(800, 500);
	screen.setResizable(false);
	screen.setLayout(null);
	screen.getContentPane().add(controller);
	screen.getContentPane().add(cpview);
	controller.setBounds(0, 210, 450, 50);
	screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	screen.setVisible(true);
    }
}
