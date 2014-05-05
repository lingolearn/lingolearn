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

  private final CardServiceAsync cardService;
  private final HandlerManager eventBus;
  private final QuizView display;
  private final SessionPresenter sessionPresenter;
  private Card currentCard;
  private int currentNumConfusers;
  private String currentConfuserType;  //TODO
  private String currentWrongAnswers;
  private String currentCorrectAnswer;
  private boolean useConfusers;
  
  public QuizPresenter(CardServiceAsync cardService, HandlerManager eventBus, 
		  QuizView display, SessionPresenter sessionPresenter) {
    this.cardService = cardService;
    this.eventBus = eventBus;
    this.display = display;
    this.sessionPresenter = sessionPresenter;
    currentNumConfusers = 0;
    currentConfuserType = "";
    useConfusers = true;
  }
  
  public QuizView getDisplay() {
	  return this.display;
  }
  
  public void setUseConfusers(Boolean useConfusers) {
	  this.useConfusers = useConfusers;
  }
  
  public void bind() {
    
	display.getSubmitButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	  boolean wasCorrect = display.getSelectedAnswer().equals(currentCorrectAnswer);
    	  
    	  //Check answer and emit event saying user has submitted
    	  if (wasCorrect) {
    		  display.showCorrect();
    		  sessionPresenter.gotoNextCard();
    	  } else {
    		  display.showIncorrect(currentCorrectAnswer);
    	  }
    	  
    	  //Send knowledge to the analytics service
    	  QuizResponse quizResponse = new QuizResponse();
    	  quizResponse.setCardId(currentCard.getId());
    	  quizResponse.setCorrect(wasCorrect);
    	  quizResponse.setNumConfusersUsed(currentNumConfusers);
    	  quizResponse.setWrongAnswers(currentWrongAnswers);
    	  sessionPresenter.recordQuizResponse(quizResponse);
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
  }
  
  public void setCardData(Long cardId, ArrayList<Long> otherOptionIds,
  												final SessionTypes sessionType ) {
	  
	  ArrayList<Long> requestedIds = otherOptionIds;
	  requestedIds.add(cardId);
	  
	  cardService.getCardsByIds(requestedIds, new AsyncCallback<ArrayList<Card>>() {
	      public void onSuccess(ArrayList<Card> cards) {
	    	  Card card = cards.remove(cards.size()-1);
	    	  populateQuizInfo(card,cards, sessionType );
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching card.");
	      }
	    });
  }
  
  private void populateQuizInfo(Card card, final ArrayList<Card> otherCards,
  																			final SessionTypes sessionType) {
	  currentCard = card;
	  //this.currentCorrectAnswer = card.getDisplayString();
	  this.currentCorrectAnswer = getCorrectAnswer(card, sessionType);
	  display.clearQuiz();
	  //display.addToStem(card.getTranslation());
	  setQuestion(card, sessionType);
	  display.addAnswer(this.currentCorrectAnswer);
	  if (useConfusers) {
		  cardService.getConfusersForCard(currentCard, sessionType, 
		  												new AsyncCallback<List<String>>() {
	
			@Override
			public void onFailure(Throwable caught) {
				useAsWrongAnswers(otherCards);
			}
			
			@Override
			public void onSuccess(List<String> result) {
				int count = 0;
				ArrayList<String> wrongAnswerList = new ArrayList<String>();
				String wrongAns;
				if (result != null) {
					for (int i=0;i<result.size();i++) {
						count++;
						if (currentCard.getKatakana().equals("")) {
							wrongAns = result.get(i) + "  —  " + currentCard.getHiragana();
						} else {
							wrongAns = result.get(i);
						}
						wrongAnswerList.add(wrongAns);
						display.addAnswer(wrongAns);
					}
				}
				currentNumConfusers = count;
				for (int i=count;i<3;i++) {
					if (otherCards.get(i) != null) {
						display.addAnswer(otherCards.get(i).getDisplayString());
						wrongAnswerList.add(otherCards.get(i).getDisplayString());
					}
				}
				
				currentWrongAnswers = "";
				for (int i=0;i<wrongAnswerList.size();i++) {
					currentWrongAnswers += wrongAnswerList.get(i);
					if (i != (wrongAnswerList.size()-1)) {
						currentWrongAnswers += ";";
					}
				}
				
				
			}
			  
		  });
	  } else {
		  useAsWrongAnswers(otherCards);
	  }
	  
  }
  
  private void setQuestion(Card card, SessionTypes sessionType ){
  	switch( sessionType ){
  		case Kanji_Translation:
  		case Kanji_Hiragana:
  			display.addToStem(card.getKanji());
  			break;
  			
  		case Hiragana_Translation:
  		case Hiragana_Kanji:
  			display.addToStem(card.getHiragana());
  			break;
  			
  		case Translation_Kanji:
  		case Translation_Hiragana:
  			display.addToStem(card.getTranslation());
  			break;
  			
  		default:
  			break;
  	}
  }
  
  private String getCorrectAnswer(Card card, SessionTypes sessionType ){
  	switch( sessionType ){
			case Kanji_Translation:
			case Hiragana_Translation:
				return card.getTranslation();
				
			case Hiragana_Kanji:
			case Translation_Kanji:
				return card.getKanji();
				
			
			case Translation_Hiragana:
			case Kanji_Hiragana:
				return card.getHiragana();
				
			default:
				return "";
  	}
  }
  
  private void useAsWrongAnswers(ArrayList<Card> otherCards) {
	currentNumConfusers = 0;
	currentWrongAnswers = "";
	for (int i=0;i<otherCards.size();i++) {
		display.addAnswer(otherCards.get(i).getDisplayString());
		currentWrongAnswers += otherCards.get(i).getDisplayString();
		if (i != (otherCards.size()-1)) {
			currentWrongAnswers += ";";
		}
	}
  }

}
