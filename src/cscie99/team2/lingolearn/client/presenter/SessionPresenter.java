package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.event.FlippedCardEvent;
import cscie99.team2.lingolearn.client.event.FlippedCardEventHandler;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEventHandler;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.client.view.SessionView;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
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
  private Session session;
  private int currentCardNumber;
  
  public SessionPresenter(CourseServiceAsync courseService, 
		  CardServiceAsync cardService, HandlerManager eventBus, 
		  SessionView display) {
      this.courseService = courseService;
      this.cardPresenter = new CardPresenter(cardService, eventBus, new CardView());
	  this.eventBus = eventBus;
      this.display = display;
      
      //Let the card presenter handle the card display
      this.cardPresenter.go(this.display.getCardContainer());
  }
  
  public void bind() {
	  
	  display.getKnowledgeHighButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  recordKnowledge("high");
		  }
	  });
	  
	  display.getKnowledgeMediumButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  recordKnowledge("medium");
		  }
	  });
	  
	  display.getKnowledgeLowButton().addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
			  recordKnowledge("low");
		  }
	  });
	  
	  eventBus.addHandler(FlippedCardEvent.TYPE,
        new FlippedCardEventHandler() {
          public void onFlippedCard(FlippedCardEvent event) {
              display.showKnowledgeAssessmentArea();
          }
        }); 
	  
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    
    //Set session based on query parameter in URL
    String sessionId = "session1";
    sessionId = Window.Location.getParameter("sessionId");
    this.setSession(sessionId);
  }
  
  /*
   * Sets and starts a session
   */
  public void setSession(String sessionId) {
	  
	  courseService.getSessionById(sessionId, 
			  new AsyncCallback<Session>() {
		  public void onSuccess(Session returnedSession) {
			  session = returnedSession;
			  display.setSessionName("Session " + session.getSessionId());
			  currentCardNumber = 0;
			  gotoNextCard();
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching session");
	      }
	  });
  }
  
  private void recordKnowledge(String knowledge) {
	  //TODO: send knowledge to the course service?
	  gotoNextCard();
  }
  
  private void gotoNextCard() {
	  cardPresenter.setCardData(session.getDeck().getCardIds().get(currentCardNumber));
	  currentCardNumber++;
	  if (currentCardNumber >= session.getDeck().getCardIds().size()) {
		  currentCardNumber = 0;
	  }
	  display.hideKnowledgeAssessmentArea();
  }
  

}
