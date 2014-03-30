package cscie99.team2.lingolearn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

import cscie99.team2.lingolearn.client.AppController;
import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.client.CardServiceAsync;

public class LingoLearn implements EntryPoint {

	@Override
	public void onModuleLoad() {
		//Set up controller / event manager
		UserServiceAsync userService = GWT.create(UserService.class);
		CourseServiceAsync courseService = GWT.create(CourseService.class);
		CardServiceAsync cardService = GWT.create(CardService.class);
		AnalyticsServiceAsync analyticsService = GWT.create(AnalyticsService.class);
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(userService, courseService, 
	    		cardService, analyticsService, eventBus);
	    
	    //Clear loading screen
	    RootPanel.get("content").clear(true);
	    
	    //Go to home page
	    appViewer.go(RootPanel.get("content"));
	    
	}

}
