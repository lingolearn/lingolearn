package cscie99.team2.lingolearn.server.spacedrepetition;

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
	public abstract boolean CardsRemaining();
	
	/**
	 * Get the id of the next card from the deck.
	 * 
	 * @return The next card that should be shown to the user.
	 */
	public abstract Card DrawCard() throws SpacedRepetitionException;
	
	/**
	 * Set the deck that should be used by the algorithm.
	 * 
	 * @param deck The deck to be used by the algorithm.
	 */
	public void SetDeck(Deck deck) {
		this.deck = deck;		
	}
	
	/**
	 * Shuffle the deck of cards. 
	 */
	public abstract void ShuffleDeck() throws SpacedRepetitionException;
}
