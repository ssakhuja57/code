package tic_tac_toe;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.Label;

public class gui extends JFrame{
	public gui() {
		clicks=0;
		for (int i=0;i<9;i++)
			boxes[i]="";
		player="X";
		turn.setText(player);
		
		setTitle("Tic Tac Toe");
		getContentPane().setLayout(null);
		
		box1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box1.setEnabled(false);
				box1.setLabel(player);
				boxes[0]=player;
				clicks++;
				refresh();
			}
		});
		box1.setBounds(50, 50, 50, 50);
		getContentPane().add(box1);
		
		box2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box2.setEnabled(false);
				box2.setLabel(player);
				boxes[1]=player;
				clicks++;
				refresh();
			}
		});
		box2.setBounds(110, 50, 50, 50);
		getContentPane().add(box2);
		
		box3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box3.setEnabled(false);
				box3.setLabel(player);
				boxes[2]=player;
				clicks++;
				refresh();
			}
		});
		box3.setBounds(170, 50, 50, 50);
		getContentPane().add(box3);
		
		box4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box4.setEnabled(false);
				box4.setLabel(player);
				boxes[3]=player;
				clicks++;
				refresh();
			}
		});
		box4.setBounds(50, 110, 50, 50);
		getContentPane().add(box4);
		
		box5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box5.setEnabled(false);
				box5.setLabel(player);
				boxes[4]=player;
				clicks++;
				refresh();
			}
		});
		box5.setBounds(110, 110, 50, 50);
		getContentPane().add(box5);
		
		box6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box6.setEnabled(false);
				box6.setLabel(player);
				boxes[5]=player;
				clicks++;
				refresh();
			}
		});
		box6.setBounds(170, 110, 50, 50);
		getContentPane().add(box6);
		
		box7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box7.setEnabled(false);
				box7.setLabel(player);
				boxes[6]=player;
				clicks++;
				refresh();
			}
		});
		box7.setBounds(50, 170, 50, 50);
		getContentPane().add(box7);
		
		box8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box8.setEnabled(false);
				box8.setLabel(player);
				boxes[7]=player;
				clicks++;
				refresh();
			}
		});
		box8.setBounds(110, 170, 50, 50);
		getContentPane().add(box8);
		
		box9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				box9.setEnabled(false);
				box9.setLabel(player);
				boxes[8]=player;
				clicks++;
				refresh();
			}
		});
		box9.setBounds(170, 170, 50, 50);
		getContentPane().add(box9);
		
		new_game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0;i<9;i++)
					boxes[i]="";
				clicks=0;
				resultbox.setText("");
				player="X";
				turn.setText(player);
				box1.setLabel("");
				box2.setLabel("");
				box3.setLabel("");
				box4.setLabel("");
				box5.setLabel("");
				box6.setLabel("");
				box7.setLabel("");
				box8.setLabel("");
				box9.setLabel("");
				box1.setEnabled(true);
				box2.setEnabled(true);
				box3.setEnabled(true);
				box4.setEnabled(true);
				box5.setEnabled(true);
				box6.setEnabled(true);
				box7.setEnabled(true);
				box8.setEnabled(true);
				box9.setEnabled(true);
				
			}
		});
		new_game.setBounds(288, 53, 79, 24);
		getContentPane().add(new_game);
		
		turn.setText("X");
		turn.setEditable(false);
		turn.setBounds(337, 110, 16, 24);
		getContentPane().add(turn);
		
		resultbox.setAlignment(Label.CENTER);
		resultbox.setBounds(288, 157, 134, 24);
		getContentPane().add(resultbox);
		
		Label label_1 = new Label("Turn:");
		label_1.setBounds(288, 110, 46, 24);
		getContentPane().add(label_1);
	}
	
	public Button box1 = new Button("");
	public Button box2 = new Button("");
	public Button box3 = new Button("");
	public Button box4 = new Button("");
	public Button box5 = new Button("");
	public Button box6 = new Button("");
	public Button box7 = new Button("");
	public Button box8 = new Button("");
	public Button box9 = new Button("");
	
	public Button new_game = new Button("New Game");
	public Label resultbox = new Label("");
	public TextField turn = new TextField();
	
	
	private String player;
	private static String[] boxes = new String[9];
	private int clicks;
	
	private static String[] check_win1(String[] boxes){
		String[] fin = new String[2];
		for (int i=0;i<7;i++){
			if(boxes[i] != ""){
				Integer j = 1;
				while (j < 4){
					try{
						if (boxes[i].equals(boxes[i+j]) && boxes[i].equals(boxes[i+2*j]) && 
								( i%3==(i+2*j)%3 || (int)(i/3)==(int)((i+2*j)/3)) ){
							fin[0] = boxes[i];
							fin[1] = j.toString();
							return fin;
						}
					}
					catch (Exception e){}
					j += 2;
				}
			}
		}
		if (boxes[0]!=""&&boxes[0]==boxes[4]&&boxes[4]==boxes[8]){
			fin[0] = boxes[0];
			fin[1] = "4";
			return fin;
		}
		else if (boxes[2]!=""&&boxes[2]==boxes[4]&&boxes[4]==boxes[6]){
			fin[0] = boxes[2];
			fin[1] = "2";
			return fin;
		}
		fin[0] = null;
		fin[1] = null;
		return fin;
	}
	
	private String[] check_win2(String[] boxes){
		String[] fin = new String[2];
		Integer hor=1;
		Integer ver=3;
		Integer dia1=4;
		Integer dia2=2;
		for (int i=0;i<=6;i+=3){
			if (boxes[i]!=""){
				if (boxes[i]==boxes[i+hor]&&boxes[i]==boxes[i+2*hor]){
					fin[0] = boxes[i];
					fin[1] = hor.toString();
					return fin;
				}
			}
		}
		for (int i=0;i<=2;i++){
			if (boxes[i]!=""){
				if (boxes[i]==boxes[i+ver]&&boxes[i+ver]==boxes[i+2*ver]){
					fin[0] = boxes[i];
					fin[1] = ver.toString();
					return fin;
				}
			}
		}
		if (boxes[0]!=""&&boxes[0]==boxes[dia1]&&boxes[dia1]==boxes[2*dia1]){
			fin[0] = boxes[0];
			fin[1] = dia1.toString();
			return fin;
		}
		else if (boxes[2]!=""&&boxes[2]==boxes[2+dia2]&&boxes[2+dia2]==boxes[2+2*dia2]){
			fin[0] = boxes[2];
			fin[1] = dia2.toString();
			return fin;
		}
		fin[0] = null;
		fin[1] = null;
		return fin;
	}
	private void refresh(){
		String[] res = check_win1(boxes);
		String orient = "";
		if (res[0] != null){
			box1.setEnabled(false);
			box2.setEnabled(false);
			box3.setEnabled(false);
			box4.setEnabled(false);
			box5.setEnabled(false);
			box6.setEnabled(false);
			box7.setEnabled(false);
			box8.setEnabled(false);
			box9.setEnabled(false);
			turn.setText("");
			if (res[1]=="1")
				orient = "horizontally";
			else if (res[1]=="3")
				orient = "vertically";
			else if (res[1]=="2" || res[1]=="4")
				orient = "diagonally";
			resultbox.setText(res[0] + " wins " + orient + "!");
			if (res[1]==null) System.out.println("test");
			//System.out.println(orient);
		}
		else if (clicks==9){
			resultbox.setText("It's a tie!");
			box1.setEnabled(false);
			box2.setEnabled(false);
			box3.setEnabled(false);
			box4.setEnabled(false);
			box5.setEnabled(false);
			box6.setEnabled(false);
			box7.setEnabled(false);
			box8.setEnabled(false);
			box9.setEnabled(false);
			turn.setText("");
		}
		else{
			if (player=="X")
				player="O";
			else
				player="X";
			turn.setText(player);
		}	
	}
	
   /* public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(100, 100, 250, 260);
        g2.draw(lin);
        g2.drawLine(50, 50, 170, 170);
        g2.clearRect(50, 50, 200, 200);
    }*/
	
	public static void main(String[] args){
		gui g = new gui();
		g.setVisible(true);
		//String[] test = String{["X", "X", "X", "X","X","X","X","X","X"]};
		//String[] res = check_win()
		//System.out.println(res[1]);
	}


}
