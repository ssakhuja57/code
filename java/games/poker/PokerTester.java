package poker;


// Referenced classes of package Poker:
//            PokerGamev3

public class PokerTester
{

    public PokerTester()
    {
    }

    public static void main(String args[])
        throws Exception
    {
        PokerGamev3 game = new PokerGamev3("test");
        game.setVisible(true);
    }
}