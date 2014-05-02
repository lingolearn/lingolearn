package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.Notice;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CoursePresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final CourseView display;
  private final CourseServiceAsync courseService;
  private Course course;
  private User currentUser;
  private final AnalyticsServiceAsync analyticsService;
  
  public CoursePresenter(CourseServiceAsync courseService, 
		  AnalyticsServiceAsync analyticsService, 
		  User currentUser, HandlerManager eventBus, 
		  CourseView display) {
      this.courseService = courseService;
      this.analyticsService = analyticsService;
	  this.eventBus = eventBus;
      this.display = display;
      this.course = null;
      this.currentUser = currentUser;
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
	  
	  
	  courseService.getCoursesUserIsInstructing(currentUser.getGplusId(), new AsyncCallback<ArrayList<Course>>() {

		@Override
		public void onFailure(Throwable caught) {
			Notice.showNotice("Unable to obtain analytics","warning");
		}

		@Override
		public void onSuccess(ArrayList<Course> results) {
			boolean isInstructor = false;
			for (int i=0;i<results.size();i++) {
				if (results.get(i).getCourseId().equals(course.getCourseId())) {
					isInstructor = true;
				}
			}
			if (isInstructor) {
				populateAnalyticsWithInstructorView();
			} else {
				populateAnalyticsWithStudentView();
			}
		}
		  
	  });
	  
  }
  
  private void populateAnalyticsWithInstructorView() {
	  analyticsService.getCourseMetricsDataInstructorView(this.course.getCourseId(), null,  
			  new AsyncCallback<Map<String, Map<String, Float>>>() {
		  public void onSuccess(Map<String, Map<String, Float>> data) {
			  
			  int noStudents = 0;
			  float noClue = 0.0f;
			  float sortaKnewIt = 0.0f;
			  float definitelyKnewIt = 0.0f;
			  
			  String[] headers = {"User ID","No Clue","Sorta Knew It",
					  "Definitely Knew It","Quiz Recall Rate"};
			  display.setStatisticsHeader(headers);

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
	      
	      public void onFailure(Throwable caught) {
	         //Window.alert("Error fetching course assignments");
	      }
	  });
  }
  
  private void populateAnalyticsWithStudentView() {
	  analyticsService.getCourseMetricsDataStudentView(course.getCourseId(), currentUser.getGplusId(),
			  new AsyncCallback<List<List<Object>>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(List<List<Object>> result) {
					  int noSessions = 0;
					  float noClue = 0.0f;
					  float sortaKnewIt = 0.0f;
					  float definitelyKnewIt = 0.0f;
					  
					  String[] headers = {"Time","Assignment","No Clue","Sorta Knew It",
							  "Definitely Knew It","Quiz Recall Rate"};
					  display.setStatisticsHeader(headers);

					  for (int i=0;i<result.size();i++) {
						    List<Object> studentData = result.get(i);
						    
						    //List was added as
						    //row.add(DateFormat.getInstance().format(us.getSessStart()));
							//row.add(a.getDeck().getDesc());
							//row.add(metricsData.get("noClue"));
							//row.add(metricsData.get("sortaKnewIt"));
							//row.add(metricsData.get("definitelyKnewIt"));
							//row.add(metricsData.get("recallRate"));
							
						    noSessions++;
					    	noClue += (Float) studentData.get(2);
					    	sortaKnewIt += (Float) studentData.get(3);
					    	definitelyKnewIt += (Float) studentData.get(4);
							
						    String[] row = new String[6];
							row[0] = studentData.get(0).toString();
							row[1] = studentData.get(1).toString();
							row[2] = studentData.get(2).toString();
							row[3] = studentData.get(3).toString();
							row[4] = studentData.get(4).toString();
							row[5] = studentData.get(5).toString();
							
							display.addStatisticsRow(row);
					  }
					  
					  noClue = noClue/(float)noSessions;
					  sortaKnewIt = sortaKnewIt/(float)noSessions;
					  definitelyKnewIt = definitelyKnewIt/(float)noSessions;
					  display.setVisualizations(noClue, sortaKnewIt, definitelyKnewIt);
					 
				}
		  
	  });
  }
  

  
  private String percentage_format(Float value) {
	  return (double)Math.round(value * 1000) / 10 + "%";
  }
  

}
