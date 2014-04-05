package cscie99.team2.lingolearn.server.confuser;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import cscie99.team2.lingolearn.shared.Card;

/**
 * This class encapsulates a means of getting characters that are similar to 
 * a given example for the purposes of confusing humans.
 */
public class Confuser {
  /**
   * Get a list of kanji phrases that are similar to the one that has been
   * provided. In the event that no kanji confusers for the given card then
   * null will be returned, otherwise, confusers up to the provided count 
   * will be provided.
   */
  public List<String> getKanjiSubsitution(Card card, int count) {
    // Get the confusers for Japanese
    
    // Break the card's kanji phrase apart so we have multiple options
    
    // Iterate through the families until we find the correct one
    
      // We have a match, note the family
      
    // If we don't have any families identified, return null
    
    // Try to find a confuser based upon each family provided
    
    // Return the confusers
    return null;
  }
  
  /**
   * Get a list of kanji phrases that have the hiragana extended off the
   * kanji where appropriate.
   * 
   * @param card The card to build the phrases off of.
   * @return A list of phrases built from the card, or an empty list if 
   * there are no valid confusers.
   */
  public List<String> getKanjiBoundries(Card card) {
	  // Check to see if the phrase ends with する (to do) since this approach is invalid
	  if (card.getKanji().endsWith("する")) {
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
		  // Get the hiragana for this substring
		  char ch = kana.charAt(kana.length() - 1);
		  String hiragana = card.getHiragana();
		  hiragana = hiragana.substring(kanjiOffset.get(ndx), hiragana.indexOf(ch, offset) + 1);		  
		  // Discard the first character since it is represented by the kanji
		  if (hiragana.length() == 1) {
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
   * Get a list of hiragana phrase confusers that are similar to the one
   * that has been provided.
   */
  public List<String> getHiraganaPhrases(String phrase) {
    return null;
  }
  
  /**
   * Get a list of katakana phrase confusers that are similar to the one
   * that has been provided.
   */
  public List<String> getKatakanaPhrases(String phrase) {
    return null;
  }
}
