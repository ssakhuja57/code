package listeners;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class draw_example extends JPanel{
	
	private static int x,y,x2,y2;
	
	public void drawing(int a, int b, int a2, int b2){
		x = a;
		y = b;
		x2 = a2;
		y2 = b2;
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.drawOval(x-(x2-x), y-(y2-y), 2*(x2-x), 2*(y2-y));
	}
}
