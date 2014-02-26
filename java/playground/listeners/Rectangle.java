package listeners;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Rectangle extends JComponent implements TwoPointShape{
	
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
		int width = Math.abs(x2-x);
		int height = Math.abs(y2-y);
		if(x2<x) x=x2;
		if(y2<y) y=y2;
		g.drawRect(x, y, width, height);
			
	}
}
