package listeners;

import javax.swing.JFrame;

public class test1{
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(500, 500);
		//frame.getContentPane().setLayout(null);
		Circle circ1 = new Circle();
		circ1.draw(200, 200, 300, 200);
		frame.add(circ1);
		frame.revalidate();
		//Circle circ2 = new Circle();
		//circ2.draw(300, 200, 300, 200);
		//frame.add(circ2);
		Circle rect1 = new Circle();
		rect1.draw(200, 200, 100, 100);
		frame.add(rect1);
		frame.revalidate();
	}
	
	
}
