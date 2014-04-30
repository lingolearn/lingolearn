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
	  
	  
	  analyticsService.getCourseMetricsDataInstructorView(this.course.getCourseId(), null,  
			  new AsyncCallback<Map<String, Map<String, Float>>>() {
		  public void onSuccess(Map<String, Map<String, Float>> data) {
			  
			  int noStudents = 0;
			  float noClue = 0.0f;
			  float sortaKnewIt = 0.0f;
			  float definitelyKnewIt = 0.0f;

			  for (Entry<String, Map<String, Float>> entry : data.entrySet()) {
				    Map<String, Float> studentData = entry.getValue();
				    
			    	noStudents++;
			    	noClue += studentData.get("noClue");
			    	sortaKnewIt += entry.getValue().get("sortaKnewIt");
			    	definitelyKnewIt += studentData.get("definitelyKnewIt");
					
				    String[] row = new String[5];
					row[0] = entry.getKey();
					row[1] = percentage_format(studentData.get("noClue"));
					row[2] = percentage_format(studentData.get("sortaKnewIt"));
					row[3] = percentage_format(studentData.get("definitelyKnewIt"));
					row[4] = percentage_format(studentData.get("recallRate"));
					
					display.addStatisticsRow(row);
			  }
			  
			  noClue = noClue/(float)noStudents;
			  sortaKnewIt = sortaKnewIt/(float)noStudents;
			  definitelyKnewIt = definitelyKnewIt/(float)noStudents;
			  display.setVisualizations(noClue, sortaKnewIt, definitelyKnewIt);
			  
	      }
		  
		  private String percentage_format(Float value) {
			  return (double)Math.round(value * 1000) / 10 + "%";
		  }
	      
	      public void onFailure(Throwable caught) {
	         //Window.alert("Error fetching course assignments");
	      }
	  });
	  
  }
  

}
