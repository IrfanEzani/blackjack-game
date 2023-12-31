package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

/**
 * Model of the BlackJack game logic.  
 * The class includes normal getters and setters for the cards.
 * Includes methods required to run the game such as initial player and dealer
 * cards, dealing method, and shuffling the deck.
 * Also includes logical implementation for the game such as hand assessments,
 * game assessments, possible hand values.
 */
public class BlackjackModel {

	/* Instance variables */

	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private Deck deck;

	/* Getters and setters for dealer and player cards.*/

	//returns a new ArrayList for the dealerCards to prevent privacy leaks.
	public ArrayList<Card> getDealerCards() {
		return new ArrayList<Card>(dealerCards);
	}

	//returns a new ArrayList for the dealerCards to prevent privacy leaks.
	public ArrayList<Card> getPlayerCards() {
		return new ArrayList<Card>(playerCards);
	}

	//set dealer cards with a new ArrayList, with cloned elements from param.
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = new ArrayList<Card>(cards);
	}
	
	//set player cards with a new ArrayList, with cloned elements from param.
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = new ArrayList<Card>(cards);
	}
    
	/* Game Methods */

	//instantiate Deck and shuffles the cards using deck's shuffle method.
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}
	
	//instantiate the dealerCard array & add them to that list
	public void initialDealerCards() {
		dealerCards = new ArrayList<Card>();
		for (int i = 0; i < 2; i++) {
			dealerCards.add(deck.dealOneCard());
		}
	}
	 
	//instantiate the dealerCard array & add them to that list 
	public void initialPlayerCards() {
		playerCards = new ArrayList<Card>();
		for (int i = 0; i < 2; i++) {
			playerCards.add(deck.dealOneCard());
		}
	}

	//add a card to the player's current hand from the deck
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}

	//add a card to the deck's current hand from the deck
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}

	/* Game Logic */

	/* Checks for possible hand values.
	 *
	 * An arraylist with size two, a boolean, and two integer local variable
	 * is initialized to store the values. Method first loops through the 
	 * arrayList to find ace.
	 * 
	 * The values of the hand without ace is first added and stored in the
	 * totalWithoutAce variable. If there's an ace, add 10 to the previous
	 * variable.
	 * 
	 * If there's an ace, add both totalWithoutAce and totalWithAce to
	 * the arrayList. If not, only totalWithoutAce is added to the arraylist.
	 * 
	 * Method is created according to BlackJack game logic. For instance;
	 * 
	 * --> {Ace,Three,Five} will return 9/19.
	 * 
	 * --> If the ace becomes 11 and when is added up, 
	 *     and if it exceeds 21, it will not be counted.
	 * 
	 * --> {Ace,King,Two} will ONLY return 13.
	 * 	    23 is not counted as it exceeds 21.
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		ArrayList<Integer> possibleVal = new ArrayList<Integer>(2);
		boolean ace = false;
		int totalWithoutAce = 0, totalWithAce = 0;

		// check for Ace
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank() == Rank.ACE) {
				ace = true;
			}
		}

		// add hand total without ace
		for (int i = 0; i < hand.size(); i++) {
			totalWithoutAce += hand.get(i).getRank().getValue();
		}

		// if there's an ace in the Hand, add 10 to the totalWithAce var.
		if (ace) {
			if (totalWithoutAce <= 11) {
				totalWithAce = totalWithoutAce + 10;
			}
		}

		//add both totals in to the ArrayList, if totalWithAce > 0.
		possibleVal.add(totalWithoutAce);
		if (totalWithAce > 0) {
			possibleVal.add(totalWithAce);
		}
		return possibleVal;
	}

	/* Checks for current hand card configuration.
	 * Using simple if-else statements and returns one of the four
	 * HandAssessment constants according to these conditions:
	 * 
	 * INSUFFICIENT_CARDS: if hand is null or hand size is less than 2 card
	 * NATURAL_BLACKJACK:  if hand equals "Natural Blackjack"
	 * BUST:  if hand's value > 21
	 * NORMAL": other than above

	*/
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if (hand == null || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		} else if (possibleHandValues(hand).size() == 2
				&& possibleHandValues(hand).get(1) == 21
				&& hand.size() == 2) {
			return HandAssessment.NATURAL_BLACKJACK;
		} else if (possibleHandValues(hand).get(0) > 21) {
			return HandAssessment.BUST;
		} else {
			return HandAssessment.NORMAL;
		}
	}

	/* Evaluates result for the game.
	 * Player can either win normally or by natural blackjack, lose or push.
	 * Based on multiple cases.
	*/
	public GameResult gameAssessment() {
		/* 1st Case: Player has Blackjack, dealer doesnt. 
		 * Result: Player wins by Natural Blackjack. 
		 */
		if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK && 
		assessHand(dealerCards) != HandAssessment.NATURAL_BLACKJACK) {
			return GameResult.NATURAL_BLACKJACK;
		} 
		/* 2nd Case: Player & Dealer has Blackjack. 
		 * Result: Push.
		 */
		else if ((assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK)
			&& (assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.PUSH;
			
		} 
		/* 3rd Case: Player is Busted.
		 * Note: Dealer card does not matter in this case. 
		 * Result: Player Lost.
		 */
		else if (assessHand(playerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_LOST;
		} 
		/* 4th Case: Dealer is Busted, Player is not Busted.
		 * Result: Player Win.
		 */
		else if (assessHand(dealerCards) == HandAssessment.BUST &&
				assessHand(playerCards) != HandAssessment.BUST) {
			return GameResult.PLAYER_WON;
			
		} 
		/*  If all doesn't apply, game is evaluate by the value of their cards.
		 *  This is done according to all possible cases.
		 */
		else {
			// 1st Case: Player and Dealer has 2 possible hand values
			if (possibleHandValues(playerCards).size() == 2 &&
				possibleHandValues(dealerCards).size() == 2) {

				//if the biggest player card is bigger than dealer, player win
				if (possibleHandValues(playerCards).get(1) > 
					possibleHandValues(dealerCards).get(1)) {
					return GameResult.PLAYER_WON;
				//if the biggest player card is same as dealer, game is pushed.
				} else if (possibleHandValues(playerCards).get(1) == 
					possibleHandValues(dealerCards).get(1)) {
					return GameResult.PUSH;
				//if the biggest player card is smaller than dealer, player win
				} else {
					return GameResult.PLAYER_LOST;
				}
			}

			// 2nd Case: Player and Dealer has 1 possible hand values
			else if (possibleHandValues(playerCards).size() == 1 &&
					possibleHandValues(dealerCards).size() == 1) {

				//if the biggest player card is bigger than dealer, player win
				if (possibleHandValues(playerCards).get(0) > 
					possibleHandValues(dealerCards).get(0)) {
					return GameResult.PLAYER_WON;
				//if the biggest player card is same as dealer, game is pushed.
				} else if (possibleHandValues(playerCards).get(0) == 
					possibleHandValues(dealerCards).get(0)) {
					return GameResult.PUSH;
				} 
				//if the biggest player card is same as dealer, game is pushed.
				else {
					return GameResult.PLAYER_LOST;
				}
			}
			// 3rd Case: Player has 1 & Dealer has 2 possible hand values
			else if (possibleHandValues(playerCards).size() == 1 &&
					possibleHandValues(dealerCards).size() == 2) {

				//if the biggest player card is bigger than dealer, player win
				if (possibleHandValues(playerCards).get(0) > 
				possibleHandValues(dealerCards).get(1)) {
					return GameResult.PLAYER_WON;
				//if the biggest player card is same as dealer, game is pushed.
				} else if (possibleHandValues(playerCards).get(0) == 
				possibleHandValues(dealerCards).get(1)) {
					return GameResult.PUSH;
				//if the biggest player card is same as dealer, game is pushed.	
				} else {
					return GameResult.PLAYER_LOST;
				}
			}
			// 4th Case: Player has 2 & Dealer has 1 possible hand values
			else {
				//if the biggest player card is bigger than dealer, player win
				if (possibleHandValues(playerCards).get(1) > 
				possibleHandValues(dealerCards).get(0)) {
					return GameResult.PLAYER_WON;
				//if the biggest player card is same as dealer, game is pushed.
				} else if (possibleHandValues(playerCards).get(1) == 
				possibleHandValues(dealerCards).get(0)) {
					return GameResult.PUSH;
				}
				//if the biggest player card is same as dealer, game is pushed.	
				else {
					return GameResult.PLAYER_LOST;
				}
			}
		}
	}

	/* Evaluates when should a dealer take a card. 
	 * A boolean var is set to false. It can only be true in 2 cases;
	 * 
	 * 1. If hand size is 1 and the smallest possible value is less or equal
	 *    to 16.
	 * 2. If hand size is 2, the smallest possible value is less than 8, and
	 *    the largest possible value is less than 18.
	 * 
	 * Method returns the boolean variable.
	*/
	public boolean dealerShouldTakeCard() {
		boolean drawCard = false; 
		if (possibleHandValues(dealerCards).size() == 1 && 
			possibleHandValues(dealerCards).get(0) <= 16) {
				drawCard = true;
		} else if (possibleHandValues(dealerCards).size() == 2 && 
			possibleHandValues(dealerCards).get(0) < 8 &&
			possibleHandValues(dealerCards).get(1) < 18) {
				drawCard = true; 
		}
		return drawCard; 
	}

}
