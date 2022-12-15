package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;
// workshop 15 team 10


public class LegalStrategy implements NPCStrategy{


    @Override
    public Card play(NPC NPCPlayer, Oh_Heaven oh_heaven, RoundInformation roundInformation) {
        Hand hand = NPCPlayer.getHand();
        Oh_Heaven.Suit lead = oh_heaven.getLead();

        // if this npc takes the lead
        if (lead == null){
            Oh_Heaven.randomCard(hand);
        }

        ArrayList<Card> cardsWithSameSuit = NPCPlayer.getHand().getCardsWithSuit(lead);  // store card with same suit


        // if arraylist is empty, random play card, else, play card in arraylist randomly
        if (cardsWithSameSuit.isEmpty()){
            return Oh_Heaven.randomCard(hand);
        }

        return Oh_Heaven.randomCardArray(cardsWithSameSuit);


    }
}
