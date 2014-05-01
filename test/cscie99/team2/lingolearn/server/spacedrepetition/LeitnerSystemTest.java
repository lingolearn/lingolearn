package cscie99.team2.lingolearn.server.spacedrepetition;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

/**
 * Test to make sure the Leitner System spaced repetition algorithm is working
 * correctly.
 */
public class LeitnerSystemTest {
	// The deck used for the tests
	private Deck deck;
	
	// The system object used for the tests
	private SpacedRepetition study;
	
	private static final Map<Integer, LeitnerSystemSession> sessions;
	static {
		sessions = new HashMap<Integer, LeitnerSystemSession>();
		sessions.put(0, new LeitnerSystemSession("ABC", new String[] {"3", "012", ""}));
		sessions.put(1, new LeitnerSystemSession("DC", new String[] {"01", "3", "2"}));
		sessions.put(2, new LeitnerSystemSession("AD", new String[] {"12", "0", "3"}));
	};
	
	/**
	 * Get an array of the sessions from the current Leitner System object.
	 * 
	 * @return An array that can be used for validation against the expected
	 * session state.
	 */
	@SuppressWarnings("rawtypes")
	private String[] getSessionArray() {
		ArrayList<String> state = new ArrayList<String>();
		for (List session : ((LeitnerSystem)study).getSessions()) {
			String values = "";
			for (Object value : session) {
				values += value;
			}
			state.add(values);
		}
		return state.toArray(new String[state.size()]);
	}
	
	/**
	 * Prepare to run the test by creating the deck of cards.
	 */
	@Before
	public void Setup() throws SpacedRepetitionException {
		// Setup the deck to use for studying
		deck = new Deck();
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
		
		// Get a Leitner System object
		study = SpacedRepetitionFactory.getSpacedRepetition(LeitnerSystem.class.getCanonicalName());
		study.setDeck(deck);
	}
	
	/**
	 * Iterate through three study sessions and make sure the state of the
	 * system is what we expect each time.
	 */
	@Test
	public void testSessions() throws SpacedRepetitionException {
		for (int session = 0; session < LeitnerSystem.NUMBER_SESSIONS; session++) {
			System.out.println("Session " + session);
			// Do the necessary studying for this session
			String answers = sessions.get(session).getCorrect();
			study.shuffleDeck();
			while (study.cardsRemaining()) {
				Card card = study.drawCard();
				boolean correct = answers.contains(card.getNativeLanguage());
				study.setResult(card, correct);
			}
			// Check to make sure the session state is what we expect
			String[] sessionState = getSessionArray();
			String[] cannonical = sessions.get(session).getSession();
			for (int ndx = 0; ndx < cannonical.length; ndx++) {
				assertEquals(cannonical[ndx].length(), sessionState[ndx].length());
				for (int ndy = 0; ndy < cannonical[ndx].length(); ndy++) {
					char ch = cannonical[ndx].charAt(ndy);
					assertTrue(sessionState[ndx].contains(String.valueOf(ch)));
				}
			}
		}
	}
}
