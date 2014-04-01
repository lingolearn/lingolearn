/**
 * 
 */
package cscie99.team2.lingolearn.server;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

/**
 * @author YPolyanskyy
 * 
 * Unit tests for CardDAO class.
 * 
 * Make sure the following jars are in the classpath:
 * 
 * ${SDK_ROOT}/lib/testing/appengine-testing.jar
 * ${SDK_ROOT}/lib/impl/appengine-api.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-labs.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-stubs.jar
 * 
 */
public class CardDAOTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
		      new LocalDatastoreServiceTestConfig());
		 
	@BeforeClass
	  public static void setUpBeforeClass() throws Exception {
	  }
	 
	  @AfterClass
	  public static void tearDownAfterClass() throws Exception {
	  }
	 
	  @Before
	  public void setUp() throws Exception {
	    helper.setUp();
	    
	    // create and load 3 testing cards
	    // 
		Card tc1 = new Card();
		tc1.setId((long) 101); 
		tc1.setKanji("k1");
		tc1.setHiragana("h1");
		tc1.setKatakana("kt1");
		tc1.setTranslation("card1");
		tc1.setNativeLanguage("us-en");
		tc1.setDesc("TestDeck");
	    
		// same kanji as in tc1
		Card tc2 = new Card();
		tc2.setId((long) 102); 
		tc2.setKanji("k1");
		tc2.setHiragana("h2");
		tc2.setKatakana("kt2");
		tc2.setTranslation("carte2");
		tc2.setNativeLanguage("ca-fr");
		tc2.setDesc("TestDeck");
		
		// with Sound and Image
		String imgUri = "data:image/png;base64,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";
		Image myImage = new Image();
		myImage.setImageUri(imgUri);
		
		String soundUri = "data:audio/mpeg;base64,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";
		Sound mySound = new Sound();
		mySound.setSoundUri(soundUri);
		
		Card tc3 = new Card();
		tc3.setId((long) 103); 
		tc3.setKanji("k3");
		tc3.setHiragana("h3");
		tc3.setKatakana("kt3");
		tc3.setTranslation("card3");
		tc3.setNativeLanguage("us-en");
		tc3.setDesc("MultiMediaDeck");
		tc3.setImage(myImage);
		tc3.setSound(mySound);
		
		// Store them in the local In-memory datastore
		CardDAO cardAccessor = CardDAO.getInstance();
		try {
			cardAccessor.storeCard(tc1);
			cardAccessor.storeCard(tc2);
			cardAccessor.storeCard(tc3);
		} catch (CardNotFoundException myCardNotFoundException) {
			// Duplicate card
			System.err.println(myCardNotFoundException.getMsg() + " for " + myCardNotFoundException.getSearchParam());
		}
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    helper.tearDown();
	  }
	  
	  @Test
	  /**
	   * Test storeCard method for correct duplication detection 
	   */
	  public void testDupRefusal() {
		  try {
			  // Create the card that already exists in the datastore
			  // because of setUp()
			  CardDAO cardAccessor = CardDAO.getInstance();
			  Card tc1 = new Card();
			  tc1.setId((long) 101); 
			  tc1.setKanji("k1");
			  tc1.setHiragana("h1");
			  tc1.setKatakana("kt1");
			  tc1.setTranslation("card1");
			  tc1.setNativeLanguage("us-en");
			  tc1.setDesc("TestDeck");
			  
			  // Attempt to store it should result in CardNotFoundException
			  cardAccessor.storeCard(tc1);

		  } catch (CardNotFoundException myCardNotFoundException) {
			  // Duplicate card
			  assertTrue(true);
		  }
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "kanji" 
	   */
	  public void testGetByKanji() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  String result = null;
		  
		  try {
			  c = cardAccessor.getCardByKanji("k1");
			  result = c.getTranslation();
		  } catch (CardNotFoundException e) {
			  System.err.println("Card was not found");
		  }
		  assertTrue("Retrieval card by Kanjii failed: expected card1, but obtained: " + result, result.equals("card1"));
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "hiragana"
	   */
	  public void testGetByHiragana() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  String result = null;
		  try {
			  c = cardAccessor.getCardByHiragana("h3");
			  result = c.getHiragana();
		  } catch (CardNotFoundException e) {
			  System.err.println("Card was not found");
		  }
		  assertTrue("Retrieval card by Hiragana failed: expected h3, but obtained: " + result, result.equals("h3"));
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "katakana" 
	   */
	  public void testGetByKatakana() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  String result = null;
		  try {
			  c = cardAccessor.getCardByKatakana("kt3");
			  result = c.getKatakana();
		  } catch (CardNotFoundException e) {
			  System.err.println("Card was not found");
		  }
		  assertTrue("Retrieval card by Katakana failed: expected kt3, but obtained: " + result, result.equals("kt3"));
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "translation" 
	   */
	  public void testGetByTranslation() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  String result = null;
		  try {
			  c = cardAccessor.getCardByTranslation("carte2");
			  result = c.getTranslation();
		  } catch (CardNotFoundException e) {
			  System.err.println("Card was not found");
		  }
		  assertTrue("Retrieval card by Translation failed: expected carte2, but obtained: " + result, result.equals("carte2"));
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "description" 
	   */
	  public void testGetByDescription() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  String result = null;
		  try {
			  c = cardAccessor.getCardByDescription("MultiMediaDeck");
			  result = c.getDesc();
		  } catch (CardNotFoundException e) {
			  System.err.println("Card was not found");
		  }
		  assertTrue("Retrieval card by Description failed: expected MultiMediaDeck, but obtained: " + result, result.equals("MultiMediaDeck"));
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "cardId" 
	   */
	  public void testGetByCardId() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  long result = 0;
		  // Retrieve existing card tc1 with cardId=101
		  c = cardAccessor.getCardById((long)101);
		  if (c != null) {
			  result = c.getId();}
		  assertTrue("Retrieval card by CardId failed: expected 101, but obtained: " + result, (long)101 == result);
	  }
	  
	  @Test
	  /**
	   * Test card retrieval by "cardId" for non-existing cardId
	   */
	  public void testFailedGetByCardId() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  // Retrieve non-existing card with cardId=111
		  c = cardAccessor.getCardById((long)111);
		  if (c != null) {
			  assertTrue(false);
		  }
	  }
	  
	  @Test
	  /**
	   * Test card's List retrieval by "kanji"
	   */
	  public void testGetAllCardsByKanji () {
		  CardDAO cardAccessor = CardDAO.getInstance();
			// Obtain a list of all Cards with the same kanji
			List<Card> cardList = null;
			int size = 2;
			try {
				cardList = cardAccessor.getAllCardsByKanji("k1");
			} catch (CardNotFoundException e) {
				// There is no any Card stored yet with this kanji
				System.err.println("Cards were not found");
			}
			// The list should contain 2 cards
			assertTrue("Retrieval of the cards list with the same kanji failed: expected 2 Cards, but obtained: " + cardList.size(), cardList.size() == size);
	  }
	  
	  @Test
	  /**
	   * Test card's List retrieval by "native-language"
	   */
	  public void testGetAllCardsByLanguage () {
		  CardDAO cardAccessor = CardDAO.getInstance();
			// Obtain a list of all Cards with the same kanji
			List<Card> cardList = null;
			int size = 1;
			try {
				cardList = cardAccessor.getAllCardsByLanguage("ca-fr");
			} catch (CardNotFoundException e) {
				// There is no any Card stored yet with this native language
				System.err.println("Cards were not found");
			}
			// The list should contain only 1 card
			assertTrue("Retrieval of the cards list with the same native language failed: expected 1 Card, but obtained: " + cardList.size(), cardList.size() == size);
	  }
	    
	  @Test
	  /**
	   * Test card delete by "cardId"
	   */
	  public void testDeleteCardByCardId() {
		  CardDAO cardAccessor = CardDAO.getInstance();
		  Card c = null;
		  // Delete card with cardId 101
		  cardAccessor.deleteCardById((long)101);
		  // Retrieve non-existing card with cardId=101
		  c = cardAccessor.getCardById((long)101);
		  if (c != null) {
			  assertTrue(false);
		  }
	  }
	  
}
