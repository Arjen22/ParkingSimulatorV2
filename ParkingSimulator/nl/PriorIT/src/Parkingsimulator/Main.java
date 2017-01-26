    /**
     * 
     * @author Prior IT
     * @version 0.1 Early Alpha
     * 
     */
package nl.PriorIT.src.Parkingsimulator;

/**
 * 
 * Constructor of the main class. 
 */
public class Main {
    /**
     * 
     * Public method which defines the new simulator view.
     * @param args
     * @return nothing
     * @see Simulator#run()
     * @see Simulator#Simulator()
     */
	public static void main(String[] args) {
		Simulator simulator = new Simulator();
		testclass testwindow = new testclass();
		simulator.run();
		
	}

}
