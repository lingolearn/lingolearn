package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableCard;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

public class CardDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 * *****
	 */
	private static class CardDAOHolder { 
		public static final CardDAO INSTANCE = new CardDAO();
	}

	public static CardDAO getInstance() {
		return CardDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the Card in the datastore
	 * @param card Card to be stored in the datastore
	 * @return stored Card for diagnostic purpose
	 * @throws CardNotFoundException if the card is duplicate
	 */
	public Card storeCard( Card card ) throws CardNotFoundException {
		Card duplicateCard = findDuplicateCard(card);
		if (duplicateCard == null) {
			ObjectifyableCard oCard = new ObjectifyableCard(card); 
			ofy().save().entity(oCard).now();
			ObjectifyableCard fetched = ofy().load().entity(oCard).now();
			card = fetched.getCard();
		} else {
			card = duplicateCard;
		}
		return card;
	}
	
	/**
	 * Helper method to verify that the card is not already is the datastore.
	 * 
	 * Card is considered to be the same with the Card already stored if thier respective
	 * kanji, hiragana, katakana, translation, description and native language are the same.
	 *    
	 * @param card Card
	 * @return null if this card is not in the datastore or the Card is the card is already stored 
	 */
	private Card findDuplicateCard(Card card) { 
		// First obtain a list of all Cards with the same kanji
		List<Card> cardList;
		try {
			cardList = getAllCardsByKanji(card.getKanji());
		} catch (CardNotFoundException e) {
			// There is no any Card stored yet with this kanji, no further checks are needed, proceed with storing
			return null;
		}
		Iterator<Card> it = cardList.iterator();
		while (it.hasNext()) {
			Card currCard = it.next();		
			if (currCard.equals(card)) {
				return currCard;
			}
		}
		// Will be considered Unique card, proceed with storing
		return null;	
	}
	
	/**
	 * Obtains Card by CardId
	 * @param cardId cardId
	 * @return Card stored with the cardId
	 */
	public Card getCardById(Long cardId) {
		if (cardId == null) {
			return null;
		}
		ObjectifyableCard oCard = ofy().load().type(ObjectifyableCard.class).id(cardId).now();
		if (oCard != null) {
			Card card = oCard.getCard();
			return card;
			}
		return null;
	}
	
	/**
	 * Obtains first available card with specified kanji in the datastore
	 * @param kanji in as the search parameter
	 * @return Card with the requested kanji
	 * @throws CardNotFoundException 
	 */
	public Card getCardByKanji(String kanji) throws CardNotFoundException {
		ObjectifyableCard oCard = ofy().load().type(ObjectifyableCard.class).filter("kanji", kanji).first().now();
		if (oCard==null)
			throw new CardNotFoundException("Card was not found in the datastore", "kanji", kanji);
		else {
			Card card = oCard.getCard();
			return card;
		}
	}

	/**
	 * Obtains first available card with specified hiragana in the datastore
	 * @param hiragana in as the search parameter
	 * @return Card with the requested hiragana
	 * @throws CardNotFoundException 
	 */
	public Card getCardByHiragana(String hiragana) throws CardNotFoundException {
		ObjectifyableCard oCard = ofy().load().type(ObjectifyableCard.class).filter("hiragana", hiragana).first().now();
		if (oCard==null)
			throw new CardNotFoundException("Card was not found in the datastore", "hiragana", hiragana);
		else {
			Card card = oCard.getCard();
			return card;
		}
	}
	
	/**
	 * Obtains first available card with specified katakana in the datastore
	 * @param katakana in as the search parameter
	 * @return Card with the requested katakana
	 * @throws CardNotFoundException 
	 */
	public Card getCardByKatakana(String katakana) throws CardNotFoundException {
		ObjectifyableCard oCard = ofy().load().type(ObjectifyableCard.class).filter("katakana", katakana).first().now();
		if (oCard==null)
			throw new CardNotFoundException("Card was not found in the datastore", "katakana", katakana);
		else {
			Card card = oCard.getCard();
			return card;
		}
	}
	
	/**
	 * Obtains first available card with specified translation in the datastore
	 * @param translation in as the search parameter
	 * @return Card with the requested translation
	 * @throws CardNotFoundException 
	 */
	public Card getCardByTranslation(String translation) throws CardNotFoundException {
		ObjectifyableCard oCard = ofy().load().type(ObjectifyableCard.class).filter("translation", translation).first().now();
		if (oCard==null)
			throw new CardNotFoundException("Card was not found in the datastore", "translation", translation);
		else {
			Card card = oCard.getCard();
			return card;
		}
	}
	
	/**
	 * Obtains first available card with specified description in the datastore
	 * @param description in as the search parameter
	 * @return Card with the requested description
	 * @throws CardNotFoundException 
	 */
	public Card getCardByDescription(String desc) throws CardNotFoundException {
		ObjectifyableCard oCard = ofy().load().type(ObjectifyableCard.class).filter("description", desc).first().now();
		if (oCard==null)
			throw new CardNotFoundException("Card was not found in the datastore", "description", desc);
		else {
			Card card = oCard.getCard();
			return card;}
	}

	/**
	 * Obtains list of all available cards with specified kanji in the datastore
	 * @param kanji
	 * @return List of all Cards in the datastore containing the same kanji
	 * @throws CardNotFoundException 
	 */
	public List<Card> getAllCardsByKanji(String kanji) throws CardNotFoundException {
		List<ObjectifyableCard> oCards = ofy().load().type(ObjectifyableCard.class).filter("kanji", kanji).list();
		Iterator<ObjectifyableCard> it = oCards.iterator();
		List<Card> cards = new ArrayList<>();
		while (it.hasNext()) {
			cards.add(it.next().getCard());
		}
		if (cards.size() == 0) {
			throw new CardNotFoundException("Cards were not found in the datastore", "kanji", kanji);
		} else {
			return cards;
		}
	}
	
	/**
	 * Obtains list of all available cards in specified native language from the datastore
	 * @param lang native language of the card
	 * @return List of all Cards in the datastore in the same native language
	 * @throws CardNotFoundException
	 */
	public List<Card> getAllCardsByLanguage(String lang) throws CardNotFoundException {
		List<ObjectifyableCard> oCards = ofy().load().type(ObjectifyableCard.class).filter("nativeLanguage", lang).list();
		Iterator<ObjectifyableCard> it = oCards.iterator();
		List<Card> cards = new ArrayList<>();
		while (it.hasNext()) {
			cards.add(it.next().getCard());
		}
		if (cards.size() == 0) {
			throw new CardNotFoundException("Cards were not found in the datastore", "native language", lang);
		} else {
			return cards;
		}
	}
	
	/**
	 * Obtains a list of all available Cards in the datastore
	 * @return list of all Cards or Null if no Cards in the datastore
	 * @throws CardNotFoundException 
	 */
	public List<Card> getAllCards() throws CardNotFoundException {
		List<ObjectifyableCard> oUsers = ofy().load().type(ObjectifyableCard.class).list();
		Iterator<ObjectifyableCard> it = oUsers.iterator();
		List<Card> cards = new ArrayList<>();
		while (it.hasNext()) {
			cards.add(it.next().getCard());
		}
		if (cards.size() == 0) {
			throw new CardNotFoundException("No Cards were not found in the datastore");
		} else {
			return cards;
		}
	}
	
	/**
	 * Deletes the card with the specified CardId from the datastore
	 * @param cardId
	 */
	public void deleteCardById(Long cardId) {
		if (cardId != null) {
			ofy().delete().type(ObjectifyableCard.class).id(cardId).now();
		}
	}
}
