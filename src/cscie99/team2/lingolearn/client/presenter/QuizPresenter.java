package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.view.QuizView;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.QuizResponse;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
import java.util.List;

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
  
  public QuizPresenter(CardServiceAsync cardService, HandlerManager eventBus, 
		  QuizView display, SessionPresenter sessionPresenter) {
    this.cardService = cardService;
    this.eventBus = eventBus;
    this.display = display;
    this.sessionPresenter = sessionPresenter;
    currentNumConfusers = 0;
    currentConfuserType = "";
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
    		  display.showIncorrect(currentCorrectAnswer);
    		  display.showNextButton();
    		  display.hideSubmitButton();
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
	  if (!card.getHiragana().equals("")) {
		  this.currentCorrectAnswer += "  —  " + card.getHiragana();
	  }
	  if (!card.getKatakana().equals("")) {
		  this.currentCorrectAnswer += " " + card.getKatakana();
	  }
	  display.clearQuiz();
	  display.addToStem(card.getTranslation());
	  display.addAnswer(this.currentCorrectAnswer);
	  cardService.getConfusersForCard(currentCard, new AsyncCallback<List<String>>() {

		@Override
		public void onFailure(Throwable caught) {
			currentNumConfusers = 0;
			display.addAnswer("failed confuser 1");
			display.addAnswer("failed confuser 2");
			display.addAnswer("failed confuser 3");
			currentWrongAnswers = "failed confuser 1,failed confuser 2,failed confuser 3";
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
				display.addAnswer("mock confuser " + i);
				wrongAnswerList.add("mock confuser " + i);
			}
			
			currentWrongAnswers = "";
			for (int i=0;i<wrongAnswerList.size();i++) {
				currentWrongAnswers += wrongAnswerList.get(i);
				if (i != (wrongAnswerList.size()-1)) {
					currentWrongAnswers += ",";
				}
			}
		}
		  
	  });
	  
  }

}
