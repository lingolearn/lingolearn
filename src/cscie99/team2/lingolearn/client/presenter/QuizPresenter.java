package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.event.AnalyticsEvent;
import cscie99.team2.lingolearn.client.event.FlippedCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.QuizView;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class QuizPresenter implements Presenter {  

  private final CardServiceAsync cardService;
  private final HandlerManager eventBus;
  private final QuizView display;
  private Card currentCard;
  private String currentCorrectAnswer;
  
  public QuizPresenter(CardServiceAsync cardService, HandlerManager eventBus, QuizView display) {
    this.cardService = cardService;
    this.eventBus = eventBus;
    this.display = display;
  }
  
  public QuizView getDisplay() {
	  return this.display;
  }
  
  public void bind() {
    
	display.getSubmitButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	  boolean wasCorrect = display.getSelectedAnswer().equals(currentCorrectAnswer);
    	  
    	  //Check answer and emit event saying user has submitted
    	  if (wasCorrect) {
    		  display.showCorrect();
    		  display.showNextButton();
    		  display.hideSubmitButton();
    	  } else {
    		  display.showIncorrect();
    		  display.showNextButton();
    		  display.hideSubmitButton();
    	  }
    	  
    	  //Send knowledge to the analytics service
    	  QuizResponse quizResponse = new QuizResponse();
    	  quizResponse.setCardId(currentCard.getId());
    	  quizResponse.setCorrect(wasCorrect);
    	  AnalyticsEvent quizEvent = new AnalyticsEvent(quizResponse);
    	  eventBus.fireEvent(quizEvent);
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
  }
  
  public void setCardData(Long cardId) {
	  
	  cardService.getCardById(cardId, new AsyncCallback<Card>() {
	      public void onSuccess(Card card) {
	          populateQuizInfo(card);
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching card.");
	      }
	    });
  }
  
  private void populateQuizInfo(Card card) {
	  currentCard = card;
	  this.currentCorrectAnswer = card.getKanji();
	  if (card.getHiragana() != null) {
		  this.currentCorrectAnswer += " " + card.getHiragana();
	  }
	  if (card.getKatakana() != null) {
		  this.currentCorrectAnswer += " " + card.getKatakana();
	  }
	  display.clearQuiz();
	  display.addToStem(card.getTranslation());
	  display.addAnswer("bs answer");
	  display.addAnswer("not the right one");
	  display.addAnswer(this.currentCorrectAnswer);
	  display.addAnswer("also wrong");
  }

}
