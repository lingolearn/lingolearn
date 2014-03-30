package cscie99.team2.lingolearn.server;

import static org.junit.Assert.*;

import org.junit.Test;

import cscie99.team2.lingolearn.shared.error.ConfuserException;

public class ConfuserTest {
	/**
	 * Check the detection of Unicode ranges by using the standard ranges from
	 * http://www.unicode.org/charts/
	 */
	@Test
	public void testCheckCharacter() throws ConfuserException {
		// Check to make sure unknowns work by checking against basic ASCII
		for (char ndx = 0x021; ndx < 0x007E; ndx++) {
			assertEquals(Confuser.CharacterType.Unknown, Confuser.checkCharacter(String.valueOf(ndx)));
		}
		// Check to make sure Hiragana pass
		for (char ndx = 0x3041; ndx < 0x3096; ndx++) {
			assertEquals(Confuser.CharacterType.Hiragana, Confuser.checkCharacter(String.valueOf(ndx)));
		}
		// Check to make sure the katakana pass
		for (char ndx = 0x30A1; ndx < 0x30FC; ndx++) {
			assertEquals(Confuser.CharacterType.Katakana, Confuser.checkCharacter(String.valueOf(ndx)));
		}
		// Check to make sure the kanji pass, note that we are checking the 
		// full CJK range so this includes kanji that are not used in Japanese
		for (char ndx = 0x4E00; ndx < 0x9FAF; ndx++) {
			assertEquals(Confuser.CharacterType.Kanji, Confuser.checkCharacter(String.valueOf(ndx)));
		}
	}

}
