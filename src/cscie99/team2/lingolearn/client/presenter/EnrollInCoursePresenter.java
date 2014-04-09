package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.CurrentUser;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.view.CourseView;
import cscie99.team2.lingolearn.client.view.EnrollInCourseView;
import cscie99.team2.lingolearn.client.view.NewCourseView;
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

public class EnrollInCoursePresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final EnrollInCourseView display;
  private final CourseServiceAsync courseService;
  
  public EnrollInCoursePresenter(CourseServiceAsync courseService, HandlerManager eventBus, 
		  EnrollInCourseView display) {
      this.courseService = courseService;
	  this.eventBus = eventBus;
      this.display = display;
  }
  
  public void bind() {
      display.getEnrollButton().addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			final Course c = display.getSelectedCourse();
			courseService.enrollInCourse(c.getCourseId(),CurrentUser.gplusId, new AsyncCallback<Boolean>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Trouble enrolling in the course");
				}

				@Override
				public void onSuccess(Boolean success) {
					Window.Location.assign("/app.html?courseId=" + c.getCourseId() + "#course");
				}
				
			});
		}
      });
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    addAvailableCoursesToList();
  }
  
  private void addAvailableCoursesToList() {
	  courseService.getAllAvailableCourses(CurrentUser.gplusId, new AsyncCallback<ArrayList<Course>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Trouble enrolling in the course");
			}

			@Override
			public void onSuccess(ArrayList<Course> courses) {
				display.setCourseList(courses);
			}
			
		});
  }
  

}
