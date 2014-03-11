// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/21/2013 6:47:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Card_Graphics.java

package resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

// Referenced classes of package Card_Hand_Deck:
//            Card

public class Card_Graphics extends JPanel
{

    public Card_Graphics()
    {
    }

    public static BufferedImage createImage(Card c)
    {
        int cardWidth = 60;
        int cardHeight = 80;
        BufferedImage image = new BufferedImage(cardWidth, cardHeight, 2);
        Graphics2D gr = (Graphics2D)image.getGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, cardWidth, cardHeight);
        gr.setColor(Color.BLACK);
        gr.drawRect(0, 0, cardWidth - 1, cardHeight - 1);
        Font font = new Font("Dialog", 0, 14);
        gr.setFont(font);
        if(c == null)
        {
            gr.drawString("EMPTY", 6, 45);
            return image;
        }
        String val;
        if(c.getCardValue() == 10)
            val = c.getValue().substring(0, 2);
        else
            val = c.getValue().substring(0, 1);
        String suitval = c.getSuit().substring(0, 1);
        String suit = "";
        Color color = Color.BLACK;
        if(suitval.equals("S"))
            suit = "\u2660";
        else
        if(suitval.equals("H"))
        {
            suit = "\u2665";
            color = Color.RED;
        } else
        if(suitval.equals("C"))
            suit = "\u2663";
        else
        if(suitval.equals("D"))
        {
            suit = "\u2666";
            color = Color.RED;
        }
        int x = 5;
        if(c.getCardValue() == 10)
            x = 1;
        Font font1 = new Font("Dialog", 0, 28);
        gr.setFont(font1);
        gr.setColor(color);
        gr.drawString((new StringBuilder(String.valueOf(suit))).append(val).toString(), x, 45);
        return image;
    }
}