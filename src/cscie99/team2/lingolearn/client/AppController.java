package cscie99.team2.lingolearn.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEventHandler;
import cscie99.team2.lingolearn.client.presenter.CardPresenter;
import cscie99.team2.lingolearn.client.presenter.CoursePresenter;
import cscie99.team2.lingolearn.client.presenter.HomePresenter;
import cscie99.team2.lingolearn.client.presenter.Presenter;
import cscie99.team2.lingolearn.client.presenter.RegistrationPresenter;
import cscie99.team2.lingolearn.client.presenter.SessionPresenter;
import cscie99.team2.lingolearn.client.view.AppRegisterView;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.client.view.HomeView;
import cscie99.team2.lingolearn.client.view.SessionView;
import cscie99.team2.lingolearn.shared.Card;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private final UserServiceAsync userService;
  private final CardServiceAsync cardService; 
  private final CourseServiceAsync courseService;
  private final AnalyticsServiceAsync analyticsService;
  private final QuizResponseServiceAsync quizResponseService;
  private final FlashCardResponseServiceAsync flashCardResponseService;
  private HasWidgets container;
  private boolean userAuthenticated;
  
  public AppController(UserServiceAsync userService, CourseServiceAsync courseService,
		  CardServiceAsync cardService, AnalyticsServiceAsync analyticsService, 
		  QuizResponseServiceAsync quizResponseService, 
		  FlashCardResponseServiceAsync flashCardResponseService, 
		  HandlerManager eventBus) {
    this.eventBus = eventBus;
    this.userService = userService;
    this.cardService = cardService;
    this.courseService = courseService;
    this.analyticsService = analyticsService;
    this.quizResponseService = quizResponseService;
    this.flashCardResponseService = flashCardResponseService;
    
    this.userAuthenticated = false;
    bind();
  }
  
  
  private void bind() {
    History.addValueChangeHandler(this);

    eventBus.addHandler(ViewCardEvent.TYPE,
        new ViewCardEventHandler() {
          public void onViewCard(ViewCardEvent event) {
            doViewCard();
          }
        });  

  }
  
  private void doViewCard() {
    History.newItem("viewCard");
  }
  
  @Override
  public void go(final HasWidgets container) {
    this.container = container;
    
    authenticateUser();
  }

  /*
   * Redirect the User to their appropriate view.
   * @param isAuthenticated - this indicates if the user is logged in
   * or not.  If the user is not logged in, they will be
   * redirected to the login page.
   */
  private void openView( boolean isAuthenticated ){

	  if( isAuthenticated ){
		  if ("".equals(History.getToken())) {
		        History.newItem("home");
		  }
		  else {
		        History.fireCurrentHistoryState();
		  }
	  }
	  else {
		  Window.Location.assign("/login.html");
	  }
  }
  
  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();
    
    if (token != null) {
      Presenter presenter = null;

      switch (token) {
      
      case "viewCard": 
    	  presenter = new CardPresenter(cardService, 
    			  eventBus, new CardView());
    	  break;
      case "home":
    	  presenter = new HomePresenter(userService, courseService, 
    			  eventBus, new HomeView());
    	  break;
      case "course":
    	  presenter = new CoursePresenter(courseService, analyticsService, 
    			  eventBus, new CourseView());
    	  break;
      case "session":
    	  presenter = new SessionPresenter(courseService, cardService, 
    			  eventBus, new SessionView());
    	  break;
      case "register":
    	  presenter = new RegistrationPresenter(userService, eventBus, 
    			  new AppRegisterView());
    	  break;
      }
      
      if (presenter != null) {
        presenter.go(container);
      }
    }
  } 
  
  /*
   * Check to see that a user is logged into this session.
   * This is performed by calling the user service.
   * One the async method returns, we can bring the user to a
   * page (to use the app, or login).
   *
   * @notes this is stubbed.  Registration needs to be added
   */
  private void authenticateUser(){
	  
	  //TODO: Add registration redirection, rather than just
	  // gmail login.
	  /*
	  userService.isUserLoggedIn( new AsyncCallback<Boolean>(){
	  
		  public void onSuccess(Boolean auth) {
			  userAuthenticated = auth;
		  }
		      
		  public void onFailure(Throwable caught) {
		        Window.alert("Error fetching card.");
		  }
	  });
	  */
	  userService.getSessionGmail( new AsyncCallback<String>(){
		  public void onSuccess(String gmail) {
			  userAuthenticated = !gmail.equals("");
			  openView(userAuthenticated);
		  }
		      
		  public void onFailure(Throwable caught) {
		        Window.alert("Error fetching card.");
		  }
	  
	  });
  }
}
