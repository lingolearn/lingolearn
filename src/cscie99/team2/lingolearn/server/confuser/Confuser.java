package cscie99.team2.lingolearn.server.confuser;

import java.util.List;

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
    
    // Break the card's kanji phrase appart so we have mulitple options
    
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
   */
  public List<String> getKanjiBoundries(Card card) {
    return null;
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
