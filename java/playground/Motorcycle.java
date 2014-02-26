
public class Motorcycle extends Vehicle{

	String testOverride1 = "overridden attribute";
	String testOverload1 = "overloaded attribute";
	
	public void testOverload2(){
		System.out.println("overloaded method");
	}
	
	public void testOverride2(){
		System.out.println("overridden method");
	}


	public static void main(String[] args){
		Motorcycle abc = new Motorcycle();
		
		System.out.println(abc.testOverride1);
		//System.out.println(abc.testOverload1);
		
		abc.testOverride2();
		//abc.testOverload2;
	}
}
