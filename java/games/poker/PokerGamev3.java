

package poker;

import resources.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.MessageFormat;
import javax.swing.*;


public class PokerGamev3 extends JFrame
{

    public static void main(String args[])
    {
        PokerGamev3 game = null;
        try
        {
            game = new PokerGamev3(null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        game.setVisible(true);
    }

    public PokerGamev3(final String uname)
        throws Exception
    {
        Draws = Integer.valueOf(0);
//        Class.forName("com.mysql.jdbc.Driver");
//        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/poker", "root", "Tina1119");
//        s1 = con.createStatement();
//        ResultSet res1 = s1.executeQuery("select max(gameid) from hands");
//        res1.next();
//        gameid = res1.getInt(1);
//		  con.close()
        d = new Deck();
        h = new Hand();
        setTitle("5 Card Draw");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(3);
        Dimension dim = new Dimension(625, 300);
        setPreferredSize(dim);
        pack();
        setResizable(false);
        EvaluatedHand = new JTextField();
        EvaluatedHand.setEditable(false);
        EvaluatedHand.setBounds(29, 208, 356, 42);
        NewGame = new JButton("New Game");
        getContentPane().add(EvaluatedHand);
        NewGame.setBounds(446, 146, 117, 42);
        getContentPane().add(NewGame);
        NewGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                Draws = Integer.valueOf(2);
                h.removeCards();
                d.shuffle();
                h.addCardsfromDeck(d, 5);
                gameid++;
//                try
//                {
//                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/poker", "root", "Tina1119");
//                    System.out.println(insertString(uname, gameid, 3 - Draws.intValue(), h));
//                    s1.executeUpdate(insertString(uname, gameid, 3 - Draws.intValue(), h));
//                }
//                catch(SQLException e1)
//                {
//                    e1.printStackTrace();
//                }
                sort_none.setSelected(true);
                DrawCards.setEnabled(false);
                EndGame.setEnabled(true);
                RemCard1.setEnabled(true);
                RemCard2.setEnabled(true);
                RemCard3.setEnabled(true);
                RemCard4.setEnabled(true);
                RemCard5.setEnabled(true);
                refreshDisplay(h);
            }
        }
);
        DrawCards = new JButton("Draw Cards");
        DrawCards.setBounds(446, 50, 117, 42);
        getContentPane().add(DrawCards);
        DrawCards.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                Draws = Integer.valueOf(Draws.intValue() - 1);
                h.addCardsfromDeck(d, 5);
                DrawCards.setEnabled(false);
                EndGame.setEnabled(true);
                refreshDisplay(h);
//                try
//                {
//                    System.out.println(insertString(uname, gameid, 3 - Draws.intValue(), h));
//                    s1.executeUpdate(insertString(uname, gameid, 3 - Draws.intValue(), h));
//                }
//                catch(SQLException e1)
//                {
//                    e1.printStackTrace();
//                }
            }
        }
);
        EndGame = new JButton("End Game");
        EndGame.setBounds(446, 98, 117, 42);
        getContentPane().add(EndGame);
        EndGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
            	Draws = 0;
//                try
//                {
//                    for(; Draws.intValue() > 0; s1.executeUpdate(insertString(uname, gameid, 3 - Draws.intValue(), h)))
//                    {
//                        Draws = Integer.valueOf(Draws.intValue() - 1);
//                        System.out.println(insertString(uname, gameid, 3 - Draws.intValue(), h));
//                    }
//
//                }
//                catch(SQLException e1)
//                {
//                    e1.printStackTrace();
//                }
                refreshDisplay(h);
            }
        }
);
        RemCard1 = new JButton("REM");
        RemCard1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        RemCard1.setBounds(29, 117, 60, 24);
        getContentPane().add(RemCard1);
        RemCard1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(1);
                refreshDisplay(h);
            }
        }
);
        RemCard2 = new JButton("REM");
        RemCard2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        RemCard2.setBounds(95, 117, 60, 24);
        getContentPane().add(RemCard2);
        RemCard2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(2);
                refreshDisplay(h);
            }
        }
);
        RemCard3 = new JButton("REM");
        RemCard3.setFont(new Font("Tahoma", Font.PLAIN, 11));
        RemCard3.setBounds(161, 117, 60, 24);
        getContentPane().add(RemCard3);
        RemCard3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(3);
                refreshDisplay(h);
            }
        }
);
        RemCard4 = new JButton("REM");
        RemCard4.setFont(new Font("Tahoma", Font.PLAIN, 11));
        RemCard4.setBounds(227, 117, 60, 24);
        getContentPane().add(RemCard4);
        RemCard4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(4);
                refreshDisplay(h);
            }
        }
);
        RemCard5 = new JButton("REM");
        RemCard5.setFont(new Font("Tahoma", Font.PLAIN, 11));
        RemCard5.setBounds(293, 117, 60, 24);
        getContentPane().add(RemCard5);
        RemCard5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(5);
                refreshDisplay(h);
            }
        }
);
        JLabel lblNewLabel = new JLabel("Draws Remaining: ");
        lblNewLabel.setEnabled(false);
        lblNewLabel.setBounds(428, 194, 115, 28);
        getContentPane().add(lblNewLabel);
        Draws_Remaining = new JLabel("");
        Draws_Remaining.setEnabled(false);
        Draws_Remaining.setBounds(553, 194, 31, 28);
        getContentPane().add(Draws_Remaining);
        sort_none = new JRadioButton("None");
        sort_none.setSelected(true);
        sort_none.setBounds(119, 162, 65, 23);
        getContentPane().add(sort_none);
        sort_value = new JRadioButton("By Value");
        sort_value.setBounds(186, 162, 80, 23);
        getContentPane().add(sort_value);
        sort_suit = new JRadioButton("By Suit");
        sort_suit.setBounds(274, 162, 80, 23);
        getContentPane().add(sort_suit);
        JLabel lblSort = new JLabel("Sort Hand:");
        lblSort.setBounds(46, 162, 73, 23);
        getContentPane().add(lblSort);
        ButtonGroup sort_opts = new ButtonGroup();
        sort_opts.add(sort_none);
        sort_opts.add(sort_value);
        sort_opts.add(sort_suit);
        Card1 = new JLabel();
        Card1.setBounds(29, 30, 60, 80);
        getContentPane().add(Card1);
        Card2 = new JLabel();
        Card2.setBounds(95, 30, 60, 80);
        getContentPane().add(Card2);
        Card3 = new JLabel();
        Card3.setBounds(161, 30, 60, 80);
        getContentPane().add(Card3);
        Card4 = new JLabel();
        Card4.setBounds(227, 30, 60, 80);
        getContentPane().add(Card4);
        Card5 = new JLabel();
        Card5.setBounds(293, 30, 60, 80);
        getContentPane().add(Card5);
        ActionListener e1 = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent)
            {
                refreshDisplay(h);
            }
        }
;
        sort_none.addActionListener(e1);
        sort_value.addActionListener(e1);
        sort_suit.addActionListener(e1);
        refreshDisplay(h);
    }

    public void displayCard(TextField a, Hand h, int i)
    {
        a.setText(h.Cards[i - 1].CardParams());
    }

    public void refreshDisplay(Hand h)
    {
        Draws_Remaining.setText(Draws.toString());
        if(Draws.intValue() == 0)
        {
            DrawCards.setEnabled(false);
            EndGame.setEnabled(false);
            RemCard1.setEnabled(false);
            RemCard2.setEnabled(false);
            RemCard3.setEnabled(false);
            RemCard4.setEnabled(false);
            RemCard5.setEnabled(false);
        } else
        if(h.getCardCount() < 5)
            DrawCards.setEnabled(true);
        if(h.getCardCount() == 5)
            sort_hand(h);
        else
            EvaluatedHand.setText("");
        Card1.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[0])));
        Card2.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[1])));
        Card3.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[2])));
        Card4.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[3])));
        Card5.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[4])));
        if(h.getCardCount() == 5)
        {
            Hand h2 = new Hand();
            for(int i = 0; i < 5; i++)
                h2.Cards[i] = h.Cards[i];

            if(Draws.intValue() == 0)
                EvaluatedHand.setText((new StringBuilder("Your final hand is")).append(PokerHands.EvaluateSTR(h2).substring(8)).toString());
            else
                EvaluatedHand.setText(PokerHands.EvaluateSTR(h2));
        } else
        {
            EndGame.setEnabled(false);
        }
    }

    public void sort_hand(Hand h)
    {
        if(sort_value.isSelected())
            h.sort();
        else
        if(sort_suit.isSelected())
            h.sort_by_suit();
    }

    private String insertString(String uname, int gameid, int handnum, Hand h)
    {
        Hand h2 = new Hand();
        for(int i = 0; i < 5; i++)
            h2.Cards[i] = h.Cards[i];

        String current_hand = PokerHands.EvaluateSTR(h2);
        h2.sort();
        return MessageFormat.format("insert into hands values(null, {0}, {1}, {2}, \"{3}\", {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12}, {13}) ", new Object[] {
            uname, Integer.valueOf(gameid), Integer.valueOf(handnum), current_hand, Integer.valueOf(h2.Cards[0].getCardValue()), Integer.valueOf(h2.Cards[0].getSuitValue()), Integer.valueOf(h2.Cards[1].getCardValue()), Integer.valueOf(h2.Cards[1].getSuitValue()), Integer.valueOf(h2.Cards[2].getCardValue()), Integer.valueOf(h2.Cards[2].getSuitValue()), 
            Integer.valueOf(h2.Cards[3].getCardValue()), Integer.valueOf(h2.Cards[3].getSuitValue()), Integer.valueOf(h2.Cards[4].getCardValue()), Integer.valueOf(h2.Cards[4].getSuitValue())
        });
    }

    Integer Draws;
    Deck d;
    Hand h;
    JTextField EvaluatedHand;
    JButton NewGame;
    JButton DrawCards;
    JButton EndGame;
    JButton RemCard1;
    JButton RemCard2;
    JButton RemCard3;
    JButton RemCard4;
    JButton RemCard5;
    JLabel Draws_Remaining;
    JRadioButton sort_none;
    JRadioButton sort_value;
    JRadioButton sort_suit;
    JLabel Card1;
    JLabel Card2;
    JLabel Card3;
    JLabel Card4;
    JLabel Card5;
    int gameid;
    Connection con;
    Statement s1;

}