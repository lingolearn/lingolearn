package cscie99.team2.lingolearn.server.spacedrepetition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

/**
 * This class implements the Leitner System of spaced-repetition.
 */
public class LeitnerSystem extends SpacedRepetition {
	// The number of sessions that the algorithm uses
	public static int NUMBER_SESSIONS = 3;
	
	// The following stores the session information for the Leitner system,
	// each of the sessions contains the card ids.
	@SuppressWarnings("rawtypes")
	private ArrayList<List> sessions;
	
	// The following stores the current shuffled deck
	private ArrayList<Long> shuffledDeck;
	
	// The current session we are in
	private int currentSession = 0;
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public LeitnerSystem() {
		sessions = new ArrayList<List>();
		for (int ndx = 0; ndx < NUMBER_SESSIONS; ndx++) {
			sessions.add(new ArrayList<Long>());
		}
	}
	
	@Override
	public boolean CardsRemaining() {
		return (shuffledDeck.size() > 0);
	}

	@Override
	public Card DrawCard() throws SpacedRepetitionException {
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
		// Remove and return the first card in the draw deck
		try {
			return deck.getCard(shuffledDeck.remove(0));
		} catch (CardNotFoundException ex) {
			throw new SpacedRepetitionException("An error occured while drawing the card.", ex);
		}
	}
	
	/**
	 * Prepare the deck to be used by preparing the initial session.
	 */
	@Override
	public void SetDeck(Deck deck) {
		// Let the parent class set the deck
		super.SetDeck(deck);
		// Create the first session for the user
		currentSession = -1;
		sessions.set(0, this.deck.getCardIds());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void SetResult(Card card, boolean correct) {
		// Start by finding where the card is
		for (int ndx = 0; ndx < currentSession; ndx++) {
			for (int ndy = 0; ndy < sessions.get(ndx).size(); ndx++) {
				// If this isn't the right card, press on
				if (sessions.get(ndx).get(ndy) != card.getId()) {
					continue;
				}
				// We found the right card, so remove it 
				sessions.get(ndx).remove(ndy);
				// Find the session to insert the card into, default to the 
				// first session which is where wrong answers go
				int next = 0;
				// If the use is correct then the card goes in the next session
				// unless we are already at the last one
				if (correct) {
					next = ((ndx + 1) != NUMBER_SESSIONS) ? ndx + 1 : NUMBER_SESSIONS - 1; 
				}
				// Add the card and return
				sessions.get(next).add(card.getId());
				return;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void ShuffleDeck() throws SpacedRepetitionException {
		// Update the current session and cycle as needed
		currentSession++;
		if (currentSession == NUMBER_SESSIONS) {
			currentSession = 0;
		}
		// Move the cards for the sessions into the current shuffled deck
		shuffledDeck = new ArrayList<Long>();
		for (int ndx = 0; ndx < currentSession; ndx++) {
			shuffledDeck.addAll(sessions.get(ndx));
		}
		Collections.shuffle(shuffledDeck);
	}
}
