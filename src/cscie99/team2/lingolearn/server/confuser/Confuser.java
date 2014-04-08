package cscie99.team2.lingolearn.server.confuser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.ConfuserException;

/**
 * This class encapsulates a means of getting characters that are similar to a
 * given example for the purposes of confusing humans.
 */
public class Confuser {
	// The path to the directory of confusers
	private final static String CONFUSER_DIRECTORY = "/confusers/";
	
	// The extension for the files
	private final static String CONFUSER_EXTENSION = ".confusers";
	
	// The language code we are working with
	private final static String CONFUSER_LANGUAGE = "jp";
	
	/**
	 * Get a random list of confusers of given type limited to the count 
	 * provided, these results are checked against the black list to 
	 * ensure that nothing inappropriate is returned. 
	 * 
	 * @param card The card to get the confusers for.
	 * @param type The focus of the confusers.
	 * @param count The number that should be returned.
	 * @return A list of string zero to the requested count of confusers.
	 */
	public List<String> getConfusers(Card card, CharacterType type, int count) throws ConfuserException {
		try {
			// Start by running the relevant functions
			List<String> results = new ArrayList<String>();
			switch (type) {
				case Hiragana:
					results.addAll(getNManipulation(card.getHiragana()));
					results.addAll(getSmallTsuManiuplation(card.getHiragana()));
					break;
				case Katakana:
					results.addAll(getNManipulation(card.getKatakana()));
					results.addAll(getSmallTsuManiuplation(card.getKatakana()));
					results.addAll(getVowelManiuplation(card.getKatakana()));
					break;
				case Kanji:
					results.addAll(getKanjiBoundries(card));
					results.addAll(getKanjiSubsitution(card.getKanji()));
					break;
				default:
					throw new ConfuserException("An invalid type, " + type + " was provided.");
			}
			// Check to make sure all of the results are appropriate
			for (int ndx = 0; ndx < results.size(); ndx++) {
				if (ConfuserTools.onBlackList(results.get(ndx), "jp")) {
					results.remove(ndx);
					ndx--;
				}
			}
			// Trim the results down to the count and return them
			Random random = new Random();
			while (results.size() > count) {
				int ndx = random.nextInt(results.size());
				results.remove(ndx);
			}
			return results;
		}
		catch (IOException ex) {
			throw new ConfuserException("There was an error while reading the blacklist.", ex);
		}
	}
		
	/**
	 * Get a list of kanji phrases that have the hiragana extended off the kanji
	 * where appropriate.
	 * 
	 * @param card The card to build the phrases off of.
	 * @return A list of phrases built from the card, or an empty list if there
	 * are no valid confusers.
	 */
	public List<String> getKanjiBoundries(Card card) throws ConfuserException {
		// Check to see if there is only one character which we can't work with
		// and also check to see if the phrase ends with する (to do) since this 
		// approach is invalid
		if (card.getKanji().length() == 1 || card.getKanji().endsWith("する")) {
			return new ArrayList<String>();
		}

		// Prepare to run
		int offset = 0;
		CharacterType previous = null;
		StringBuilder phrase = new StringBuilder();

		// We need to maintain the order of the values, so use two lists
		ArrayList<String> kanjiOrder = new ArrayList<String>();
		ArrayList<Integer> kanjiOffset = new ArrayList<Integer>();

		// First, iterate through the string and break it down into sub strings
		// based upon where it transitions from hiragana to kanji
		String kanji = card.getKanji();
		for (int ndx = 0; ndx < kanji.length(); ndx++) {
			// Store the character
			char ch = kanji.charAt(ndx);
			phrase.append(ch);
			// Check for a boundary
			CharacterType type = ConfuserTools.checkCharacter(ch);
			if (type == CharacterType.Kanji && previous == CharacterType.Hiragana) {
				String value = phrase.toString();
				kanjiOrder.add(value.substring(0, value.length() - 1));
				kanjiOffset.add(offset);
				offset = ndx;
				phrase = new StringBuilder(String.valueOf(ch));
			}
			// Update the previous character type
			previous = type;
		}
		// Store any remaining phrase information
		kanjiOrder.add(phrase.toString());
		kanjiOffset.add(offset);
		
		// Iterate through kana and use that to build out substrings
		List<String> phrases = new ArrayList<String>();
		for (int ndx = 0; ndx < kanjiOrder.size(); ndx++) {
			// Note the items in this pairing
			String kana = kanjiOrder.get(ndx);
			offset = kanjiOffset.get(ndx);
			// Discard this string if it is only a single character
			if (kana.length() <= 1) {
				continue;
			}
			// Get the hiragana for this substring
			char ch = kana.charAt(kana.length() - 1);
			String hiragana = card.getHiragana();
			// TODO Make sure we can find the character, this may happen in some
			// TODO cases where a kanji is confused for a hiragana
			if (hiragana.indexOf(ch, offset) == -1) {
				throw new ConfuserException("Error finding boundry for " + kanji);
			}
			hiragana = hiragana.substring(kanjiOffset.get(ndx), hiragana.indexOf(ch, offset) + 1);
			// Press on if we don't have at least two characters to work with
			if (hiragana.length() <= 1) {
				continue;
			}
			hiragana = hiragana.substring(1);
			// Replace the hiragana in the current kana with what we have
			String replacement = kana.replace(String.valueOf(ch), hiragana);
			if (replacement.equals(kana)) {
				continue;
			}
			// Generate the updated kanji and append them to the results
			phrase = new StringBuilder();
			for (String value : kanjiOrder) {
				if (value.equals(kana)) {
					phrase.append(kana.replace(String.valueOf(ch), hiragana));
				} else {
					phrase.append(value);
				}
			}
			phrases.add(phrase.toString());
		}

		// Return the results
		return phrases;
	}

	/**
	 * Get a list of kanji phrases that are similar to the one that has been
	 * provided. In the event that no kanji confusers for the given card then
	 * null will be returned, otherwise, confusers up to the provided count will
	 * be provided.
	 * 
	 * @param card
	 * @return
	 */
	public List<String> getKanjiSubsitution(String phrase) throws ConfuserException, IOException {
		// Read the confusers from the resource file
		List<String> confusers = readConfusers();
		// Iterate through the characters in this phrase
		List<String> phrases = new ArrayList<String>();
		for (int ndx = 0; ndx < phrase.length(); ndx++) {
			char ch = phrase.charAt(ndx);
			if (ConfuserTools.checkCharacter(ch) != CharacterType.Kanji) {
				continue;
			}
			// Iterate through each of the lines for confusers
			for (String confuser : confusers) {
				if (confuser.contains(String.valueOf(ch))) {
					phrases.addAll(getReplacements(phrase, ch, ndx, confuser));
					break;
				}
			}
		}
		// Return the confusers
		return phrases;		
	}
		
	/**
	 * Manipulate the phrase provided to add or remove n characters (ん, ン) as
	 * appropriate.
	 * 
	 * @param phrase The phrase to be manipulated.
	 * @return A list of manipulations or an empty list if there is no valid
	 * work to be done.
	 */
	public List<String> getNManipulation(String phrase) {
		// Determine if the phrase is in hiragana or katakana and adjust
		// our assumptions accordingly
		char n = 'ん';
		String characters = "なにぬねの";
		if (ConfuserTools.checkCharacter(phrase.charAt(0)) == CharacterType.Katakana) {
			n = 'ン';
			characters = "ナニヌネノ";
		}
		// Now start scanning through the phrase for relevant matches for this 
		// we are only focusing on the characters that are in the middle of 
		// the phrase
		List<String> phrases = new ArrayList<String>();
		for (int ndx = 1; ndx < phrase.length() - 1; ndx++) {
			char ch = phrase.charAt(ndx);
			// If this is an n character, remove it if the next is an n* sound
			if (ch == n) {
				char next = phrase.charAt(ndx + 1);
				if (characters.contains(String.valueOf(next))) {
					phrases.add(phrase.substring(0, ndx) + phrase.substring(ndx + 1));
					// Make sure we skip the next character to avoid doubling n's
					ndx++;
				}
			}
			// If this is an n* sound, add a n before it
			else if (characters.contains(String.valueOf(ch))) {
				phrases.add(phrase.substring(0, ndx) + n + phrase.substring(ndx));
			}
		}
		return phrases;
	}
	
	/**
	 * Generate all of the valid replacements for the parameters provided.
	 * 
	 * @param phrase The kanji phrase have the substitutions applied to.
	 * @param kanji The kanji that is to be replaced.
	 * @param index The index that the kanji exists at.
	 * @param replacements The kanji family as read from the confusers list.
	 * @return The list of updated kanji phrases.
	 */
	private List<String> getReplacements(String phrase, char kanji, int index, String replacements) {
		// Make sure the kanji provided does not appear in the list
		replacements = replacements.replace(String.valueOf(kanji), "");
		// Iterate through the replacements and generate new strings with 
		// the character at the index being replaced
		List<String> phrases = new ArrayList<String>();
		for (int ndx = 0; ndx < replacements.length(); ndx++) { 
			char replacement = replacements.charAt(ndx);
			// Make sure the phrase is built correctly
			if (index == 0) {
				phrases.add(String.valueOf(replacement) + phrase.substring(1));
				continue;
			}
			int offset = (index == 1) ? 1 : index - 1;
			if (index == (phrase.length() - 1)) {
					phrases.add(phrase.substring(0, offset) + String.valueOf(replacement));
			} else {
				String bah = phrase.substring(0, offset) + replacement + phrase.substring(index + 1);

				phrases.add(bah);
			}
		}
		return phrases;
	}
	
	/**
	 * Add or remove the small tsu (っ, ッ) from the phrase as warranted.
	 * 
	 * @param phrase The hiragana to be manipulated.
	 * @return A list of manipulations or an empty list if there is no valid
	 * work to be done.
	 */
	public List<String> getSmallTsuManiuplation(String phrase) {
		// The following are the parameters for xtsu (っ, ッ) manipulation
		char xtsu = 'っ';
		String characters = "かきくけこさしたちつてとはひふへほぱぴぷぺぽ";
		if (ConfuserTools.checkCharacter(phrase.charAt(0)) == CharacterType.Katakana) {
			xtsu = 'ッ';
			characters = "カキクケコサシタチツテトハヒフヘホパピプペポ";
		}
		// Now start scanning through the phrase for relevant matches for this 
		// we are only focusing on the characters that are in the middle of 
		// the phrase
		List<String> phrases = new ArrayList<String>();
		for (int ndx = 0; ndx < phrase.length(); ndx++) {
			char ch = phrase.charAt(ndx);
			// If this is a small tsu character, remove it
			if (ch == xtsu) {
				phrases.add(phrase.substring(0, ndx) + phrase.substring(ndx + 1));
			}
			// If this is a matching sound, add a small tsu
			else if (characters.contains(String.valueOf(ch))) {
				// Are we at the end of the phrase?
				if ((ndx + 1) == phrase.length()) {
					continue;
				}
				// Make sure the next character is not the small tsu
				if (phrase.charAt(ndx + 1) == xtsu) {
					continue;
				}
				// Make sure the phrase is built correctly
				if (ndx == 0) {
					phrases.add(String.valueOf(phrase.charAt(0)) + xtsu + phrase.substring(1));
				} else {
					phrases.add(phrase.substring(0, ndx) + xtsu + phrase.substring(ndx));
				}
			}
		}
		return phrases;
	}
	
	/**
	 * Add or remove vowel elongation characters (ー) from the provided phrase. 
	 * This algorithm attempts to either add or remove a single character on a
	 * single pass through the entire phrase.
	 * 
	 * @param phrase The phrase to be manipulated.
	 * @return A list of valid manipulations of the provided phrase.
	 */
	public List<String> getVowelManiuplation(String phrase) {
		// The following are the parameters for the manipulation
		char choonpu = 'ー';
		char n = 'ン';
		String invalidFollowers = "ャュョェ";
		// Start scanning through the phrase for relevant matches and either
		// add or remove the choopu as required
		List<String> phrases = new ArrayList<String>();
		for (int ndx = 0; ndx < phrase.length(); ndx++) {
			char ch = phrase.charAt(ndx);
			// Press on if we can't replace this character
			if (ch == n) {
				continue;
			}
			// Check to make sure the next character is not an extension
			if (ndx != (phrase.length() - 1)) {
				char next = phrase.charAt(ndx + 1);
				if (next == choonpu || invalidFollowers.contains(String.valueOf(next))) {
					continue;
				}
			}
			// Are we doing a delete?
			if (ch == choonpu) {
				phrases.add(phrase.substring(0, ndx) + phrase.substring(ndx + 1));
				continue;
			}
			// We must be performing an insert instead
			if (ndx == 0) {
				phrases.add(String.valueOf(ch) + choonpu + phrase.substring(1));
			} else if (ndx == (phrase.length() - 1)) {
				phrases.add(phrase + choonpu);
			} else {
				String foo = phrase.substring(0, ndx + 1) + choonpu + phrase.substring(ndx + 1);
				phrases.add(foo);
			}
		}
		return phrases;
	}
	
	/**
	 * 
	 * @return
	 */
	private List<String> readConfusers() throws ConfuserException, IOException {
		// Open the black list for this language and check to see if it exists
		String path = CONFUSER_DIRECTORY + CONFUSER_LANGUAGE + CONFUSER_EXTENSION;
		InputStream confusers = ConfuserTools.class.getResourceAsStream(path);
		if (confusers == null) {
			throw new ConfuserException("Unable to open the confusers for, " + CONFUSER_LANGUAGE);
		}
		// Prepare the stream reader
		BufferedReader reader = null;
		InputStreamReader stream = null;
		try {
			// Open up the stream to be read
			stream = new InputStreamReader(confusers);
			reader = new BufferedReader(stream);
			// Read and process the contents
			List<String> results = new ArrayList<String>();
			String data;
			while ((data = reader.readLine()) != null) {
				if (data.isEmpty() || data.charAt(0) == '#') {
					continue;
				}
				results.add(data.replace(" ", ""));
			}
			return results;
		} catch (FileNotFoundException ex) {
			throw new ConfuserException("Unable to open the confusers for, " + CONFUSER_LANGUAGE);
		} catch (IOException ex) {
			throw new ConfuserException("An error occured while reading the next line", ex);
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}
}
