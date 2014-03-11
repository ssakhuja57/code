package poker;

import resources.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Referenced classes of package Poker:
//            PokerHands

public class PokerGame extends JFrame
{

    public PokerGame()
    {
        d = new Deck();
        h = new Hand();
        setTitle("5 Card Draw");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(3);
        Card1 = new TextField();
        Card1.setEditable(false);
        Card1.setBounds(56, 49, 234, 36);
        getContentPane().add(Card1);
        Card2 = new TextField();
        Card2.setEditable(false);
        Card2.setBounds(56, 100, 234, 36);
        getContentPane().add(Card2);
        Card3 = new TextField();
        Card3.setEditable(false);
        Card3.setBounds(56, 154, 234, 36);
        getContentPane().add(Card3);
        Card4 = new TextField();
        Card4.setEditable(false);
        Card4.setBounds(56, 206, 234, 36);
        getContentPane().add(Card4);
        Card5 = new TextField();
        Card5.setEditable(false);
        Card5.setBounds(56, 259, 234, 36);
        getContentPane().add(Card5);
        EvaluatedHand = new TextField();
        EvaluatedHand.setEditable(false);
        EvaluatedHand.setBounds(156, 335, 330, 42);
        Add1 = new Button("Add 1 Card");
        getContentPane().add(EvaluatedHand);
        Add1.setBounds(446, 49, 117, 42);
        getContentPane().add(Add1);
        Add1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.addCardsfromDeck(d, 1);
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        AddAll = new Button("Add All Cards");
        AddAll.setBounds(446, 117, 117, 42);
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

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        Button RemAll = new Button("Remove All Cards");
        RemAll.setBounds(446, 180, 117, 42);
        getContentPane().add(RemAll);
        RemAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCards();
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        Button RemCard1 = new Button("Remove");
        RemCard1.setBounds(304, 49, 79, 24);
        getContentPane().add(RemCard1);
        RemCard1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(1);
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        Button RemCard2 = new Button("Remove");
        RemCard2.setBounds(304, 100, 79, 24);
        getContentPane().add(RemCard2);
        RemCard2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(2);
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        Button RemCard3 = new Button("Remove");
        RemCard3.setBounds(304, 154, 79, 24);
        getContentPane().add(RemCard3);
        RemCard3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(3);
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        Button RemCard4 = new Button("Remove");
        RemCard4.setBounds(304, 206, 79, 24);
        getContentPane().add(RemCard4);
        RemCard4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(4);
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        Button RemCard5 = new Button("Remove");
        RemCard5.setBounds(304, 259, 79, 24);
        getContentPane().add(RemCard5);
        RemCard5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                h.removeCard(5);
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
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

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
            }
        }
);
        restart.setBounds(420, 253, 165, 42);
        getContentPane().add(restart);
        JLabel lblNewLabel = new JLabel("Cards Remaning in Deck:");
        lblNewLabel.setEnabled(false);
        lblNewLabel.setBounds(420, 301, 144, 28);
        getContentPane().add(lblNewLabel);
        Card_count = new JLabel("");
        Card_count.setEnabled(false);
        Card_count.setBounds(567, 301, 56, 28);
        getContentPane().add(Card_count);
        sort_none = new JRadioButton("None");
        sort_none.setSelected(true);
        sort_none.setBounds(616, 105, 109, 23);
        getContentPane().add(sort_none);
        sort_value = new JRadioButton("By Value, Then Suit");
        sort_value.setBounds(616, 136, 144, 23);
        getContentPane().add(sort_value);
        sort_suit = new JRadioButton("By Suit, Then Value");
        sort_suit.setBounds(616, 167, 144, 23);
        getContentPane().add(sort_suit);
        JLabel lblSort = new JLabel("Sort Hand:");
        lblSort.setBounds(616, 84, 73, 14);
        getContentPane().add(lblSort);
        ButtonGroup sort_opts = new ButtonGroup();
        sort_opts.add(sort_none);
        sort_opts.add(sort_value);
        sort_opts.add(sort_suit);
        ActionListener e1 = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent)
            {
                refreshDisplay(h);
            }

            final PokerGame this$0;

            
            {
                this$0 = PokerGame.this;
                //super();
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
        Card1.setText(h.ShowCardSTR(1));
        Card2.setText(h.ShowCardSTR(2));
        Card3.setText(h.ShowCardSTR(3));
        Card4.setText(h.ShowCardSTR(4));
        Card5.setText(h.ShowCardSTR(5));
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

    public void EmptyCard(TextField a)
    {
        a.setText("Empty");
    }

    public void EmptyAll()
    {
        Card1.setText("Empty");
        Card2.setText("Empty");
        Card3.setText("Empty");
        Card4.setText("Empty");
        Card5.setText("Empty");
    }

    Deck d;
    Hand h;
    Hand h2;
    TextField Card1;
    TextField Card2;
    TextField Card3;
    TextField Card4;
    TextField Card5;
    TextField EvaluatedHand;
    Button Add1;
    Button AddAll;
    JLabel Card_count;
    JRadioButton sort_none;
    JRadioButton sort_value;
    JRadioButton sort_suit;
}