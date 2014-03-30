package cscie99.team2.lingolearn.server;

import cscie99.team2.lingolearn.shared.error.ConfuserException;

/**
 * This class encapsulates a means of getting characters that are similar to 
 * a given example for the purposes of confusing humans.
 */
public class Confuser {
	/**
	 * The various character types that are recognized by the confuser algorithm
	 */
	public enum CharacterType {
		Unknown,
		Hiragana,
		Katakana,
		Kanji
	}
	
	/**
	 * Check to see what type of Japanese character the given character is.
	 * Note that this code does not detect if a kanji is limited ot use in 
	 * Chinese as opposed to Japanese.
	 * 
	 * @param character The character to be tested.
	 * @return The type of character provided, or unknown if it was not determined.
	 */
	public static CharacterType checkCharacter(String character) throws ConfuserException {
		// Make sure we are only working with a single character
		if (character.length() != 1) {
			throw new ConfuserException("The character string is an invalid length.");
		}		
		// Check to see if this is a hiragana
		char ch = character.charAt(0);
		if (ch >= 0x3041 && ch <= 0x3096) {
			return CharacterType.Hiragana;
		}
		// Check to see if this is a katakana
		if (ch >= 0x30A1 && ch <= 0x30FC) {
			return CharacterType.Katakana;
		}
		// Check to see if this is kanji
		if (ch >= 0x4E00 && ch <= 0x9FAF) {
			return CharacterType.Kanji;
		}
		// Fall through to unknown
		return CharacterType.Unknown;
	}
}
