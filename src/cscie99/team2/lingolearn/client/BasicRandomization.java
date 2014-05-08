package cscie99.team2.lingolearn.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.Random;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

/**
 * This class implements a basic randomization method, that isn't spaced
 * repetition per se.
 */
public class BasicRandomization extends SpacedRepetition {
	// The shuffled deck of cards that is drawn off of 
	private List<Long> shuffledDeck = null;
	
	@Override
	public Long drawCard() throws SpacedRepetitionException {
		// Check for issues with the deck
		if (deck == null) {
			throw new SpacedRepetitionException("The deck has not been provided yet.");
		}
		if (shuffledDeck == null) {
			throw new SpacedRepetitionException("The deck has not been shuffled yet.");
		}
		if (shuffledDeck.size() == 0) {
			throw new SpacedRepetitionException("The deck needs to be reshuffled.");
		}
		return shuffledDeck.remove(0);
	}
	
	@Override
	public boolean cardsRemaining() {
		return (shuffledDeck.size() > 0);
	}
	
	@Override
	public void shuffleDeck() throws SpacedRepetitionException {
		// Make sure we have a deck to work with
		if (deck == null) {
			throw new SpacedRepetitionException("The deck has not been provided yet.");
		}
		// Copy the card ids over and shuffle the deck
		shuffledDeck = new ArrayList<Long>(deck.getCardIds());
		//Collections.shuffle(shuffledDeck);
		for(int index = 0; index < shuffledDeck.size(); index += 1) {  
			Collections.swap(shuffledDeck, index, Random.nextInt(shuffledDeck.size()));  
		} 
	}

	/**
	 * Overridden method, since basic randomization does not need this 
	 * information, it is silently dropped.
	 */
	@Override
	public void setResult(Card card, boolean correct) { }
}
