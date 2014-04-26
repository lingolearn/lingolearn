package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Session;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
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
    Long courseId;
    courseId = Long.valueOf(Window.Location.getParameter("courseId"));
    this.getCourseInfo(courseId);
  }
  
  private void getCourseInfo(Long courseId) {
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

			  // TODO: lots of tricky nested data structures here, can probably be simpfilied
			  for (Entry<String, Map<String, Float>> entry : data.entrySet()) {
				    String studentName = entry.getKey();
				    Map<String, Float> studentData = entry.getValue();
			    	
			    	ArrayList<String> row = new ArrayList<String>();
			    	row.add(studentName);
				    for (Entry<String, Float> statEntry : studentData.entrySet()) {
				    	String statName = statEntry.getKey();
				    	Double statValue = (double)Math.round(statEntry.getValue() * 1000) / 10;
				    	
				    	if (statName.equals("noClue"))
				    		row.add(statValue.toString() + "%");
				    	if (statName.equals("sortaKnewIt"))
				    		row.add(statValue.toString() + "%");
				    	if (statName.equals("definitelyKnewIt"))
				    		row.add(statValue.toString() + "%");
				    	if (statName.equals("recallRate"))
				    		row.add(statValue.toString() + "%");
				    }
				    display.addStatisticsRow(row);
			  }
	      }
	      
	      public void onFailure(Throwable caught) {
	         //Window.alert("Error fetching course assignments");
	      }
	  });
	  
  }
  

}
