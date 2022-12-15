package oh_heaven.game;

// Oh_Heaven.java
// workshop 15 team 10


import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import util.RandomUtil;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {


    public enum Suit
	  {
		SPADES, HEARTS, DIAMONDS, CLUBS
	  }


    public enum Rank
	  {
		// Reverse order of rank importance (see rankGreater() below)
		// Order of cards is tied to card images
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	  }


  	final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

	static public final int seed = 30006;
	static final Random random = new Random(seed);

  // return random Enum value
  	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
  	}

  // return random Card from Hand
    public static Card randomCard(Hand hand){
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }
 
  // return random Card from ArrayList
    public static Card randomCardArray(ArrayList<Card> list){
        int x = random.nextInt(list.size());
        return list.get(x);
    }
  
    private void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {
	    Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
		for (int i = 0; i < nbCardsPerPlayer; i++) {
			for (int j=0; j < nbPlayers; j++) {
				if (pack.isEmpty()) return;
				Card dealt = randomCard(pack);
				// System.out.println("Cards = " + dealt);
				dealt.removeFromHand(false);
				hands[j].insert(dealt, false);
				// dealt.transfer(hands[j], true);
			}
		}
	}

	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}
	 
	private final String version = "1.0";
	public final int nbPlayers = 4;
	public int nbStartCards = 13;
	public int nbRounds = 3;
	public int madeBidBonus = 10;
	private final int handWidth = 400;
	private final int trickWidth = 40;
	private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
	private final Location[] handLocations = {
			new Location(350, 625),
			new Location(75, 350),
			new Location(350, 75),
			new Location(625, 350)
	};

	private final Location[] scoreLocations = {
			new Location(575, 675),
			new Location(25, 575),
			new Location(575, 25),
			// new Location(650, 575)
			new Location(575, 575)
	};

	private Actor[] scoreActors = {null, null, null, null };
	private final Location trickLocation = new Location(350, 350);
	private final Location textLocation = new Location(350, 450);
	private int thinkingTime = 2000;
	private Location hideLocation = new Location(-500, - 500);
	private Location trumpsActorLocation = new Location(50, 50);
	private boolean enforceRules = false;


	public void setStatus(String string) { setStatusText(string); }



	private Hand[] hands;
	private int[] scores = new int[nbPlayers];
	private int[] tricks = new int[nbPlayers];
	private int[] bids = new int[nbPlayers];


	// new things for assignment ----------------------------------------
	private List<Player> players = new ArrayList<>();

	// ----------------------------------------------------------------

	Font bigFont = new Font("Serif", Font.BOLD, 36);



	private void updateScore(int player) {
		if (scoreActors[player] != null) {
			removeActor(scoreActors[player]);
		}
		String text = "[" + String.valueOf(players.get(player).getScore()) + "]" + String.valueOf(players.get(player).getTrick()) + "/"
				+ String.valueOf(players.get(player).getBid());
		scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		addActor(scoreActors[player], scoreLocations[player]);
	}

	private void initScores() {
		 for (int i = 0; i < nbPlayers; i++) {
			 scores[i] = 0;
		 }
	}

	private void updateScores() {
		players.forEach(p -> p.updateScore(madeBidBonus));
	}

	private void initTricks() {
		players.forEach(p -> p.setTrick(0));
	}



	private void initBids(Suit trumps, int nextPlayer) {
		int total = 0;
		for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
			int iP = i % nbPlayers;
			players.get(iP).setBid(nbStartCards / 4 + RandomUtil.random.nextInt(2));
			total += players.get(iP).getBid();
//		 	bids[iP] = nbStartCards / 4 + random.nextInt(2);
//		 	total += bids[iP];
		}
		if (total == nbStartCards) {  // Force last bid so not every bid possible
			int iP = (nextPlayer + nbPlayers) % nbPlayers;
			int currBid = players.get(iP).getBid();
			if (currBid == 0) {
				players.get(iP).setBid(1);
			} else {
				players.get(iP).setBid(currBid + (RandomUtil.random.nextBoolean() ? -1 : 1));
			}
//			if (bids[iP] == 0) {
//				bids[iP] = 1;
//			} else {
//				bids[iP] += random.nextBoolean() ? -1 : 1;
//			}
		}
		// for (int i = 0; i < nbPlayers; i++) {
		// 	 bids[i] = nbStartCards / 4 + 1;
		//  }
	}



	private Card selected;

	private void initRound() {
		hands = new Hand[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			hands[i] = new Hand(deck);
			players.get(i).setHand(hands[i]);  // set up hands for each player
		}

		dealingOut(hands, nbPlayers, nbStartCards);
		for (int i = 0; i < nbPlayers; i++) {
			hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		}

//			 // Set up human player for interaction
//			CardListener cardListener = new CardAdapter()  // Human Player plays card
//					{
//					  public void leftDoubleClicked(Card card) { selected = card; hands[0].setTouchEnabled(false); }
//					};
//			hands[0].addCardListener(cardListener);
		for (int i = 0; i < players.size(); i++){
			if (players.get(i) instanceof InteractivePlayer){
				((InteractivePlayer) players.get(i)).initCardListener();
			}
		}

		// graphics
		RowLayout[] layouts = new RowLayout[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			layouts[i] = new RowLayout(handLocations[i], handWidth);
			layouts[i].setRotationAngle(90 * i);
			// layouts[i].setStepDelay(10);
			hands[i].setView(this, layouts[i]);
			hands[i].setTargetArea(new TargetArea(trickLocation));
			hands[i].draw();
		}

	//	    for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide the cards in a hand (make them face down)
	//	      hands[i].setVerso(true);			// You do not need to use or change this code.
			// End graphics
	}


	

	// new things for assignment --------------------------------
	Suit trumps = randomEnum(Suit.class);
	public Suit getTrumps() {
		return trumps;
	}

	private Suit lead;
	public Suit getLead() {
		return lead;
	}

	Card winningCard;
	public Card getWinningCard() {
		return winningCard;
	}

	// ---------------------------------------------------------




	private void playRound() {
		// Select and display trump suit
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
		addActor(trumpsActor, trumpsActorLocation);
		// End trump suit

		Hand trick;
		int winner;

		RoundInformation round = new RoundInformation(trumps);

		int nextPlayer = random.nextInt(nbPlayers);  // randomly select player to lead for this round
		initBids(trumps, nextPlayer);

		for (int i = 0; i < nbPlayers; i++) updateScore(i);

		for (int i = 0; i < nbStartCards; i++) {
			trick = new Hand(deck);
			selected = players.get(nextPlayer).playCard(this);

			// Lead with selected card
			trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;

			//update round information
			round.cardPlayedRecord(nextPlayer, selected);
			round.setLead(lead);
			round.setWinner(winner);
			round.setWinningCard(winningCard);
//			round.setScore();
			// End Lead


			for (int j = 1; j < nbPlayers; j++) {
				if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first

//				selected = null;
//				// if (false) {
//				if (0 == nextPlayer) {
//					hands[0].setTouchEnabled(true);
//					setStatus("Player 0 double-click on card to follow.");
//					while (null == selected) delay(100);
//				} else {
//					setStatusText("Player " + nextPlayer + " thinking...");
//					delay(thinkingTime);
//					selected = randomCard(hands[nextPlayer]);
//				}
				selected = players.get(nextPlayer).playCard(this);

				// Follow with selected card
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
				if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
					// Rule violation
					String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
					System.out.println(violation);
					if (enforceRules)
						try {
							throw(new BrokeRuleException(violation));
						} catch (BrokeRuleException e) {
							e.printStackTrace();
							System.out.println("A cheating player spoiled the game!");
							System.exit(0);
						}
				}
				// End Check

				selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				System.out.println("winning: " + winningCard);
				System.out.println(" played: " + selected);
				// System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				// System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
				if ( // beat current winner with higher card
						(selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
						  // trumped when non-trump was winning
						 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
						 System.out.println("NEW WINNER");
						 winner = nextPlayer;
						 winningCard = selected;
				}

				//update round information
				round.cardPlayedRecord(nextPlayer, selected);
				round.setLead(lead);
				round.setWinner(winner);
				round.setWinningCard(winningCard);
				// End Follow
			}

			delay(600);
			trick.setView(this, new RowLayout(hideLocation, 0));
			trick.draw();
			nextPlayer = winner;
			setStatusText("Player " + nextPlayer + " wins trick.");
			players.get(nextPlayer).setTrick(players.get(nextPlayer).getTrick() + 1);
			updateScore(nextPlayer);
			lead = null;  // clear lead after every round
		}
		removeActor(trumpsActor);
	}




	// new things for assignment -----------------------
	private void setUpPlayers(Properties properties){
		for (int i = 0; i < nbPlayers; i++) {
			String playerType = properties.getProperty(String.format("players.%d", i));
			Player player;
			if (playerType.equals("human")){
				player = new InteractivePlayer(i, this);
			}
			else{
				player = new NPC(i, playerType);
			}
			players.add(player);
			player.setScore(0);
			updateScore(i);
		}
	}




	public Oh_Heaven(Properties properties) {
		super(700, 700, 30);
		setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");

		initProperties(properties);
		setUpPlayers(properties);
		initScores();


		for (int i=0; i <nbRounds; i++) {
		  initTricks();
		  initRound();
		  playRound();
		  updateScores();
		};


		int maxScore = 0;
		Set<Integer> winners = new HashSet<Integer>();
		for (int i = 0; i < nbPlayers; i++) {
			updateScore(i);
			if (players.get(i).getScore() > maxScore) {
				winners.clear();
				maxScore = players.get(i).getScore();
				winners.add(i);
			} else if (players.get(i).getScore() == maxScore) {
				winners.add(i);
			}
		}

//		int maxScore = 0;
//		for (int i = 0; i <nbPlayers; i++) if (scores[i] > maxScore) maxScore = scores[i];
//		Set <Integer> winners = new HashSet<Integer>();
//		for (int i = 0; i <nbPlayers; i++) if (scores[i] == maxScore) winners.add(i);

		String winText;
		if (winners.size() == 1) {
			winText = "Game over. Winner is player: " +
					winners.iterator().next();
		}
		else {
			winText = "Game Over. Drawn winners are players: " +
					String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
		}
		addActor(new Actor("sprites/gameover.gif"), textLocation);
		setStatusText(winText);
		refresh();
	}



	// properties involved
	private void initProperties (Properties properties){

		this.nbStartCards = properties.getProperty("nbStartCards") == null? nbStartCards :
				Integer.parseInt(properties.getProperty("nbStartCards"));

		this.nbRounds = properties.getProperty("rounds") == null? nbRounds :
				Integer.parseInt(properties.getProperty("rounds"));

		this.enforceRules = properties.getProperty("enforceRules") == null? enforceRules :
				Boolean.parseBoolean(properties.getProperty("enforceRules"));

		this.thinkingTime = properties.getProperty("thinkingTime") == null ? thinkingTime :
				Integer.parseInt(properties.getProperty("thinkingTime"));
		this.madeBidBonus = properties.getProperty("bonus") == null ? madeBidBonus :
				Integer.parseInt(properties.getProperty("bonus"));

	}




	public static void main(String[] args) {
		// System.out.println("Working Directory = " + System.getProperty("user.dir"));
		final Properties properties;
		if (args == null || args.length == 0) {
		  	properties = PropertiesLoader.loadPropertiesFile(null);
		}
		else {
			properties = PropertiesLoader.loadPropertiesFile(args[0]);
		}
		new Oh_Heaven(properties);
	}

}
