package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

/**
 * This class represents a deck of the flashcards.
 */
public class Deck implements Serializable {
	// The id to use for the serialization version
	private static final long serialVersionUID = 1L;

	// The unique id of the deck
	private Long id;
	
	// The map of cards that are part of this deck
	private HashMap<Long, Card> cards;

	// The ids of the cards that are part of this deck
	private List<Long> cardIds;
		
	// The language of this deck
	private String language;
	
	// The native language of the translations
	private String nativeLangauge;
	
	/**
	 * Constructor.
	 */
	public Deck(Long id, List<Card> cards, String language, String nativeLanguage) {
		this.id = id;
		this.language = language;
		this.nativeLangauge = nativeLanguage;
		this.cards = new HashMap<Long, Card>();
		this.cardIds = new ArrayList<Long>();
		for (Card card : cards) {
			this.add(card);
		}
	}
	
	public Deck() {}
	
	/**
	 * Add the card indicated to the deck. If it already exists then this 
	 * operation will be ignored.
	 */
	public void add(Card card) {
		if (cardIds.contains(card.getId())) {
			return;
		}
		cardIds.add(card.getId());
		cards.put(card.getId(), card);		
	}
	
	/**
	 * Get the card with the id indicated.
	 */
	public Card getCard(Long id) throws CardNotFoundException {
		// Is this card part of the deck?
		if (!cardIds.contains(id)) {
			throw new CardNotFoundException("The card with the id " + id + " is not part of this deck.");
		}
		// Do we already have the card in the local cache?
		if (cards.containsKey(id)) {
			return cards.get(id);
		}
		// Get the card and make sure we keep a local copy

		// TODO Get the card from the data layer
		throw new CardNotFoundException("TODO Get the card");
	}
	
	/**
	 * Get the id of the cards that are part of this deck.
	 */
	public List<Long> getCardIds() {
		return cardIds;
	}
	
	/**
	 * Get the id of this deck.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Get the language of this deck.
	 */
	public String getLangauge() {
		return language;
	}
	
	/**
	 * Get the native language of this deck.
	 */
	public String getNativeLangauge() {
		return nativeLangauge;
	}
	
	/**
	 * Get the size of the deck.
	 */
	public int getSize() {
		return cardIds.size();
	}
	
	/**
	 * Remove the card from the deck. If it is not in the deck then this
	 * operation will be ignored.
	 */
	public void remove(Card card) {
		if (!cardIds.contains(card.getId())) {
			return;
		}
		// Remove the card id from the list of ids
		for (int ndx = 0; ndx < cardIds.size(); ndx++) {
			if (cardIds.get(ndx) == card.getId()) {
				cardIds.remove(ndx);
				break;
			}
		}
		cards.remove(card.getId());
	}
	
	/**
	 * Set the language of this deck.
	 */
	public void setLangauge(String language) {
		this.language = language;
	}
	
	/**
	 * Set the native language of this deck.
	 */
	public void setNativeLangauge(String nativeLanguage) {
		this.nativeLangauge = nativeLanguage;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCards(HashMap<Long, Card> cards) {
		this.cards = cards;
	}

	public void setCardIds(List<Long> cardIds) {
		this.cardIds = cardIds;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
