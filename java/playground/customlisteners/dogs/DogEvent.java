package customlisteners.dogs;

public class DogEvent {
	//dog object so that we can keep
	//track of which object generated the event
	private Dog source;
	//constructor
	public DogEvent(Dog source){
		this.source = source;
	}
	//methods can be added to provide
	//information about the event, this
	//first example is one that is generally
	//provided by OOTB event classes in java
	//and it provides the info on which object
	//generated the event
	public Dog getSource(){
		return source;
	}
}
