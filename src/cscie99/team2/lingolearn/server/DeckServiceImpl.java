package cscie99.team2.lingolearn.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.DeckService;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.DeckNotFoundException;

/**
 *
 */
@SuppressWarnings("serial")
public class DeckServiceImpl extends RemoteServiceServlet implements DeckService {

	private DeckDAO deckAccessor = DeckDAO.getInstance();

	public void deleteDeckById(Long deckId) {
		deckAccessor.deleteDeckById(deckId);
	}

	public Deck getDeckById(Long id) {
		Deck d = null;
		try {
			d = deckAccessor.getDeckById(id);
		} catch (DeckNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}


	public Deck storeDeck(Deck deck) {
		Deck d = null;
		d = deckAccessor.storeDeck(deck);
		return d;
	}

	@Override
	public List<Deck> getAllDecks() {
		return deckAccessor.getAllDecks();
	}
}
