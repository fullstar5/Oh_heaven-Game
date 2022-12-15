package oh_heaven.game;


import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// workshop 15 team 10


public class RoundInformation {

    private Oh_Heaven.Suit trump;
    private Oh_Heaven.Suit lead;
    private Card winningCard;
    private int winner;
    private HashMap<Integer, HashSet<Card>> cardsPlayed;  // cards that every player played

    private ArrayList<Integer> score;  // scores for every player

    public RoundInformation(Oh_Heaven.Suit trump) {
        this.trump = trump;
        this.cardsPlayed = new HashMap<>();
        this.score = new ArrayList<>();
    }


    public void cardPlayedRecord(int player, Card cardPlayed){
        if (cardsPlayed.containsKey(player)){
            cardsPlayed.get(player).add(cardPlayed);
        }
        else{
            HashSet<Card> cardPlayedAlready = new HashSet<>();
            cardPlayedAlready.add(cardPlayed);
            cardsPlayed.put(player, cardPlayedAlready);
        }
    }

    // getters ---------------------------------------------------------------------------------------

    public Oh_Heaven.Suit getTrump() {
        return trump;
    }

    public Oh_Heaven.Suit getLead() {
        return lead;
    }

    public Card getWinningCard() {
        return winningCard;
    }

    public int getWinner() {
        return winner;
    }

    public HashMap<Integer, HashSet<Card>> getCardsPlayed() {
        return cardsPlayed;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }


    // setters-------------------------------------------------------------------------------------------

    public void setTrump(Oh_Heaven.Suit trump) {
        this.trump = trump;
    }

    public void setLead(Oh_Heaven.Suit lead) {
        this.lead = lead;
    }

    public void setWinningCard(Card winningCard) {
        this.winningCard = winningCard;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void setCardsPlayed(HashMap<Integer, HashSet<Card>> cardsPlayed) {
        this.cardsPlayed = cardsPlayed;
    }

    public void setScore(ArrayList<Integer> score) {
        this.score = score;
    }
}
