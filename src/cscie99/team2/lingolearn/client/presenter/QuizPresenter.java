package cscie99.team2.lingolearn.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.view.QuizView;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.SessionTypes;

public class QuizPresenter implements Presenter {

	// The number of confusers to get from the server
	private final static int CONFUSER_COUNT = 3;
	
	// The phrases that are used for null strings
	private final static String NO_HIRAGANA_PHRASE = "[No Hiragana]";
	private final static String NO_KANJI_PHARSE = "[No Kanji]";
	private final static String NO_KATAKANA_PHRASE = "[No Katakana]";
		
	private final CardServiceAsync cardService;
	private final QuizView display;
	private final SessionPresenter sessionPresenter;
	private Card currentCard;
	private int currentNumConfusers;
	private String currentWrongAnswers;
	private String currentCorrectAnswer;
	private boolean useConfusers;

	public QuizPresenter(CardServiceAsync cardService, HandlerManager eventBus,
			QuizView display, SessionPresenter sessionPresenter) {
		this.cardService = cardService;
		this.display = display;
		this.sessionPresenter = sessionPresenter;
		currentNumConfusers = 0;
		useConfusers = true;
	}

	public QuizView getDisplay() {
		return this.display;
	}

	public void setUseConfusers(Boolean useConfusers, SessionTypes sessionType) {
		this.useConfusers = useConfusers && sessionType.isConfuserSupported();
	}

	public void bind() {
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				submit();
			}
		});
	}

	public void submit() {
		// Check answer and emit event saying user has submitted
		boolean wasCorrect = display.getSelectedAnswer().equals(currentCorrectAnswer);
		if (wasCorrect) {
			display.showCorrect();
			sessionPresenter.gotoNextCard();
		} else {
			display.showIncorrect(currentCorrectAnswer);
		}

		// Send knowledge to the analytics service
		QuizResponse quizResponse = new QuizResponse();
		quizResponse.setCardId(currentCard.getId());
		quizResponse.setCorrect(wasCorrect);
		quizResponse.setNumConfusersUsed(currentNumConfusers);
		quizResponse.setWrongAnswers(currentWrongAnswers);
		sessionPresenter.recordQuizResponse(quizResponse);
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
	}

	public void setCardData(Long cardId, ArrayList<Long> otherOptionIds, final SessionTypes sessionType) {
		ArrayList<Long> requestedIds = otherOptionIds;
		requestedIds.add(cardId);
		cardService.getCardsByIds(requestedIds,
				new AsyncCallback<ArrayList<Card>>() {
					public void onSuccess(ArrayList<Card> cards) {
						Card card = cards.remove(cards.size() - 1);
						// If we are using katakana confusers, make sure we have something 
						// to actually work with
						// HACK This is a bit of a hack since the rendering can make things
						// HACK a bit unusual. A better approach requires a touch more 
						// HACK refactoring to make sure Translation to Katakana can't be 
						// HACK selected for decks without katakana
						if (!isCardValid(card, sessionType)) {
							sessionPresenter.gotoNextCard();
							return;
						}
						populateQuizInfo(card, cards, sessionType);
					}
					public void onFailure(Throwable caught) {
						Window.alert("Error fetching card.");
					}
				});
	}

	private void populateQuizInfo(Card card, final ArrayList<Card> otherCards, final SessionTypes sessionType) {
		currentCard = card;
		this.currentCorrectAnswer = getCorrectAnswer(card, sessionType);
		display.clearQuiz();
		setQuestion(card, sessionType);
		display.addAnswer(this.currentCorrectAnswer);
		// Set the wrong answers if we aren't using confusers
		if (!useConfusers) {
			useAsWrongAnswers(otherCards, sessionType);
			return;
		}
		// Configure things to use the confusers
		cardService.getConfusersForCard(currentCard, sessionType,
				new AsyncCallback<List<String>>() {
					@Override
					public void onFailure(Throwable caught) {
						useAsWrongAnswers(otherCards, sessionType);
					}

					@Override
					public void onSuccess(List<String> result) {
						// First, make sure we actually have something returned
						if (result == null || result.size() == 0) {
							useAsWrongAnswers(otherCards, sessionType);
							return;
						}
						
						// Add the wrong confusers to the answer display
						ArrayList<String> wrongAnswerList = new ArrayList<String>(result);
						for (String answer : result) {
							display.addAnswer(answer);
						}
						currentNumConfusers = wrongAnswerList.size();
						
						// Pad the wrong answers with random selections if need be
						for (int i = currentNumConfusers; wrongAnswerList.size() < CONFUSER_COUNT; i++) {
							if (otherCards.get(i) != null) {
								String answer = getCorrectAnswer(otherCards.get(i), sessionType);
								if (answer.isEmpty()) {
									continue;
								}
								display.addAnswer(answer);
								wrongAnswerList.add(getCorrectAnswer(otherCards.get(i), sessionType));
							}
						}
						
						// Prepare the wrong answer list
						currentWrongAnswers = "";
						String delimiter = "";
						for (int i = 0; i < wrongAnswerList.size(); i++) {
							currentWrongAnswers += delimiter + wrongAnswerList.get(i);
							delimiter = ";";
						}
					}
				});
	}

	/**
	 * Set the question text that will be displayed to the user.
	 * 
	 * @param card The card to extract the text from.
	 * @param sessionType The type of session the user requested.
	 */
	private void setQuestion(Card card, SessionTypes sessionType) {
		switch (sessionType) {
			case Kanji_Translation:
			case Kanji_Hiragana:
				display.addToStem(card.getKanji());
				return;
			case Katakana_Translation:
				display.addToStem(card.getKatakana());
				return;
			case Hiragana_Translation:
			case Hiragana_Kanji:
				display.addToStem(card.getHiragana());
				return;
			case Translation_Kanji:
			case Translation_Hiragana:
			case Translation_Katakana:
				display.addToStem(card.getTranslation());
				return;
		}
		// If we got here then the options were exhausted, so throw an error
		throw new IllegalStateException("The session type provided, "
				+ sessionType + ", was not recognized.");
	}

	/**
	 * 
	 * @param card
	 * @param sessionType
	 * @return
	 */
	private String getCorrectAnswer(Card card, SessionTypes sessionType) {
		switch (sessionType) {
			case Kanji_Translation:
			case Katakana_Translation:
			case Hiragana_Translation:
				return card.getTranslation();
			case Hiragana_Kanji:
			case Translation_Kanji:
				return (!card.getKanji().isEmpty()) ? card.getKanji() : NO_KANJI_PHARSE;
			case Translation_Katakana:
				return (!card.getKatakana().isEmpty()) ? card.getKatakana() : NO_KATAKANA_PHRASE;
			case Translation_Hiragana:
			case Kanji_Hiragana:
				return (!card.getHiragana().isEmpty()) ? card.getHiragana() : NO_HIRAGANA_PHRASE;
		}
		// If we got here then the options were exhausted, so throw an error
		throw new IllegalStateException("The session type provided, "
				+ sessionType + ", was not recognized.");
	}

	/**
	 * Check to see if this card is valid for the selected session type.
	 * 
	 * @param card The card to check to see if it is valid.
	 * @param sessionType The current session type we are running.
	 * @return True if the combination is valid, false otherwise.
	 */
	private boolean isCardValid(Card card, SessionTypes sessionType) {
		switch (sessionType) {
			case Hiragana_Kanji:
			case Hiragana_Translation:
				return !card.getHiragana().isEmpty();
			case Kanji_Hiragana:
			case Kanji_Translation:
				return !card.getKanji().isEmpty();
			case Katakana_Translation:
				return !card.getKatakana().isEmpty();
			case Translation_Hiragana:
			case Translation_Kanji:
			case Translation_Katakana:
				return !card.getTranslation().isEmpty();
		}
		// If we got here then the options were exhausted, so throw an error
		throw new IllegalStateException("The session type provided, "
				+ sessionType + ", was not recognized.");
	}
	
	/**
	 * Generate the wrong answers to use for the current card.
	 * 
	 * @param otherCards The other cards to choose the wrong answers from.
	 * @param sessionType The session type that is currently being displayed.
	 * @param confuserCount The current number of confusers.
	 */
	private void useAsWrongAnswers(List<Card> otherCards, SessionTypes sessionType) {
		currentNumConfusers = 0;
		currentWrongAnswers = "";
		String delimiter = "";
		// No blanks for wrong answers if the correct answer is blank
		boolean blankUsed = currentCorrectAnswer.isEmpty();
		for (Card card : otherCards) {
			// Get the answer and make sure we limit the appearance of blanks 
			// in the answers to just one occurrence 
			String answer = getCorrectAnswer(card, sessionType);
			if (answer.isEmpty() && blankUsed) {
				continue;
			}
			blankUsed = (answer.isEmpty()) ? true : blankUsed;
			// Add the wrong answer to the list
			display.addAnswer(answer);
			currentWrongAnswers += delimiter + answer;
			delimiter = ";";
		}
	}
}
