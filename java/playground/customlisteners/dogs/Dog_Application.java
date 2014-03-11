package customlisteners.dogs;

import java.util.ArrayList;

public class Dog_Application {

	public static void main(String[] args) {
		
		ArrayList<Dog> dogs = new ArrayList<Dog>();
		
		Dog d1 = new Dog("Milli");
		Dog d2 = new Dog("Papa");
		Dog d3 = new Dog("Cherry");
		Dog d4 = new Dog("Brownie");
		
		dogs.add(d1);
		dogs.add(d2);
		dogs.add(d3);
		dogs.add(d4);
		
		Person p1 = new Person("Puki");
		Person p2 = new Person("Mummi");
		Person p3 = new Person("Arnav");
		Person p4 = new Person("Nikhil");
		
		d1.addDogListener(p1);
		d1.addDogListener(p2);
		
		d2.addDogListener(p2);
		
		d3.addDogListener(p3);
		
		d4.addDogListener(p4);
		
		for (Dog d: dogs){
			d.activate();
			try {
				Thread.sleep(1732l);
			} catch (InterruptedException e) {}
		}

	}

}
