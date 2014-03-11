// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/21/2013 6:47:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Hand.java

package resources;

import java.io.PrintStream;
import java.util.Arrays;

// Referenced classes of package Card_Hand_Deck:
//            Card, Deck

public class Hand
{

    public Hand()
    {
        Cards = new Card[5];
        getNewHand();
    }

    public void getNewHand()
    {
        for(int i = 0; i < 5; i++)
            Cards[i] = null;

    }

    public int getCardCount()
    {
        int count = 0;
        for(int i = 0; i < 5; i++)
            if(Cards[i] != null)
                count++;

        return count;
    }

    public void removeCard(int a)
    {
        Cards[a - 1] = null;
    }

    public void removeCards()
    {
        for(int i = 0; i < 5; i++)
            Cards[i] = null;

    }

    public void addCardsfromDeck(Deck d)
    {
        for(int i = 0; i <= 5; i++)
        {
            if(Cards[i] != null)
                continue;
            Cards[i] = d.getTopCard();
            break;
        }

    }

    public void addCardsfromDeck(Deck d, int a)
    {
        int inserts = 0;
        for(int i = 0; inserts < a && i < 5; i++)
            if(Cards[i] == null)
            {
                Cards[i] = d.getTopCard();
                inserts++;
            }

    }

    public void addCards()
    {
        for(int i = 0; i <= 5; i++)
        {
            if(Cards[i] != null)
                continue;
            Cards[i] = new Card();
            break;
        }

    }

    public void addCards(int a)
    {
        int inserts = 0;
        for(int i = 0; inserts < a; i++)
            if(Cards[i] == null)
            {
                Cards[i] = new Card();
                inserts++;
            }

    }

    public String ShowCardSTR(int a)
    {
        if(Cards[a - 1] == null)
            return "Empty";
        else
            return Cards[a - 1].CardParams();
    }

    public void ShowCard(int a)
    {
        if(Cards[a - 1] == null)
        {
            System.out.printf("/nCard number %d is EMPTY", new Object[] {
                Integer.valueOf(a)
            });
        } else
        {
            System.out.println("Card number %d is the ");
            Cards[a - 1].showCard();
        }
    }

    public void ShowHand()
    {
        for(int i = 0; i < 5; i++)
            if(Cards[i] == null)
                System.out.println("EMPTY");
            else
                Cards[i].showCard();

        System.out.println();
    }

    public void sort()
    {
        Arrays.sort(Cards);
    }

    public void sort_by_suit()
    {
        sort();
        Hand h2 = new Hand();
        for(int j = 0; j < 5; j++)
            h2.Cards[j] = Cards[j];

        for(int insert = 0; insert < 5;)
        {
            for(int suit = 1; suit <= 4; suit++)
            {
                for(int i = 0; i < 5; i++)
                    if(h2.Cards[i].getSuitValue() == suit)
                    {
                        Cards[insert] = h2.Cards[i];
                        insert++;
                    }

            }

        }

    }

    public void reverse()
    {
        Card res[] = new Card[5];
        for(int i = 0; i < 5; i++)
            res[i] = Cards[4 - i];

        Cards = res;
    }

    public Card Cards[];
}