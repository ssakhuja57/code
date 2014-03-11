package multi;

public class Racer implements Runnable{

	private String name;
	private double speed;
	private double distCovered;
	private long goal;
	
	public Racer(String name, double speed, long goal){
		this.name = name;
		this.speed = speed;
		this.distCovered = 0;
		this.goal = goal;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getDistance(){
		return this.distCovered;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public void run() {
		while (this.distCovered <= goal)
			distCovered = distCovered + speed;
	}
		
}
