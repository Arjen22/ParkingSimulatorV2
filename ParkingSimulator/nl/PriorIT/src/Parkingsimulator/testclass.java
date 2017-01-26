package nl.PriorIT.src.Parkingsimulator;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class testclass {

    /**
     * New class variables which are needed to create the gui.
     */
    private static int wwidth = 800;
    private static int wheight = 600;

    /**
     * The main constructor of the gui, determines the the screen layout of the buttons and screen properties and also the display through a height and width in pixels.
     * @param width 
     * @param height
     * @return nothing
     */
    public testclass(int width,int height) {
	JFrame guiframe = new JFrame("PsimGui");
	JPanel buttonpanel = new JPanel(new BorderLayout());
	JButton startbutton = new JButton("Start");
	JButton advbutton = new JButton("Advance");
	JButton stopbutton = new JButton("Stop");
	guiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	buttonpanel.add(advbutton, BorderLayout.CENTER);
	buttonpanel.add(startbutton, BorderLayout.EAST);
	buttonpanel.add(stopbutton, BorderLayout.SOUTH);
	guiframe.setContentPane(buttonpanel);
	guiframe.pack();
	guiframe.setSize(wwidth, wheight);
	guiframe.setVisible(true);
    }
    
    
}
