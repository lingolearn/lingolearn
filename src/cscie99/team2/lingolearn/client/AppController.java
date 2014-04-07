package cscie99.team2.lingolearn.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.event.AnalyticsEvent;
import cscie99.team2.lingolearn.client.event.AnalyticsEventHandler;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEventHandler;
import cscie99.team2.lingolearn.client.presenter.CardPresenter;
import cscie99.team2.lingolearn.client.presenter.CoursePresenter;
import cscie99.team2.lingolearn.client.presenter.HomePresenter;
import cscie99.team2.lingolearn.client.presenter.ImportPresenter;
import cscie99.team2.lingolearn.client.presenter.NewCoursePresenter;
import cscie99.team2.lingolearn.client.presenter.Presenter;
import cscie99.team2.lingolearn.client.presenter.RegistrationPresenter;
import cscie99.team2.lingolearn.client.presenter.ResearchPresenter;
import cscie99.team2.lingolearn.client.presenter.SessionPresenter;
import cscie99.team2.lingolearn.client.view.AppRegisterView;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.client.view.HomeView;
import cscie99.team2.lingolearn.client.view.ImportView;
import cscie99.team2.lingolearn.client.view.NewCourseView;
import cscie99.team2.lingolearn.client.view.ResearchView;
import cscie99.team2.lingolearn.client.view.SessionView;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.GoogleIdPackage;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.User;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private final UserServiceAsync userService;
  private final CardServiceAsync cardService; 
  private final CourseServiceAsync courseService;
  private final AnalyticsServiceAsync analyticsService;
  private final QuizResponseServiceAsync quizResponseService;
  private final FlashCardResponseServiceAsync flashCardResponseService;
  private final StorageServiceAsync storageService;
  private HasWidgets container;
  private boolean userAuthenticated;
  
  public AppController(UserServiceAsync userService, CourseServiceAsync courseService,
		  CardServiceAsync cardService, AnalyticsServiceAsync analyticsService,
		  QuizResponseServiceAsync quizResponseService, 
		  FlashCardResponseServiceAsync flashCardResponseService, 
		  StorageServiceAsync storageService,
		  HandlerManager eventBus) {
	  
    this.eventBus = eventBus;
    this.userService = userService;
    this.cardService = cardService;
    this.courseService = courseService;
    this.analyticsService = analyticsService;
    this.quizResponseService = quizResponseService;
    this.flashCardResponseService = flashCardResponseService;
    this.storageService = storageService;
    
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
    
    eventBus.addHandler(AnalyticsEvent.TYPE,
            new AnalyticsEventHandler() {
    
    		@Override
    		public void onQuizResponse(AnalyticsEvent event,
    				final QuizResponse quizResponse) {
    			
    			userService.getCurrentUser(new AsyncCallback<User>() {
    				  public void onSuccess(User user) {
    					  if (user instanceof User) {
    						  quizResponse.setGplusId(user.getGplusId());
    						  quizResponseService.storeQuizResponse(quizResponse, 
    								  new AsyncCallback<QuizResponse>() {
    							    
    			    				@Override
    			    				public void onFailure(Throwable caught) {
    			    					System.out.println("failed to store quiz response");
    			    				}
    			    
    			    				@Override
    			    				public void onSuccess(QuizResponse result) {
    			    					System.out.println("success");
    			    				}
    			    				
    			    			});
    					  }
    				  }
    				  public void onFailure(Throwable caught) {
    					  Window.alert("Error getting current user");
    				  }
    			  });
    			
    		}
    
    		@Override
    		public void onFlashCardResponse(AnalyticsEvent event,
    				final FlashCardResponse flashCardResponse) {
    			
    			userService.getCurrentUser(new AsyncCallback<User>() {
  				  public void onSuccess(User user) {
  					  if (user instanceof User) {
  						    flashCardResponse.setGplusId(user.getGplusId());
	  						flashCardResponseService.storeFlashCardResponse(flashCardResponse, new AsyncCallback<FlashCardResponse>() {
	  						     
	  		    				@Override
	  		    				public void onFailure(Throwable caught) {
	  		    					System.out.println("failed to store flash card response");
	  		    				}
	  		    
	  		    				@Override
	  		    				public void onSuccess(FlashCardResponse result) {
	  		    					System.out.println("successfully stored flash response");
	  		    				}
	  		    				
	  		    			});
  					  }
  				  }
  				  public void onFailure(Throwable caught) {
  					  Window.alert("Error getting current user");
  				  }
  			  });
    			
    		}
        });
    

  }

  public static void redirectUser(String pageUrl ){
	  Window.Location.assign(pageUrl);
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
      case "newCourse":
    	  presenter = new NewCoursePresenter(courseService, 
    			  eventBus, new NewCourseView());
    	  break;
      case "session":
    	  presenter = new SessionPresenter(courseService, cardService, 
    			  eventBus, new SessionView());
    	  break;
      case "register":
    	  presenter = new RegistrationPresenter(userService, eventBus, 
    			  new AppRegisterView());
    	  break;
      case "research":
    	  presenter = new ResearchPresenter(analyticsService, eventBus, 
    			  new ResearchView());
    	  break;
      case "import":
    	  presenter = new ImportPresenter(userService, storageService, eventBus, 
    			  new ImportView());
    	  break;
      
      }

      if (presenter != null) {
        presenter.go(container);
      }
    }
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
		  AppController.redirectUser("/login.html");
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
	  
	  userService.isUserLoggedIn(new AsyncCallback<Boolean>(){
		  public void onSuccess( Boolean loggedIn ) {
			  if( loggedIn ){
				  openView(true);
			  }else{
				  getGoogleSession();
			  }
				  
				  
		  }
		      
		  public void onFailure(Throwable caught) {
		        Window.alert("Error fetching card.");
		  }
	  
	  });
	 

  }
  
  /*
   * Retrieve the Google ID information (gmail, and gplusid).
   * If this information is retreived, then the app will attempt
   * to login the user.
   */
  private void getGoogleSession(){
	  userService.getSessionGoogleIds(new AsyncCallback<GoogleIdPackage>(){
		  public void onSuccess(GoogleIdPackage gpack) {
			  if( gpack.isValid() )
				  attemptLogin( gpack );
			  else{
				  openView(false);
			  }
		  }
		      
		  public void onFailure(Throwable caught) {
		        Window.alert("Error fetching card.");
		  }
	  
	  });
  }
  
  /*
   * Attempt to login the session user with the provided
   * Google ID information (gmail, gplus).  On success,
   * the user will be logged in, if the user with the google
   * id info doesn't exist, they will be sent to the
   * registration page.
   * 
   */
  private void attemptLogin( GoogleIdPackage gpack ){
	  userService.loginUser(gpack.getGmail(), new AsyncCallback<Boolean>(){
		  public void onSuccess( Boolean loggedIn ){
			  if( loggedIn ){
				  openView(true);
			  }else{
				  History.newItem("register");
			  }
		  }
		  
		  public void onFailure( Throwable caught ){
			  AppController.redirectUser("/error.html");
		  }
	  });
  }  
  
}
