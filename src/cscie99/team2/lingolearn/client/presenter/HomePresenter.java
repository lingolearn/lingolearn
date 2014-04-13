package cscie99.team2.lingolearn.client.presenter;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.view.HomeView;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.User;

public class HomePresenter implements Presenter {  

	
	public static final String UID_QUERY_KEY = "profile";
	
  private final HandlerManager eventBus;
  private final HomeView display;
  private final CourseServiceAsync courseService;
  private final UserServiceAsync userService;
  private User profiledUser;
  
  public HomePresenter(UserServiceAsync userService, 
		  CourseServiceAsync courseService, HandlerManager eventBus, HomeView display) {
	  this.userService = userService;
      this.courseService = courseService;
	  this.eventBus = eventBus;
      this.display = display;
  }
  
  public void bind() {
    
	display.getSampleCardButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        eventBus.fireEvent(new ViewCardEvent());
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    profileUser();
  }
  
  private void profileUser(){
  	
  	Long uid = getUserIdQueryString();
  	if( uid != 0l ){
  		
  		getUserProfileById( uid );
  		
  	}else{
  	
  		getCurrentUserProfile();

  	}
  }
  
  private void getCurrentUserProfile(){
	  userService.getCurrentUser(new AsyncCallback<User>() {
		  public void onSuccess(User user) {
			  if ( user != null && (user instanceof User) ) {
				  profiledUser = user;
				  populateUserProfile();
			  }else{
			  	Window.alert("Error getting current user");
			  }
		  }
		  public void onFailure(Throwable caught) {
			  Window.alert("Error getting current user");
		  }
	  });
  }
  
  private void getUserProfileById( Long uid ){
 		userService.getUserByUid( uid, new AsyncCallback<User>(){
		  public void onSuccess(User user) {
			  if ( user != null && (user instanceof User) ) {
				  profiledUser = user;
				  populateUserProfile();
			  }else{
			  	Window.alert("Error getting current user");
			  }
		  }
		  public void onFailure(Throwable caught) {
			  Window.alert("Error getting current user");
		  }
		});
  }
  
  private void populateUserProfile(){
  	populateUserCourseInfo();
  }
  
  private void populateUserCourseInfo() {
	  
  	display.setUserName(profiledUser.getFirstName());
	  
	  courseService.getCoursesUserIsInstructing(profiledUser.getGplusId(),  new AsyncCallback<ArrayList<Course>>() {
		  public void onSuccess(ArrayList<Course> courses) {
			  for (int i=0;i<courses.size();i++) {
				  Course course = courses.get(i);
				  HasClickHandlers anchor = display.addCourseUserIsInstructing(course);
			  }
	     }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching contact details");
	      }
	  });
	  
	  courseService.getCoursesUserIsEnrolledIn(profiledUser.getGplusId(),  new AsyncCallback<ArrayList<Course>>() {
		  public void onSuccess(ArrayList<Course> courses) {
			  for (int i=0;i<courses.size();i++) {
				  Course course = courses.get(i);
				  HasClickHandlers anchor = display.addCourseUserIsEnrolledIn(course);
			  }
	     }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching contact details");
	      }
	  });
	  
  }
  
  private Long getUserIdQueryString(){
  	try{
	  	Map<String, List<String>> parameterMap = Window.Location.getParameterMap();
	  	List<String> queryValues = parameterMap.get(UID_QUERY_KEY);
	  	if( queryValues == null )
	  		return 0l;
	  	
	  	String gplusId = queryValues.get(0);
	  	Long uid = Long.valueOf(gplusId);
	  	return uid;
  	}catch( NumberFormatException nfe ){
  		return 0l;
  	}
  }


}
