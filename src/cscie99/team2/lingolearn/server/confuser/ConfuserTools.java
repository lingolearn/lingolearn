package cscie99.team2.lingolearn.server.confuser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cscie99.team2.lingolearn.shared.error.ConfuserException;

/**
 * The following class contains functions that are largely for the support of
 * the confuser algorithm.
 */
public class ConfuserTools {
	// The path to the directory of blacklists
	private final static String BLACKLIST_DIRECTORY = "/blacklist/";
	
	// The extension for the files
	private final static String BLACKLIST_EXTENSION = ".blacklist";
	
	/**
	 * Check to see if the supplied string is on the blacklist.
	 * 
	 * @param pharse The phrase to be checked.
	 * @return True if the phrase is on the blacklist, false otherwise. If a
	 * blacklist for the given language cannot be found in WEB-INF/blacklists
	 * then it is assumed that all works are valid.
	 */
	public static boolean onBlackList(String pharse, String langaugeCode) throws ConfuserException, IOException {
		// Open the black list for this language and check to see if it exists
		String path = BLACKLIST_DIRECTORY + langaugeCode + BLACKLIST_EXTENSION;
		InputStream blacklist = ConfuserTools.class.getResourceAsStream(path);
		if (blacklist == null) {
			return false;
		}
		// Start processing the contents of the file
		BufferedReader reader = null;
		try {
			// Create a reader for the file
			reader = new BufferedReader(new InputStreamReader(blacklist));
			// Load the context of the file and return if we find a match
			String data;
			while ((data = reader.readLine()) != null) {
				// Press on if the line is blank or is a comment
				if (data.isEmpty() || data.startsWith("#")) {
					continue;
				}
				// Only keep values before a comment
				if (data.contains("#")) {
					data = data.substring(0, data.indexOf('#'));
				}
				// Do we have a match?
				if (data.trim().equals(pharse)) {
					return true;
				}
			}
			return false;
		} catch (FileNotFoundException ex) {
			throw new ConfuserException("Unable to open the blacklist for " + langaugeCode);
		} catch (IOException ex) {
			throw new ConfuserException("An error occured while reading the next line", ex);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}	
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
