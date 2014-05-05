package cscie99.team2.lingolearn.client;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

public abstract class SpacedRepetition {	
	// The main deck that can be used by extending classes
	protected Deck deck = null;
	
	/**
	 * Indicates if there are cards remaining in the deck.
	 * 
	 * @return True if there are cards remaining.
	 */
	public abstract boolean cardsRemaining();
	
	/**
	 * Get the id of the next card from the deck.
	 * 
	 * @return The next card that should be shown to the user.
	 */
	public abstract Long drawCard() throws SpacedRepetitionException;
	
	/**
	 * Set the deck that should be used by the algorithm.
	 * 
	 * @param deck The deck to be used by the algorithm.
	 */
	public void setDeck(Deck deck) {
		this.deck = deck;		
	}
	
	/**
	 * Set the result supplied by the user for the card indicated.
	 * 
	 * @param card The card to set the response for.
	 * @param correct True if the user was correct or knew the card, false 
	 * otherwise.
	 */
	public abstract void setResult(Card card, boolean correct);
	
	/**
	 * Shuffle the deck of cards. 
	 */
	public abstract void shuffleDeck() throws SpacedRepetitionException;
}
