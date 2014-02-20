package multi;

import java.util.ArrayList;

public class Race {

	private long distance;
	private double time;
	public Racer[] contestants;
	private boolean inRace = false;
	
	public Race(long distance, Racer[] contestants){
		this.distance = distance;
		this.time = 0;
		this.contestants = contestants;
	}
	
	public void start(){
		
		this.inRace = true;
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (Racer r: this.contestants){
			threads.add(new Thread(r));
		}
		for (Thread t: threads){
			t.start();
		}
		
		while (this.inRace){
			time = time + 0.1;
			this.checkWin();
		}
		
		//for (Thread t: threads){
		//	t.stop(); this is deprecated apparently
		//}
		
		this.inRace = false;
	}
	
	private void checkWin(){
		for (Racer r: this.contestants){
			double dist = r.getDistance();
			if (dist <= this.distance){
				//System.out.println("At time " + this.time + ", " + r.getName() + " is at " + dist);
				System.out.printf("At time %3.1f, %s is at %4.2f\n", this.time, r.getName(), dist);
			}
			else{
				//System.out.println("\n" + r.getName() + " has won the race at time " + this.time);
				System.out.printf("\n\n%s has won the race at time %3.1f", r.getName(), this.time);
				this.inRace = false;
				return;
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		long d = 100;
		
		Racer[] r = new Racer[4];
		r[0] = new Racer("Tinu", 0.00001, d);
		r[1] = new Racer("SS", 0.00001, d);
		r[2] = new Racer("DS", 0.00001, d);
		r[3] = new Racer("NS", 0.00001, d);
		
		Race race = new Race(100, r);
		race.start();

	}

}
