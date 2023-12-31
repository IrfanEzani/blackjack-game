package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/** 
 * This class represents a Deck that would include the normal 52 cards.  
 * Class contains a Constructor that initializes the deck, a shuffle method,
 * and a dealing card method.
 */
public class Deck {
	/* Instance variable */
	private ArrayList<Card> cardDeck;
	
	/* Constructor */
	public Deck() {
		cardDeck = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cardDeck.add(new Card(rank, suit));
			}
		}
	}
	/* Shuffle through the deck collection using Collections.shuffle method */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cardDeck, randomNumberGenerator);
	}

	/* Deals a card. Method removes the topmost card in the deck & returns it. */
	public Card dealOneCard() {
		return cardDeck.remove(0);
	}
}
