package customlisteners.dogs;

public class Person implements DogListener{

	private String name;
	
	public String getName(){
		return name;
	}
	
	public Person(String name){
		this.name = name;
	}
	
	@Override
	public void dogHungry(DogEvent e) {
		System.out.println("- " + name + " feeds " + e.getSource().getName());
	}

	@Override
	public void dogThirsty(DogEvent e) {
		System.out.println("- " + name + " gets water for " + e.getSource().getName());		
	}

	@Override
	public void dogLonely(DogEvent e) {
		System.out.println("- " + name + " plays with " + e.getSource().getName());
	}

	@Override
	public void dogSleepy(DogEvent e) {
		System.out.println("- " + name + " makes bed for " + e.getSource().getName());
	}

	
	
}
