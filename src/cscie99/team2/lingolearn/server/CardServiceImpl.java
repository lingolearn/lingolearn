package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.server.confuser.CharacterType;
import cscie99.team2.lingolearn.server.confuser.Confuser;
import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.SessionTypes;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;
import cscie99.team2.lingolearn.shared.error.ConfuserException;

@SuppressWarnings("serial")
public class CardServiceImpl extends RemoteServiceServlet implements CardService {
	// The number of confusers to be returned
	private final static int CONFUSER_LIST_SIZE = 3;
	
	CardDAO cardAccessor = CardDAO.getInstance();
	
	/**
	 * Soon to be replaced by real DAO implementation 
	 */
	public Card getCardById(Long cardId) {
		return cardAccessor.getCardById(cardId);
	}
	
	public ArrayList<Card> getCardsByIds(ArrayList<Long> cardIds) {
		ArrayList<Card> cards = new ArrayList<Card>();
		if (cardIds != null) {
			for (int i=0;i<cardIds.size();i++) {
				cards.add(cardAccessor.getCardById(cardIds.get(i)));
			}
		}
		return cards;
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
	}
	
	@Override
	public List<String> getConfusersForCard(Card card) {
		List<String> confuserStrings = null;
		Confuser confuser = new Confuser();
		try {
			if (card.getKatakana().equals("")) {
				confuserStrings = confuser.getConfusers(card, CharacterType.Kanji, 3);
			} else {
				confuserStrings = confuser.getConfusers(card, CharacterType.Katakana, 3);
			}
		} catch (ConfuserException e) {
			e.printStackTrace();
		}
		return confuserStrings;
	}

	@Override
	public List<String> getConfusersForCard(Card card, SessionTypes type) {
		try {
			// Generate the confers on the basis of the session type
			Confuser confuser = new Confuser();
			switch(type)
			{
				// Get confusers for kanji answers
				case Translation_Kanji:
				case Hiragana_Kanji:
					return confuser.getConfusers(card, CharacterType.Kanji, CONFUSER_LIST_SIZE);
				// Get confusers for katakana answers
				case Translation_Katakana:
					return confuser.getConfusers(card, CharacterType.Katakana, CONFUSER_LIST_SIZE);
				// Get confusers for hiragana answers
				case Kanji_Hiragana:
				case Translation_Hiragana:
					return confuser.getConfusers(card, CharacterType.Hiragana, CONFUSER_LIST_SIZE);
				// Confusers are not supported for the translations
				case Hiragana_Translation:
				case Kanji_Translation:
				case Katakana_Translation:
				default:
					return new ArrayList<String>();
			}
		} catch (ConfuserException ex) {
			// Generally this shouldn't happen so log the situation and return 
			// and empty list back to the client
			// TODO Add Log4j trace
			System.err.println("A confuser exception occured in getConfusersForCard!");
			System.err.println(ex.getMessage());			
			return new ArrayList<String>();
		}
	}
}
