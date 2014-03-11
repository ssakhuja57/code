package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class mouse_example {

	private static int x,y,x2,y2;
	private static JComponent[] object = new JComponent[20];
	private static int count=0;
	public static JFrame frame;
	static MouseListener AL1;
	static MouseMotionListener AL2;
	static JButton drect, dcirc;
	
	public static void main(String[] args){
		object[count] = new Rectangle();
		frame = new JFrame("Mouse");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.add(object[count]);
		AL1 = new AL();
		AL2 = new AL();
		object[count].addMouseListener(AL1);
		object[count].addMouseMotionListener(AL2);
		//drect = new JButton("Rectangle");
		//drect.setSize(50, 30);
		//drect.setBounds(0, 0, 50, 30);
		//frame.add(drect);
	}

	static class AL extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			//System.out.println("mouse clicked");
			x = e.getX();
			y = e.getY();
		}
		public void mouseDragged(MouseEvent e){
			//System.out.println("mouse dragged");
			x2 = e.getX();
			y2 = e.getY();
			frame.revalidate();
			((TwoPointShape) object[count]).draw(x,y,x2,y2);
			
		}
		public void mouseReleased(MouseEvent e){	
			//object[count].removeMouseListener(AL1);
			//object[count].removeMouseMotionListener(AL2);
			System.out.println(count);
			count++;
			System.out.println(count);
			object[count] = new Circle();
			frame.add(object[count]);
			//object[count].addMouseListener(AL1);
			//object[count].addMouseMotionListener(AL2);
			System.out.println("test");
		}
	}
	
}
