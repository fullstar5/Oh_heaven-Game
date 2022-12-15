package oh_heaven.game;

// workshop 15 team 10

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public abstract class Player {

    private int index;
    private int trick;
    private int bid;
    private int score;

    protected Hand hand;

    public Player(int index) {
        this.index = index;
    }


    // the method for every player to play card in each round
    public abstract Card playCard(Oh_Heaven oh_heaven);


    public void updateScore(int bonus) {
        score += trick;
        if (trick == bid) {
            score += bonus;
        }
    }



    // getters -----------------------------------
    public int getIndex() {
        return index;
    }

    public int getTrick() {
        return trick;
    }

    public int getBid() {
        return bid;
    }

    public int getScore() {
        return score;
    }

    public Hand getHand() {
        return hand;
    }




    // setters ------------------------------------------------------
    public void setIndex(int index) {
        this.index = index;
    }

    public void setTrick(int trick) {
        this.trick = trick;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }



}
