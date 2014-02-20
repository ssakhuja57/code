package customlisteners.dogs;

public interface DogListener {
	//dog will generate a dog event if he is
	//hungry, thirsty, lonely, or sleepy
	public void dogHungry(DogEvent e);
	public void dogThirsty(DogEvent e);
	public void dogLonely(DogEvent e);
	public void dogSleepy(DogEvent e);
}
