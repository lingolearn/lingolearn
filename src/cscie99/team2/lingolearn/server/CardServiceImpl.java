package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

@SuppressWarnings("serial")
public class CardServiceImpl extends RemoteServiceServlet implements CardService {
	
	private static final boolean Retrivining = false;
	CardDAO cardAccessor = CardDAO.getInstance();
	
	/**
	 * Soon to be replaced by real DAO implementation 
	 */
	public Card getCardById(Long cardId) {
		Card c = null;
		c = cardAccessor.getCardById(cardId);
		return c;
	}

	public Card storeCard(Card card) {
		Card c = null;
		try {
			c = cardAccessor.storeCard(card);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	public void deleteCardById(Long cardId) {
		cardAccessor.deleteCardById(cardId);
	}
	
	public Card getCardByKanji(String kanji) {
		Card c = null;
		try {
			c = cardAccessor.getCardByKanji(kanji);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	};
	public Card getCardByHiragana(String hiragana) {
		Card c = null;
		try {
			c = cardAccessor.getCardByHiragana(hiragana);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	};
	public Card getCardByKatakana(String katakana) {
		Card c = null;
		try {
			c = cardAccessor.getCardByKatakana(katakana);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	};
	public Card getCardByTranslation(String translation) {
		Card c = null;
		try {
			c = cardAccessor.getCardByTranslation(translation);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	};
	public Card getCardByDescription(String desc) {
		Card c = null;
		try {
			c = cardAccessor.getCardByDescription(desc);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	};
	public List<Card> getAllCardsByKanji(String kanji) {
		List<Card> cards = new ArrayList<>();
		try {
			cards = cardAccessor.getAllCardsByKanji(kanji);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cards;
	}
	public List<Card> getAllCardsByLanguage (String lang) {
		List<Card> cards = new ArrayList<>();
		try {
			cards = cardAccessor.getAllCardsByLanguage(lang);
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cards;
	}
	
	public List<Card> getAllCards() {
		List<Card> cards = new ArrayList<>();
		try {
			cards = cardAccessor.getAllCards();
		} catch (CardNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cards;
	};
}
