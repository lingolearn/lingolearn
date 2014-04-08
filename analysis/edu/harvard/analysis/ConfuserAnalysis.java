package edu.harvard.analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cscie99.team2.lingolearn.server.confuser.CharacterType;
import cscie99.team2.lingolearn.server.confuser.Confuser;
import cscie99.team2.lingolearn.server.confuser.ConfuserTools;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.ConfuserException;

/**
 * This file is used to perform some basis analysis on the confuser algorithm
 * to determine it's effectiveness.
 */
public class ConfuserAnalysis {
	// The path to the directory of confusers
	private final static String JLPT_DIRECTORY = "/jlpt/";
	
	// The extension for the files
	private final static String JLPT_EXTENSION = ".jlpt";
	
	// The JLPT level we are working with
	private final static String JLPT_LEVEL = "5";

	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String [] args) throws IOException {
		try {
			List<Card> cards = loadCards(JLPT_DIRECTORY + JLPT_LEVEL + JLPT_EXTENSION);
			analyzeConfusers(cards);
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open the JLPT level " + JLPT_LEVEL);
		} catch (IOException ex) {
			System.out.println("An IO exception occured.");
			System.out.println(ex.toString());
		} catch (ConfuserException ex) {
			System.out.println("An error occured whlie processing the cards.");
			System.out.println(ex.toString());
		}
	}
	
	/**
	 * 
	 * @param cards
	 * @throws IOException 
	 * @throws ConfuserException 
	 */
	private static void analyzeConfusers(List<Card> cards) throws ConfuserException, IOException {
		// Prepare the counters
		int kanji = 0, hiragana = 0, katakana = 0;
		List<Integer> kanjiConfusers = new ArrayList<Integer>();
		List<Integer> hiraganaConfusers = new ArrayList<Integer>();
		List<Integer> katakanaConfusers = new ArrayList<Integer>();
		// Iterate through the cards to find the results
		Confuser confuser = new Confuser();
		List<String> results;
		for (Card card : cards) {
			if (!card.getKanji().isEmpty()) {
				try {
					results = confuser.getKanjiBoundries(card);
					results.addAll(confuser.getKanjiSubsitution(card.getKanji()));
					kanji++;
					kanjiConfusers.add(results.size());
				} catch (ConfuserException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
	}
	
	/**
	 * 
	 * @param resourceName
	 * @return
	 */
	private static List<Card> loadCards(String resourceName) throws IOException {
		// Open the resource file
		InputStream jlpt = ConfuserAnalysis.class.getResourceAsStream(resourceName);
		// Prepare the stream reader
		BufferedReader reader = null;
		InputStreamReader stream = null;
		List<Card> cards = new ArrayList<Card>();
		try {
			// Open up the stream to be read
			stream = new InputStreamReader(jlpt);
			reader = new BufferedReader(stream);
			// Read and process the contents
			String data;
			while ((data = reader.readLine()) != null) {
				// Parse the data and prepare the card
				String[] values = data.split(",");
				Card card = new Card();
				card.setKanji(values[0]);
				if (ConfuserTools.checkCharacter(values[1].charAt(0)) == CharacterType.Hiragana) {
					card.setHiragana(values[1]);
				} else {
					card.setKatakana(values[1]);
				}
				card.setTranslation(values[2]);
				cards.add(card);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
		// Return the results
		return cards;
	}
}
