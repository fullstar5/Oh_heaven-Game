package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

// workshop 15 team 10


public class RandomStrategy implements NPCStrategy{



    @Override
    // randomly selected a card and play, only for random type NPC
    public Card play(NPC NPCPlayer, Oh_Heaven oh_heaven, RoundInformation roundInformation) {
        Hand NPC_hand = NPCPlayer.getHand();
        return Oh_Heaven.randomCard(NPC_hand);
    }



}
