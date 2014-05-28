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
						for (int i = currentNumConfusers; i < CONFUSER_COUNT; i++) {
							if (otherCards.get(i) != null) {
								display.addAnswer(getCorrectAnswer(otherCards.get(i), sessionType));
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

	private String getCorrectAnswer(Card card, SessionTypes sessionType) {
		switch (sessionType) {
			case Kanji_Translation:
			case Katakana_Translation:
			case Hiragana_Translation:
				return card.getTranslation();
			case Hiragana_Kanji:
			case Translation_Kanji:
				return card.getKanji();
			case Translation_Katakana:
				return card.getKatakana();
			case Translation_Hiragana:
			case Kanji_Hiragana:
				return card.getHiragana();
		}
		// If we got here then the options were exhausted, so throw an error
		throw new IllegalStateException("The session type provided, "
				+ sessionType + ", was not recognized.");
	}

	private void useAsWrongAnswers(ArrayList<Card> otherCards, SessionTypes sessionType) {
		currentNumConfusers = 0;
		currentWrongAnswers = "";
		for (int i = 0; i < otherCards.size(); i++) {
			Card otherCard = otherCards.get(i);
			String cardAnswer = getCorrectAnswer(otherCard, sessionType);
			display.addAnswer(cardAnswer);
			currentWrongAnswers += cardAnswer;
			if (i != (otherCards.size() - 1)) {
				currentWrongAnswers += ";";
			}
		}
	}
}
