package oh_heaven.game;

import ch.aplu.jcardgame.Card;

// workshop 15 team 10


public interface NPCStrategy {

    public Card play(NPC NPCPlayer, Oh_Heaven oh_heaven, RoundInformation roundInformation);

}
