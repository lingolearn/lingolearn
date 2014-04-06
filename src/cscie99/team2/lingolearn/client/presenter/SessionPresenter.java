package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.event.AnalyticsEvent;
import cscie99.team2.lingolearn.client.event.FlippedCardEvent;
import cscie99.team2.lingolearn.client.event.FlippedCardEventHandler;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEventHandler;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.client.view.QuizView;
import cscie99.team2.lingolearn.client.view.SessionView;
import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SessionPresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final SessionView display;
  private final CourseServiceAsync courseService;
  private final CardPresenter cardPresenter;
  private final QuizPresenter quizPresenter;
  private Session session;
  private int currentCardNumber;
  
  public SessionPresenter(CourseServiceAsync courseService, 
		  CardServiceAsync cardService, HandlerManager eventBus, 
		  SessionView display) {
      this.courseService = courseService;
      this.cardPresenter = new CardPresenter(cardService, eventBus, new CardView());
      this.quizPresenter = new QuizPresenter(cardService, eventBus, new QuizView());
	  this.eventBus = eventBus;
      this.display = display;
  }
  
  public void bind() {
	  
	  cardPresenter.getDisplay().getKnowledgeHighButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  recordKnowledge(Assessment.DEFINITELYKNEWIT);
		  }
	  });
	  
	  cardPresenter.getDisplay().getKnowledgeMediumButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  recordKnowledge(Assessment.SORTAKNEWIT);
		  }
	  });
	  
	  cardPresenter.getDisplay().getKnowledgeLowButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  recordKnowledge(Assessment.NOCLUE);
		  }
	  }); 
	  
	  quizPresenter.getDisplay().getNextButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  gotoNextCard();
		  }
	  });
	  
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    
    //Set session based on query parameter in URL
    Long sessionId = (long) 0;
    sessionId = Long.valueOf(Window.Location.getParameter("sessionId"));
    this.setSession(sessionId);
  }
  
  /*
   * Sets and starts a session
   */
  public void setSession(Long sessionId) {
	  
	  courseService.getSessionById(sessionId, 
			  new AsyncCallback<Session>() {
		  public void onSuccess(Session returnedSession) {
			  session = returnedSession;

		      if (session instanceof Lesson) {
		    	  cardPresenter.go(display.getCardContainer());
		      } else {
		    	  quizPresenter.go(display.getCardContainer());
		      }
		      
			  display.setSessionName("Session " + session.getSessionId());
			  currentCardNumber = 0;
			  gotoNextCard();
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching session");
	      }
	  });
  }
  
  private void recordKnowledge(Assessment knowledge) {
	  //Send knowledge to the analytics service
	  FlashCardResponse flashCardResponse = new FlashCardResponse();
	  flashCardResponse.setCardId(session.getDeck().getCardIds().get(currentCardNumber));
	  flashCardResponse.setAssessment(knowledge);
	  AnalyticsEvent flashCardEvent = new AnalyticsEvent(flashCardResponse);
	  eventBus.fireEvent(flashCardEvent);
	  
	  //Move to the next card
	  gotoNextCard();
  }
  
  private void gotoNextCard() {
	  if (session instanceof Lesson) {
		  cardPresenter.setCardData(session.getDeck().getCardIds().get(currentCardNumber));
	  } else {
		  quizPresenter.setCardData(session.getDeck().getCardIds().get(currentCardNumber));
	  }
	  currentCardNumber++;
	  if (currentCardNumber >= session.getDeck().getCardIds().size()) {
		  currentCardNumber = 0;
	  }
  }
  

}
