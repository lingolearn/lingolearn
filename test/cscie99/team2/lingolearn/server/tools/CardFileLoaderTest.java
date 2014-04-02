package cscie99.team2.lingolearn.server.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.FileLoadException;

/**
 * Test to make sure we can load card files to the data store and clean up afterwards.
 */
public class CardFileLoaderTest {
	// The following is the contents of the reference TSV file
	private final static String REFERENCE = 
		"父	ちち	フ	father	en-us	TestingDeck\n\r母	はは	ボ	mother	en-us	TestingDeck";
	
	// The canonical parsing of the cards in REFERENCE
	private final static List<Card> REFERNECE_CARDS = Arrays.asList(
		new Card("父", "ちち", "フ", "father", "en-us", "TestingDeck"),
		new Card("母", "はは", "ボ", "mother", "en-us", "TestingDeck")
	);
	
	/**
	 * Test to make sure we can add cards to the data store.
	 */
	@Test
	public void testLoadFile() {
		try {
			// Copy the reference information to a stream
			InputStream stream = new ByteArrayInputStream(REFERENCE.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			// Create the loader and have it attempt to add the cards
			CardFileLoader loader = new CardFileLoader();
			List<Card> cards = loader.loadCards(reader);
			// Check the cards loaded against our reference set			
			assertEquals(2, cards.size());
			for (int ndx = 0; ndx < REFERNECE_CARDS.size(); ndx++) {
				Card expected = REFERNECE_CARDS.get(ndx);
				Card actual = cards.get(ndx);
				assertEquals(expected.getKanji(), actual.getKanji());
				assertEquals(expected.getHiragana(), actual.getHiragana());
				assertEquals(expected.getKatakana(), actual.getKatakana());
				assertEquals(expected.getTranslation(), actual.getTranslation());
				assertEquals(expected.getNativeLanguage(), actual.getNativeLanguage());
				assertEquals(expected.getDesc(), actual.getKanji());
			}
		} catch (IOException|FileLoadException ex) {
			fail("An exception occured during the test, " + ex.getMessage());
		} 
	}
}
