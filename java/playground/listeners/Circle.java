package listeners;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Circle extends JComponent implements TwoPointShape{
	
	private int x,y,x2,y2;
	
	public void draw(int a, int b, int a2, int b2){
		x = a;
		y = b;
		x2 = a2;
		y2 = b2;
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		int radius = (int)(Math.sqrt( (Math.pow(x2-x,2) + Math.pow(y2-y,2)) ));
		g.drawOval(x-radius, y-radius, 2*radius, 2*radius);
	}

}
