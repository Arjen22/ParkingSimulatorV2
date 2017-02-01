package nl.PriorIT.src.Parkingsimulator.logic;



public class TestModel extends GeneralModel implements Runnable {
	private int aantal;
	private boolean run;
    	
    	public TestModel() {
    	}
	
	
	public int getAantal() {
		return aantal;
	}
	
	public void setAantal(int aantal) {
		if (aantal>=0 && aantal <=360) {
			this.aantal=aantal;
			notifyViews();
		}
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void stop() {
		run=false;
	}
	
	@Override
	public void run() {
		run=true;
		while(run) {
			setAantal(getAantal()+1);
			try {
				Thread.sleep(100);
			} catch (Exception e) {} 
		}
	}
	
    	}

    	
