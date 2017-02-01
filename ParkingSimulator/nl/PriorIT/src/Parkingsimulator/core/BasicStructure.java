package nl.PriorIT.src.Parkingsimulator.core;

import javax.swing.JFrame;

import nl.PriorIT.src.Parkingsimulator.controller.Controller;
import nl.PriorIT.src.Parkingsimulator.logic.TestModel;
import nl.PriorIT.src.Parkingsimulator.view.CarparkView;
import nl.PriorIT.src.Parkingsimulator.view.GeneralView;

public class BasicStructure {
    private TestModel SimulatorModel;
	private JFrame screen;
	private GeneralView cpview;
	private Controller controller;
	
	public BasicStructure() {
	
    SimulatorModel = new TestModel(0, 0, 0, 0);
	controller=new Controller(SimulatorModel);
	cpview=new CarparkView(SimulatorModel);
	screen=new JFrame("Parking Garage Simulator");
	screen.setSize(800, 500);
	screen.setResizable(false);
	screen.setLayout(null);
	screen.getContentPane().add(cpview);
	screen.getContentPane().add(controller);
	controller.setBounds(0, 210, 450, 50);
	screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	screen.setVisible(true);
    }
}
