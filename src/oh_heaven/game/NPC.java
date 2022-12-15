package oh_heaven.game;

import ch.aplu.jcardgame.Card;

// workshop 15 team 10


public class NPC extends Player{

    private NPCStrategy npc_strategy;
    private Oh_Heaven oh_heaven;
    private RoundInformation roundInformation;

    public NPC(int index, String strategyType) {
        super(index);
        if (strategyType.equals("random")) {
            this.npc_strategy = new RandomStrategy();
        }
        else if (strategyType.equals("legal")) {
            this.npc_strategy = new LegalStrategy();
        }
        else if (strategyType.equals("smart")) {
            this.npc_strategy = new SmartStrategy();
        }

        // if more, just add more if statement...
    }

    @Override
    public Card playCard(Oh_Heaven oh_heaven) {
        return npc_strategy.play(this, oh_heaven, roundInformation);
    }




}
