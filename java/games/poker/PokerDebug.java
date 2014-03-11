package poker;

import resources.Deck;
import resources.Hand;


public class PokerDebug
{

    public PokerDebug()
    {
    }

    public static void main(String args[])
    {
        Deck a = new Deck();
        Hand b = new Hand();
        b.getNewHand();
        b.addCardsfromDeck(a, 5);
        b.sort();
        b.ShowHand();
        b.sort_by_suit();
        b.ShowHand();
        PokerHands.Evaluate(b);
        System.out.println("\u2660");
    }
}