// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/21/2013 6:47:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Card.java

package resources;

import java.io.PrintStream;

public class Card
    implements Comparable
{

    public Card()
    {
        ValueNum = (int)(Math.random() * 13D) + 2;
        SuitNum = (int)(Math.random() * 4D) + 1;
        value = values[ValueNum - 2];
        Suit = Suits[SuitNum - 1];
    }

    public Card(int setSuit, int setvalue)
    {
        ValueNum = setvalue;
        SuitNum = setSuit;
        Suit = Suits[setSuit - 1];
        value = values[setvalue - 2];
    }

    public int getCardValue()
    {
        return ValueNum;
    }

    public int getSuitValue()
    {
        return SuitNum;
    }

    public String getValue()
    {
        return values[ValueNum - 2];
    }

    public String getSuit()
    {
        return Suits[SuitNum - 1];
    }

    public static String convert_to_value(int a)
    {
        return values[a - 2];
    }

    public static String convert_to_suit(int a)
    {
        return Suits[a - 1];
    }

    public String CardParams()
    {
        return (new StringBuilder(String.valueOf(value))).append(" of ").append(Suit).toString();
    }

    public void showCard()
    {
        System.out.printf("%s of %s\n", new Object[] {
            value, Suit
        });
    }

    public int compareTo(Card o)
    {
        if(ValueNum < o.ValueNum)
            return -1;
        if(ValueNum > o.ValueNum)
            return 1;
        if(SuitNum > o.SuitNum)
            return -1;
        return SuitNum >= o.SuitNum ? 0 : 1;
    }

    public int compareTo(Object obj)
    {
        return compareTo((Card)obj);
    }

    private static final String values[] = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", 
        "Queen", "King", "Ace"
    };
    private static final String Suits[] = {
        "Clubs", "Hearts", "Diamonds", "Spades"
    };
    private final String value;
    private final String Suit;
    private final int ValueNum;
    private final int SuitNum;

}