package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;

// workshop 15 team 10


public class InteractivePlayer extends Player{

    private Card selectedCard;  // card being selected by player

    public InteractivePlayer(int index, Oh_Heaven oh_heaven) {
        super(index);
    }



    @Override
    public Card playCard(Oh_Heaven oh_heaven) {
        selectedCard = null;

        hand.setTouchEnabled(true);

        while (selectedCard == null){
            Oh_Heaven.delay(200);
        }

        hand.setTouchEnabled(false);

        return selectedCard;
    }



    public void initCardListener(){
        // Set up human player for interaction
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                selectedCard = card;
                hand.setTouchEnabled(false); }
        };
        hand.addCardListener(cardListener);
    }


}
