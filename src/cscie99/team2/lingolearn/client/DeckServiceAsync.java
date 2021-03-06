package cscie99.team2.lingolearn.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.Deck;

public interface DeckServiceAsync {
	public void deleteDeckById(Long deckId,AsyncCallback<Void> callback);
	public void getDeckById(Long id, AsyncCallback<Deck> callback);
	public void storeDeck(Deck deck, AsyncCallback<Deck> callback);
	public void getAllDecks(AsyncCallback<List<Deck>> callback);
}
