package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Serialize;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Sound;

/**
 * This class represents Proxy for Deck
 */
@Entity(name="ObjectifyableDeck")
public class ObjectifyableDeck implements Serializable {

	private static final long serialVersionUID = -5054129063681784845L;
	
	@Id private Long id;						  // The unique id of the deck
	//@Serialize private HashMap<Long, Card> cards; // The map of cards that are part of this deck
	private List<Long> cardIds;					  // The ids of the cards that are part of this deck
	private String language; 					  // The language of this deck
	private String nativeLangauge; 				  // The native language of the translations
	
	
	public ObjectifyableDeck() {}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param deck		Deck object
	 */
	public ObjectifyableDeck(Deck deck) {
		// no need to store HashMap of Cards, can always be recreated by knowing cardIds
		this.cardIds = deck.getCardIds();
		this.language = deck.getLangauge();
		this.nativeLangauge = deck.getLangauge();
	}
	
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return Deck object
	 */
	public Deck getDeck() {
		CardDAO cardAccessor = CardDAO.getInstance();
		Deck deck = new Deck();
		deck.setCardIds(this.cardIds);
		deck.setLangauge(this.language);
		deck.setNativeLangauge(this.nativeLangauge);
		// re-create hash map of Cards
		Map<Long, Card> cards = new HashMap<Long, Card>();
		for (Long cardId : cardIds) {
			if (cardAccessor.getCardById(cardId) != null) {
				Card c = new Card();
				c = cardAccessor.getCardById(cardId);
				cards.put(cardId, c);
			}
		}
		return deck;
	}
	
}

