package cscie99.team2.lingolearn.client;

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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private final UserServiceAsync userService;
  private final CardServiceAsync cardService; 
  private final CourseServiceAsync courseService;
  private final AnalyticsServiceAsync analyticsService;
  private HasWidgets container;
  
  public AppController(UserServiceAsync userService, CourseServiceAsync courseService,
		  CardServiceAsync cardService, AnalyticsServiceAsync analyticsService, 
		  HandlerManager eventBus) {
    this.eventBus = eventBus;
    this.userService = userService;
    this.cardService = cardService;
    this.courseService = courseService;
    this.analyticsService = analyticsService;
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
  
  public void go(final HasWidgets container) {
    this.container = container;
    
    if ("".equals(History.getToken())) {
      History.newItem("home");
    }
    else {
      History.fireCurrentHistoryState();
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
}
