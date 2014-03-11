import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class JFrame_test extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	
	private JTextField t1;
	private JButton b1;
	
	public JFrame_test(){
		super("testing testing");
		setLayout(new FlowLayout());
		
		t1 = new JTextField("text field 1");
		b1 = new JButton("button 1");
		
		t1.addActionListener(new test());
		b1.addActionListener(this);
		
		add(t1);
		add(b1);
	}
	
	public static void main(String[] args) {
		JFrame_test test = new JFrame_test();
		test.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("you have performed an action, yay!");
		
	}
	
	private class test implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("you have gone through inner class!");
			
		}
		
	}


}
