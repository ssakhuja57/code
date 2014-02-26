package poker;

import resources.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Referenced classes of package Poker:
//            PokerHands

public class PokerGamev2 extends JFrame
{

    public PokerGamev2()
    {
        d = new Deck();
        h = new Hand();
        setTitle("5 Card Draw");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(3);
        Dimension dim = new Dimension(625, 300);
        setPreferredSize(dim);
        pack();
        setResizable(false);
        EvaluatedHand = new TextField();
        EvaluatedHand.setEditable(false);
        EvaluatedHand.setBounds(29, 208, 356, 42);
        Add1 = new Button("Add 1 Card");
        getContentPane().add(EvaluatedHand);
        Add1.setBounds(446, 30, 117, 42);
        getContentPane().add(Add1);
        Add1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.addCardsfromDeck(d, 1);
                refreshDisplay(h);
            }
        }
);
        AddAll = new Button("Add All Cards");
        AddAll.setBounds(446, 78, 117, 42);
        getContentPane().add(AddAll);
        AddAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                if(d.getCardPosition() > 47)
                    h.addCardsfromDeck(d, 52 - d.getCardPosition());
                else
                    h.addCardsfromDeck(d, 5);
                refreshDisplay(h);
            }
        }
        );
        Button RemAll = new Button("Remove All Cards");
        RemAll.setBounds(446, 126, 117, 42);
        getContentPane().add(RemAll);
        RemAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCards();
                refreshDisplay(h);
            }
        }
);
        Button RemCard1 = new Button("Remove");
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
        Button RemCard2 = new Button("Remove");
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
        Button RemCard3 = new Button("Remove");
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
        Button RemCard4 = new Button("Remove");
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
        Button RemCard5 = new Button("Remove");
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
        Button restart = new Button("Shuffle Deck and Restart");
        restart.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCards();
                d.shuffle();
                Add1.setEnabled(true);
                AddAll.setEnabled(true);
                refreshDisplay(h);
            }
        }
);
        restart.setBounds(420, 174, 165, 42);
        getContentPane().add(restart);
        JLabel lblNewLabel = new JLabel("Cards Remaning in Deck:");
        lblNewLabel.setEnabled(false);
        lblNewLabel.setBounds(419, 222, 144, 28);
        getContentPane().add(lblNewLabel);
        Card_count = new JLabel("");
        Card_count.setEnabled(false);
        Card_count.setBounds(563, 222, 56, 28);
        getContentPane().add(Card_count);
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
        Card_count.setText(String.valueOf(52 - d.getCardPosition()));
        if(h.getCardCount() == 5)
        {
            sort_hand(h);
            h2 = new Hand();
            for(int i = 0; i < 5; i++)
                h2.Cards[i] = h.Cards[i];

            EvaluatedHand.setText(PokerHands.EvaluateSTR(h2));
        } else
        {
            EvaluatedHand.setText("");
        }
        Card1.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[0])));
        Card2.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[1])));
        Card3.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[2])));
        Card4.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[3])));
        Card5.setIcon(new ImageIcon(Card_Graphics.createImage(h.Cards[4])));
        if(d.getCardPosition() == 52)
        {
            Add1.setEnabled(false);
            AddAll.setEnabled(false);
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

    Deck d;
    Hand h;
    Hand h2;
    TextField EvaluatedHand;
    Button Add1;
    Button AddAll;
    JLabel Card_count;
    JRadioButton sort_none;
    JRadioButton sort_value;
    JRadioButton sort_suit;
    JLabel Card1;
    JLabel Card2;
    JLabel Card3;
    JLabel Card4;
    JLabel Card5;
}