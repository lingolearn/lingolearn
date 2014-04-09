package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.DeckNotFoundException;

public class DeckDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class DeckDAOHolder { 
		public static final DeckDAO INSTANCE = new DeckDAO();
	}

	public static DeckDAO getInstance() {
		return DeckDAOHolder.INSTANCE;
	}   
	
	/**
	 * Stores the deck into the datastore.
	 * Is verification for duplication needed here?
	 * @param deck to be stored
	 * @return stored Deck for diagnostic 
	 */
	public Deck storeDeck( Deck deck ) {
		ObjectifyableDeck oDeck = new ObjectifyableDeck(deck);
		ofy().save().entity(oDeck).now();
		ObjectifyableDeck fetched = ofy().load().entity(oDeck).now();
		deck = fetched.getDeck();
		return deck;	
	}
	
	/**
	 * Retrieve the deck from the datastore
	 * @param id deckId
	 * @return Deck with the specified deckId
	 * @throws DeckNotFoundException if the requested deck can not be found
	 */
	public Deck getDeckById(Long id) throws DeckNotFoundException {
		ObjectifyableDeck oDeck = ofy().load().type(ObjectifyableDeck.class).id(id).now();
		if (oDeck==null)
			throw new DeckNotFoundException("Deck was not found in the datastore", "id", id.toString());
		else {
			Deck deck = oDeck.getDeck();
			return deck;
		}
	}
	
	/**
	 * Deletes the deck with the specified deckId from the datastore
	 * @param cardId
	 */
	public void deleteDeckById(Long deckId) {
		ofy().delete().type(ObjectifyableDeck.class).id(deckId).now();
	}

	
	public List<Deck> getAllDecks() {
		List<ObjectifyableDeck> oDecks = ofy().load().type(ObjectifyableDeck.class).list();
		Iterator<ObjectifyableDeck> it = oDecks.iterator();
		List<Deck> decks = new ArrayList<>();
		while (it.hasNext()) {
			decks.add(it.next().getDeck());
		}
		if (decks.size() == 0) {
			return null;
		} else {
			return decks;
		}
	}

}
