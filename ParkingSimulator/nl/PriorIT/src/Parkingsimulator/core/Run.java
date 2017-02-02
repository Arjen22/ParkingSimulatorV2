/**
 * 
 */
package nl.PriorIT.src.Parkingsimulator.core;

import nl.PriorIT.src.Parkingsimulator.logic.TestModel;

public class Run {

    /**
     * @param args
     */
	private TestModel testmodel1;
	
    public static void main(String[] args) {
    	TestModel testmodel1 = new TestModel(3, 6, 30, 100);
		
	}
    
    public static String getModel()
    {
        return testmodel1;
    }
    

}
