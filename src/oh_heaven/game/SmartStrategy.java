package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.awt.*;
import java.util.ArrayList;

// workshop 15 team 10


public class SmartStrategy implements NPCStrategy{

    @Override
    public Card play(NPC NPCPlayer, Oh_Heaven oh_heaven, RoundInformation roundInformation) {

        Hand hand = NPCPlayer.getHand();
        Oh_Heaven.Suit trump = oh_heaven.getTrumps();
        Oh_Heaven.Suit lead = oh_heaven.getLead();
        Card winningCard = oh_heaven.getWinningCard();
        Card smallestAtFirst = hand.sort(Hand.SortType.RANKPRIORITY, false);
        ArrayList<Card> cardsWithLeadSuit = NPCPlayer.getHand().getCardsWithSuit(lead);
        ArrayList<Card> cardsWithTrumpSuit = NPCPlayer.getHand().getCardsWithSuit(trump);


        // if this npc takes the lead
        if (lead == null){
            // if npc have trump suit card, play the largest, if not, play largest in rest of cards
            Card card;
            if (cardsWithTrumpSuit.isEmpty()){
                card = hand.getFirst();
            }
            else{
                card = cardsWithTrumpSuit.get(0);
            }
            clearCardArray(cardsWithLeadSuit, cardsWithTrumpSuit);
            return card;
        }



        // if npc has same suits as lead card
        if (cardsWithLeadSuit.size() != 0) {
            if (cardsWithLeadSuit.get(0).getSuit() == winningCard.getSuit()){   // if npc has cards that have same suits as current winning card
                if (oh_heaven.rankGreater(cardsWithLeadSuit.get(0), winningCard)){  // if this card is greater than current winning card
                    return cardsWithLeadSuit.get(0);
                }
                else{
                    return cardsWithLeadSuit.get(cardsWithLeadSuit.size() - 1);
                }
            }
            else{
                return cardsWithLeadSuit.get(cardsWithLeadSuit.size() - 1);
            }
        }

        // if npc doesn't have same cards as lead suit
        else if (cardsWithLeadSuit.isEmpty()){
            if (!cardsWithTrumpSuit.isEmpty()){  // if npc has cards with trump suit
                if (trump == winningCard.getSuit()){  // if current winning card has same suits as trump suit
                    if (oh_heaven.rankGreater(cardsWithTrumpSuit.get(0), winningCard)){  // if npc has cards that greater than current winning card
                        return cardsWithTrumpSuit.get(0);
                    }
                    else{
                        return smallestAtFirst;
//                        return hand.getLast();
                    }
                }
                else{
                    return cardsWithTrumpSuit.get(0);
                }
            }
            else{
                return smallestAtFirst;
//                return hand.getLast();
            }
        }


        return smallestAtFirst;
//        return hand.getLast();
    }


    private void clearCardArray(ArrayList cardsWithLeadSuit, ArrayList cardsWithTrumpSuit){
        cardsWithLeadSuit.clear();
        cardsWithTrumpSuit.clear();
    }
}
