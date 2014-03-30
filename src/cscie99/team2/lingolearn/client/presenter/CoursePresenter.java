package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
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

public class CoursePresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final CourseView display;
  private final CourseServiceAsync courseService;
  private Course course;
  private final AnalyticsServiceAsync analyticsService;
  
  public CoursePresenter(CourseServiceAsync courseService, 
		  AnalyticsServiceAsync analyticsService, HandlerManager eventBus, 
		  CourseView display) {
      this.courseService = courseService;
      this.analyticsService = analyticsService;
	  this.eventBus = eventBus;
      this.display = display;
      this.course = null;
  }
  
  public void bind() {
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    
    //Set course based on query parameter in URL
    String courseId = "course1";
    courseId = Window.Location.getParameter("courseId");
    this.getCourseInfo(courseId);
  }
  
  private void getCourseInfo(String courseId) {
	  courseService.getCourseById(courseId, new AsyncCallback<Course>() {
		  public void onSuccess(Course returnedCourse) {
			  course = returnedCourse;
			  populateUserCourseInfo();
		  }
		  public void onFailure(Throwable caught) {
			  Window.alert("Error fetching course from id");
		  }
	  });
  }
  
  private void populateUserCourseInfo() {
	  //TODO: use UserService to get user info
	  
	  display.setCourseData(this.course);
	  
	  
	  courseService.getSessionsForCourse(this.course.getCourseId(), 
			  new AsyncCallback<ArrayList<Session>>() {
		  public void onSuccess(ArrayList<Session> sessions) {
	          display.setAssignmentList(sessions);
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching course assignments");
	      }
	  });
	  
	  analyticsService.getCourseMetricsData(this.course.getCourseId(), 
			  new AsyncCallback<Map<String, Map<String, Float>>>() {
		  public void onSuccess(Map<String, Map<String, Float>> data) {
			  for (Entry<String, Map<String, Float>> entry : data.entrySet()) {
				    String studentName = entry.getKey();
				    Map<String, Float> studentData = entry.getValue();
				    Map<String, Float> conglomerateData = new HashMap<String, Float>();
				    for (Entry<String, Float> statEntry : studentData.entrySet()) {
				    	String statName = statEntry.getKey();
				    	Float statValue = statEntry.getValue();
				    	display.addStatisticToDisplay(statName, statValue.toString());
				    }
			  }
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching course assignments");
	      }
	  });
	  
  }
  

}
