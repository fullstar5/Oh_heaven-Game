package util;

import java.util.ArrayList;
import java.util.Random;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

// workshop 15 team 10

public class RandomUtil {
    public static final int seed = 30006;
    public static final Random random = new Random(seed);

    // return random Card from Hand
    public static Card randomCard(Hand hand) {
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    // return random Card from ArrayList
    public static Card randomCard(ArrayList<Card> list) {
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    // return random Enum value
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
