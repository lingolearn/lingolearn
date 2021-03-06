package cscie99.team2.lingolearn.client.presenter;


import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.view.EnrollInCourseView;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.User;

public class EnrollInCoursePresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final EnrollInCourseView display;
  private final CourseServiceAsync courseService;
  
  private User currentUser;
  
  public EnrollInCoursePresenter(CourseServiceAsync courseService, User user,
  		HandlerManager eventBus, 
		  EnrollInCourseView display) {
      this.courseService = courseService;
	  this.eventBus = eventBus;
      this.display = display;
      this.currentUser = user;
  }
  
  public void bind() {
      display.getEnrollButton().addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			final Course c = display.getSelectedCourse();
			courseService.enrollInCourse( c, currentUser, new AsyncCallback<Boolean>() {

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
	  courseService.getAllAvailableCourses(currentUser.getGplusId(), new AsyncCallback<ArrayList<Course>>() {

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
