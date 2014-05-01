package cscie99.team2.lingolearn.server.spacedrepetition;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

public class BasicRandomizationTest {
	// The number of passes to do for this test
	private final static int PASSES = 3;
	
	// The deck to use for the tests
	private Deck deck;
	
	// The size of the deck being used for tests
	private int deckSize;
	
	// The system object used for the tests
	private SpacedRepetition study;
	
	/**
	 * Prepare to run the test by creating the deck of cards and the object.
	 */
	@Before
	public void Setup() throws SpacedRepetitionException {
		deck = SpacedRepetitionTest.getTestDeck();
		deckSize = deck.getSize();
		study = SpacedRepetitionFactory.getSpacedRepetition(BasicRandomization.class.getCanonicalName());
		study.setDeck(deck);
	}

	/**
	 * Test to make sure we can run through the deck more than once and we see
	 * all of the cards each time.
	 */
	@Test
	public void testBasicRandomization() throws SpacedRepetitionException {
		for (int ndx = 0; ndx < PASSES; ndx++) {
			study.shuffleDeck();
			ArrayList<Long> ids = new ArrayList<Long>(deck.getCardIds());
			int count = 0;
			while (study.cardsRemaining()) {
				Card card = study.drawCard();
				ids.remove(card.getId());
				count++;
			}
			assertEquals(deckSize, count);
			assertEquals(0, ids.size());
		}
	}
}
