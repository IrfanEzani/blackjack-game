package blackjack;

public enum HandAssessment {
	INSUFFICIENT_CARDS,       // A hand with less than 2 cards
	BUST,                     // A hand with a value over 21
	NATURAL_BLACKJACK,        // A hand that is a "Natural Blackjack"
	NORMAL;                   // A hand that is "none of the above"
}
