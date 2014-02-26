package poker;

import resources.Card;
import resources.Hand;

public class PokerHands
{

    public PokerHands()
    {
    }

    public static String EvaluateSTR(Hand a)
    {
        int ans[] = {
            8, 14
        };
        if(straight_flush(a) != 0)
            return prSTR((new StringBuilder(String.valueOf(Card.convert_to_value(straight(a))))).append("-high STRAIGHT FLUSH.").toString(), check(ans, straight_flush(a)));
        if(quad(a) != 0)
            return prSTR((new StringBuilder("FOUR OF A KIND, ")).append(Card.convert_to_value(quad(a))).append("'s.").toString(), false);
        if(full_house(a) != null)
            return prSTR((new StringBuilder("FULL HOUSE, ")).append(Card.convert_to_value(full_house(a)[0])).append("'s over ").append(Card.convert_to_value(full_house(a)[1])).append("'s.").toString(), false);
        if(flush(a) != 0)
            return prSTR((new StringBuilder(String.valueOf(Card.convert_to_value(flush(a))))).append("-high FLUSH, ").append(a.Cards[0].getSuit()).append(".").toString(), check(ans, flush(a)));
        if(straight(a) != 0)
            return prSTR((new StringBuilder(String.valueOf(Card.convert_to_value(straight(a))))).append("-high STRAIGHT.").toString(), check(ans, straight(a)));
        if(triple(a) != 0)
            return prSTR((new StringBuilder("THREE OF A KIND, ")).append(Card.convert_to_value(triple(a))).append("'s.").toString(), false);
        if(two_pair(a) != null)
            return prSTR((new StringBuilder("TWO PAIR, ")).append(Card.convert_to_value(two_pair(a)[0])).append("'s and ").append(Card.convert_to_value(two_pair(a)[1])).append("'s.").toString(), false);
        if(pair(a) != 0)
            return prSTR((new StringBuilder("PAIR of ")).append(Card.convert_to_value(pair(a))).append("'s.").toString(), false);
        else
            return prSTR((new StringBuilder(String.valueOf(Card.convert_to_value(high_card(a))))).append("-high.").toString(), check(ans, high_card(a)));
    }

    private static String prSTR(String line, boolean test)
    {
        if(test)
            return (new StringBuilder("You have an ")).append(line).toString();
        else
            return (new StringBuilder("You have a ")).append(line).toString();
    }

    public static void Evaluate(Hand a)
    {
        int ans[] = {
            8, 14
        };
        if(straight_flush(a) != 0)
            pr((new StringBuilder(String.valueOf(Card.convert_to_value(straight(a))))).append("-high STRAIGHT FLUSH.").toString(), check(ans, straight_flush(a)));
        else
        if(quad(a) != 0)
            pr((new StringBuilder("FOUR OF A KIND, ")).append(Card.convert_to_value(quad(a))).append("'s.").toString(), false);
        else
        if(full_house(a) != null)
            pr((new StringBuilder("FULL HOUSE, ")).append(Card.convert_to_value(full_house(a)[0])).append("'s over ").append(Card.convert_to_value(full_house(a)[1])).append("'s.").toString(), false);
        else
        if(flush(a) != 0)
            pr((new StringBuilder(String.valueOf(Card.convert_to_value(flush(a))))).append("-high FLUSH, ").append(a.Cards[0].getSuit()).append(".").toString(), check(ans, flush(a)));
        else
        if(straight(a) != 0)
            pr((new StringBuilder(String.valueOf(Card.convert_to_value(straight(a))))).append("-high STRAIGHT.").toString(), check(ans, straight(a)));
        else
        if(triple(a) != 0)
            pr((new StringBuilder("THREE OF A KIND, ")).append(Card.convert_to_value(triple(a))).append("'s.").toString(), false);
        else
        if(two_pair(a) != null)
            pr((new StringBuilder("TWO PAIR, ")).append(Card.convert_to_value(two_pair(a)[0])).append("'s and ").append(Card.convert_to_value(two_pair(a)[1])).append("'s.").toString(), false);
        else
        if(pair(a) != 0)
            pr((new StringBuilder("PAIR of ")).append(Card.convert_to_value(pair(a))).append("'s.").toString(), false);
        else
            pr((new StringBuilder(String.valueOf(Card.convert_to_value(high_card(a))))).append("-high.").toString(), check(ans, high_card(a)));
    }

    private static void pr(String line, boolean test)
    {
        if(test)
            System.out.println((new StringBuilder("You have an ")).append(line).toString());
        else
            System.out.println((new StringBuilder("You have a ")).append(line).toString());
    }

    private static boolean check(int a[], int b)
    {
        int ai[];
        int k = (ai = a).length;
        for(int j = 0; j < k; j++)
        {
            int i = ai[j];
            if(i == b)
                return true;
        }

        return false;
    }

    private static int high_card(Hand a)
    {
        a.sort();
        return a.Cards[4].getCardValue();
    }

    private static int pair(Hand a)
    {
        a.sort();
        for(int i = 1; i < 5; i++)
            if(a.Cards[i].getCardValue() == a.Cards[i - 1].getCardValue())
                return a.Cards[i].getCardValue();

        return 0;
    }

    private static int[] two_pair(Hand a)
    {
        a.sort();
        int b = pair(a);
        a.reverse();
        int c = 0;
        for(int i = 1; i < 5; i++)
        {
            if(a.Cards[i].getCardValue() != a.Cards[i - 1].getCardValue())
                continue;
            c = a.Cards[i].getCardValue();
            break;
        }

        a.sort();
        if(b == c && quad(a) == 0)
        {
            return null;
        } else
        {
            int res[] = new int[2];
            res[0] = b;
            res[1] = c;
            return res;
        }
    }

    private static int triple(Hand a)
    {
        a.sort();
        for(int i = 1; i < 5; i++)
            if(a.Cards[i].getCardValue() == a.Cards[i - 1].getCardValue() && i < 4 && a.Cards[i].getCardValue() == a.Cards[i + 1].getCardValue())
                return a.Cards[i].getCardValue();

        return 0;
    }

    private static int straight(Hand a)
    {
        a.sort();
        if(a.Cards[4].getCardValue() != 14 || a.Cards[0].getCardValue() != 2)
        {
            for(int i = 1; i < 5; i++)
                if(a.Cards[i].getCardValue() != a.Cards[i - 1].getCardValue() + 1)
                    return 0;

            return a.Cards[4].getCardValue();
        }
        for(int i = 1; i < 4; i++)
            if(a.Cards[i].getCardValue() != a.Cards[i - 1].getCardValue() + 1)
                return 0;

        return a.Cards[3].getCardValue();
    }

    private static int flush(Hand a)
    {
        int b = a.Cards[0].getSuitValue();
        for(int i = 1; i < 5; i++)
            if(a.Cards[i].getSuitValue() != b)
                return 0;

        a.sort();
        return a.Cards[4].getCardValue();
    }

    private static int[] full_house(Hand a)
    {
        if(triple(a) != 0 && two_pair(a) != null && quad(a) == 0)
        {
            int c = 0;
            int b = triple(a);
            for(int i = 0; i < 4; i++)
            {
                if(a.Cards[i].getCardValue() == b)
                    continue;
                c = a.Cards[i].getCardValue();
                break;
            }

            int res[] = new int[2];
            res[0] = c;
            res[1] = b;
            return res;
        } else
        {
            return null;
        }
    }

    private static int quad(Hand a)
    {
        a.sort();
        if(triple(a) != 0 && (a.Cards[0].getCardValue() == a.Cards[3].getCardValue() || a.Cards[1].getCardValue() == a.Cards[4].getCardValue()))
            return triple(a);
        else
            return 0;
    }

    private static int straight_flush(Hand a)
    {
        if(straight(a) != 0 && flush(a) != 0)
            return straight(a);
        else
            return 0;
    }

    @SuppressWarnings("unused")
	private static Hand low_ace(Hand a)
    {
        Hand res = new Hand();
        for(int i = 0; i < 5; i++)
            if(a.Cards[i].getCardValue() == 14)
                a.Cards[i] = new Card(3, 12);

        return res;
    }

    public static int CompareHighCards(Hand a, Hand b)
    {
        a.sort();
        b.sort();
        for(int i = 4; i >= 0; i--)
        {
            if(a.Cards[i].getCardValue() > b.Cards[i].getCardValue())
                return 1;
            if(a.Cards[i].getCardValue() < b.Cards[i].getCardValue())
                return -1;
        }

        return 0;
    }
}