package customlisteners.dogs;

import java.util.HashSet;
import java.util.Set;

public class Dog {
	
	//a thread that will have Dog object repeatedly 
	//(randomly fire one of its four events and 
	//then sleep for 5 seconds)
	private Thread life = new Thread(){
		public void run(){
			while (true){
				int roll = (int)(Math.random()*4.0);
				switch (roll){
				case 0:fireDogHungryEvent(); break;
				case 1:fireDogThirstyEvent(); break;
				case 2:fireDogLonelyEvent(); break;
				case 3:fireDogSleepyEvent();
				}
				try {
					sleep (5000l);
				} catch (Exception e){}
			}
		}
	};
	
	//set is used so no duplicates
	private Set<DogListener> listeners;
	private String name;
	
	//constructor that will set Dog object name, initialize
	// listeners set, and start Dog object's life thread
	public Dog(String name){
		this.name = name;
		this.listeners = new HashSet<DogListener>();
	}
	
	public void activate(){
		System.out.println("*** " + name + " is here ***");
		life.start();
	}
	
	public String getName(){
		return name;
	}
	
	//the next two methods allow DogListener objects to be added
	//to Dog object's listeners set so that they are notified
	//when a fire event happens
	public synchronized void addDogListener(DogListener listener){
		listeners.add(listener);
	}
	
	public synchronized void removeDogListener(DogListener listener){
		listeners.remove(listener);
	}
	
	//firing dog event will create a new DogEvent object
	//by passing in this Dog object as the source;
	//this DogEvent object will be passed to each (listener
	//in its listeners set)'s dogXXXXXX method;
	//note that the dogXXXXXX method is the equivalent
	//to actionPerformed method for the ActionListener class
	protected synchronized void fireDogHungryEvent(){
		System.out.println(name + " is hungry");
		DogEvent e = new DogEvent(this);
		for (DogListener listener: listeners){
			listener.dogHungry(e);
		}
	}
	protected synchronized void fireDogThirstyEvent(){
		System.out.println(name + " is thirsty");
		DogEvent e = new DogEvent(this);
		for (DogListener listener: listeners){
			listener.dogThirsty(e);
		}
	}
	protected synchronized void fireDogLonelyEvent(){
		System.out.println(name + " is lonely");
		DogEvent e = new DogEvent(this);
		for (DogListener listener: listeners){
			listener.dogLonely(e);
		}
	}
	protected synchronized void fireDogSleepyEvent(){
		System.out.println(name + " is sleepy");
		DogEvent e = new DogEvent(this);
		for (DogListener listener: listeners){
			listener.dogSleepy(e);
		}
	}
}
