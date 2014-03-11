
class Feline {
	public String type = "f ";
	public Feline() {
		System.out.print("feline ");
	}
}
class Cougar extends Feline {
	//public String type = "c "; //this declares a new string so super.type still outputs "f"
	public Cougar() {
		System.out.print("cougar ");
	}
	public static void main(String[] args) {
		new Cougar().go();
	}
	void go() {
		type = "c ";
		System.out.print(this.type + super.type);
	}
}

//class BlackCougar extends Cougar{
//	public static void main(String[] args){
//		new BlackCougar();
//		System.out.println();
//	}
//}