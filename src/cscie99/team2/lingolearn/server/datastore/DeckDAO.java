package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableCard;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;
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
	
	public Deck storeDeck( Deck deck ) {
		ofy().save().entity(new ObjectifyableDeck(deck)).now();
		return deck;	
	}
	
	public Deck getDeckById(Long id) throws DeckNotFoundException {
		ObjectifyableDeck oDeck = ofy().load().type(ObjectifyableDeck.class).filter("id", id).first().now();
		if (oDeck==null)
			throw new DeckNotFoundException("Deck was not found in the datastore", "id", id.toString());
		else {
			Deck deck = oDeck.getDeck();
			return deck;
		}
	}

}
