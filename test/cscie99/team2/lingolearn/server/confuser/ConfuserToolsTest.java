package cscie99.team2.lingolearn.server.confuser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import cscie99.team2.lingolearn.shared.error.ConfuserException;

public class ConfuserToolsTest {
	/**
	 * Check to make sure the black list is running correctly by using
	 * a phrase from the middle of the list. 
	 */
	@Test
	public void testOnBlackList() throws ConfuserException, IOException {
		// Check the phrase "Stop being stupid" against the blacklist
		assertEquals(true, ConfuserTools.onBlackList("ふざけるな", "jp"));
		// Check to phrase "Hello" against the blacklist
		assertEquals(false, ConfuserTools.onBlackList("こんにちは", "jp"));
	}
	
	/**
	 * Check the detection of Unicode ranges by using the standard ranges from
	 * http://www.unicode.org/charts/
	 */
	@Test
	public void testCheckCharacter() throws ConfuserException {
		// Check to make sure unknowns work by checking against basic ASCII
		for (char ndx = 0x021; ndx < 0x007E; ndx++) {
			assertEquals(CharacterType.Unknown, ConfuserTools.checkCharacter(String.valueOf(ndx)));
		}
		// Check to make sure Hiragana pass
		for (char ndx = 0x3041; ndx < 0x3096; ndx++) {
			assertEquals(CharacterType.Hiragana, ConfuserTools.checkCharacter(String.valueOf(ndx)));
		}
		// Check to make sure the katakana pass
		for (char ndx = 0x30A1; ndx < 0x30FC; ndx++) {
			assertEquals(CharacterType.Katakana, ConfuserTools.checkCharacter(String.valueOf(ndx)));
		}
		// Check to make sure the kanji pass, note that we are checking the 
		// full CJK range so this includes kanji that are not used in Japanese
		for (char ndx = 0x4E00; ndx < 0x9FAF; ndx++) {
			assertEquals(CharacterType.Kanji, ConfuserTools.checkCharacter(String.valueOf(ndx)));
		}
	}
}
