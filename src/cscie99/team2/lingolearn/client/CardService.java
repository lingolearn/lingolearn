package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.Card;

import java.util.List;

@RemoteServiceRelativePath("cardService")
public interface CardService extends RemoteService {
	public void deleteCardById(Long cardId);
	public Card getCardById(Long cardId);
	public Card getCardByKanji(String kanji);
	public Card getCardByHiragana(String hiragana);
	public Card getCardByKatakana(String katakana);
	public Card getCardByTranslation(String translation);
	public Card getCardByDescription(String desc);
	public List<Card> getAllCardsByKanji(String kanji);
	public List<Card> getAllCardsByLanguage(String lang);
	public List<Card> getAllCards();
	public Card storeCard(Card card);
	public List<String> getConfusersForCard(Card card);
}
