package cscie99.team2.lingolearn.server.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;
import cscie99.team2.lingolearn.shared.error.FileLoadException;

/**
 * This class allows loading of a delimited file with the Card information into
 * the data store. By default this file expects to work with tab delimited data;
 * however, it can support other types of data. It is responsible for parsing 
 * of the source file, calling storeCard method of CardDAO class to load the 
 * Card for each line contained in the source file.
 * 
 * Format of the source file is:
 * kanji\thiragana\tkatakana\ttranslation\tnativelang\tdescription<LINEFEED>
 */
public class CardFileLoader {
	// Tab field delimiter
	private final static String TAB_DELIMITER = "\\t";
	// Expected number of fields on the line
	private final static int NUM_FIELDS = 6;			

	// The delimiter currently in use, default to tabs
	private String delimiter = TAB_DELIMITER;
	
	// Obtain an instance of the UnionArea to work with
	CardDAO cardLoader = CardDAO.getInstance();

	/**
	 * Constructor.
	 */
	public CardFileLoader() { }
	
	/**
	 * Constructor.
	 * 
	 * @param delimiter The file delimiter to use for the cards.
	 */
	public CardFileLoader(String delimiter) {
		this.delimiter = delimiter;
	}
	
	/**
	 * Get the delimiter that is currently being used.
	 */
	public String getDelimiter() {
		return this.delimiter;
	}
	
	/**
	 * Read and parse the source file and store each of the cards in the data store.
	 * 
	 * @param fileNameFile name of the input file.
	 * @throws FileLoadException Thrown if there is an error loading the cards, see 
	 * error message provided.
	 */
	public void loadFile(String fileName) throws FileLoadException, IOException {
		// The line in the file we are currently reading
		int lineNumber = 1;
		// The current line being read
		String data;					
		
		BufferedReader reader = null;
		try {
			// Open the file and read the first line
			FileReader input = new FileReader(fileName);
			reader = new BufferedReader(input);
			while ((data = reader.readLine().trim()) != null){
				// Parse the tokens from the line that was read
				String[] tokens = data.trim().split(delimiter);
				if (tokens.length != NUM_FIELDS) {
					throw new FileLoadException("Error parsing the contents of the file.", lineNumber);
				}
				// Create a new card and store it
				Card card = new Card();
				card.setKanji(tokens[0].trim());
				card.setHiragana(tokens[1].trim());
				card.setKatakana(tokens[2].trim());
				// TODO Currently all translations are assumed to be in English
				card.setTranslation(tokens[3].trim());
				card.setEnTranslation(tokens[3].trim());
				card.setNativeLanguage(tokens[4].trim());
				card.setDesc(tokens[5].trim());
				card.setImage(new Image());
				card.setSound(new Sound());
				try {
					card = cardLoader.storeCard(card);
				} catch (CardNotFoundException ex) {
					// The card already exists, so we don't really need to do anything
				}
				// Update the line number
				lineNumber++;
			}	
		} catch (FileNotFoundException ex) {
			throw new FileLoadException("Unable to load the indicated file",  ex);
		} catch (IOException ex) {
			throw new FileLoadException("An error occured while reading the next line", ex);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}			
	}
	
	/**
	 * Set the delimiter that is to be used/.
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
