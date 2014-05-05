package cscie99.team2.lingolearn.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.SessionTypes;

public interface CardServiceAsync {
	public void deleteCardById(Long cardId, AsyncCallback<Void> callback);
    public void getCardById(Long cardId, AsyncCallback<Card> callback);
    public void getCardsByIds(ArrayList<Long> cardIds, AsyncCallback<ArrayList<Card>> callback);
	public void getCardByKanji(String kanji, AsyncCallback<Card> callback);
	public void getCardByHiragana(String hiragana, AsyncCallback<Card> callback);
	public void getCardByKatakana(String katakana, AsyncCallback<Card> callback);
	public void getCardByTranslation(String translation, AsyncCallback<Card> callback);
	public void getCardByDescription(String desc, AsyncCallback<Card> callback);
	public void getAllCardsByKanji(String kanji, AsyncCallback<List<Card>> callback);
	public void getAllCardsByLanguage(String lang, AsyncCallback<List<Card>> callback );
	public void getAllCards(AsyncCallback<List<Card>> callback);
	public void storeCard(Card card, AsyncCallback<Card> callback);
	public void getConfusersForCard(Card card, AsyncCallback<List<String>> callback);
	public void getConfusersForCard(Card card, SessionTypes type,
																				AsyncCallback<List<String>> callback);
}
