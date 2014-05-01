package cscie99.team2.lingolearn.server.spacedrepetition;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.*;

import org.junit.Test;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

public class SpacedRepetitionTest {
	/**
	 * Get a deck that can be used for testing of spaced repetition algorithms.
	 * 
	 * @return A deck containing four cards with letters "A" through "D".
	 */
	public static Deck getTestDeck() {
		// Setup the deck to use for studying
		Deck deck = new Deck();
		Card card = new Card("A", "A", "A", "A", "A", "A");
		card.setId(0l);
		deck.add(card);
		card = new Card("B", "B", "B", "B", "B", "B");
		card.setId(1l);
		deck.add(card);
		card = new Card("C", "C", "C", "C", "C", "C");
		card.setId(2l);
		deck.add(card);
		card = new Card("D", "D", "D", "D", "D", "D");
		card.setId(3l);
		deck.add(card);
		return deck;
	}
	
	/**
	 * Test to ensure that object creation is working correctly.
	 */
	@Test
	public void testObjectCreation() throws SpacedRepetitionException {
		String name = BasicRandomization.class.getCanonicalName();
		SpacedRepetition object = SpacedRepetitionFactory.getSpacedRepetition(name);
		assertThat(object, instanceOf(BasicRandomization.class));
	}
}
