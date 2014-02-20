// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/21/2013 6:47:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Deck.java

package resources;


// Referenced classes of package Card_Hand_Deck:
//            Card

public class Deck
{

    public Deck()
    {
        card_deck = new Card[52];
        shuffle();
    }

    public Card getTopCard()
    {
        Card TopCard = card_deck[CardPosition];
        card_deck[CardPosition] = null;
        CardPosition++;
        return TopCard;
    }

    public int getCardPosition()
    {
        return CardPosition;
    }

    public void showDeck()
    {
        for(int i = 0; i < 52; i++)
            card_deck[i].showCard();

    }

    public void shuffle()
    {
        CardPosition = 0;
        Card a[][] = new Card[4][13];
        for(int x = 0; x < 4; x++)
        {
            for(int y = 0; y < 13; y++)
                a[x][y] = new Card(x + 1, y + 2);

        }

        for(int z = 0; z < 52; z++)
            for(card_deck[z] = null; card_deck[z] == null;)
            {
                int suit = (int)(Math.random() * 4D);
                int value = (int)(Math.random() * 13D);
                if(a[suit][value] != null)
                {
                    card_deck[z] = a[suit][value];
                    a[suit][value] = null;
                }
            }


    }

    private Card card_deck[];
    private int CardPosition;
}