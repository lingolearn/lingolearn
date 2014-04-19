/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.Deck;

@RemoteServiceRelativePath("deckService")
public interface DeckService extends RemoteService {
	public void deleteDeckById(Long deckId);
	public Deck getDeckById(Long id);
	public Deck storeDeck(Deck deck);
	public List<Deck> getAllDecks();
}
