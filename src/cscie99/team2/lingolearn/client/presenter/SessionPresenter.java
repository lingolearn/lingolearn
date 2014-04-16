package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.CurrentUser;
import cscie99.team2.lingolearn.client.event.AnalyticsEvent;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.QuizView;
import cscie99.team2.lingolearn.client.view.SessionView;
import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.UserSession;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class SessionPresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final SessionView display;
  private final CourseServiceAsync courseService;
  private final CardPresenter cardPresenter;
  private final QuizPresenter quizPresenter;
  private Session session;
  private UserSession userSession;
  private int currentCardNumber;
  
  public SessionPresenter(CourseServiceAsync courseService, 
		  CardServiceAsync cardService, HandlerManager eventBus, 
		  SessionView display) {
      this.courseService = courseService;
      this.cardPresenter = new CardPresenter(cardService, eventBus, new CardView());
      this.quizPresenter = new QuizPresenter(cardService, eventBus, new QuizView(), this);
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

			  courseService.createUserSession(session.getSessionId(), CurrentUser.gplusId, 
					  new AsyncCallback<UserSession>() {

				  public void onSuccess(UserSession returnedUserSession) {
					  userSession = returnedUserSession;
					  
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
					  Window.alert("unable to create user session");
				  }
				  
			  });
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching session");
	      }
	  });
  }
  
  private void recordKnowledge(Assessment knowledge) {
	  //Send knowledge to the analytics service
	  FlashCardResponse flashCardResponse = new FlashCardResponse();
	  flashCardResponse.setGplusId(CurrentUser.gplusId);
	  flashCardResponse.setCardId(session.getDeck().getCardIds().get(currentCardNumber));
	  flashCardResponse.setSessionId(session.getSessionId());
	  flashCardResponse.setUserSessionId(userSession.getUserSessionId());
	  flashCardResponse.setAssessment(knowledge);
	  AnalyticsEvent flashCardEvent = new AnalyticsEvent(flashCardResponse);
	  eventBus.fireEvent(flashCardEvent);
	  
	  //Move to the next card
	  gotoNextCard();
  }
  
  public void recordQuizResponse(QuizResponse quizResponse) {
	  quizResponse.setGplusId(CurrentUser.gplusId);
	  quizResponse.setSessionId(session.getSessionId());
	  quizResponse.setUserSessionId(userSession.getUserSessionId());
	  AnalyticsEvent quizEvent = new AnalyticsEvent(quizResponse);
	  eventBus.fireEvent(quizEvent);
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
