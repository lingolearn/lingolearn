package cscie99.team2.lingolearn.client.presenter;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.NewCourseView;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.User;

public class NewCoursePresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final NewCourseView display;
  private final CourseServiceAsync courseService;
  private final UserServiceAsync userService;
  
  private User currentUser;
  
  public NewCoursePresenter(CourseServiceAsync courseService, 
  		UserServiceAsync userService, HandlerManager eventBus, 
		  NewCourseView display) {
      this.courseService = courseService;
      this.eventBus = eventBus;
      this.display = display;
      this.userService = userService;
  }
  
  public void bind() {
      display.getCreateCourseButton().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					Course c = getCourseInfoFromUi();
					courseService.createCourse(c, new AsyncCallback<Course>() {
		
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Trouble creating the course");
						}
		
						@Override
						public void onSuccess(Course result) {
							Window.Location.assign("/app.html?courseId=" + result.getCourseId() + "#course");
						}
						
					});
				}
      });
  }
  
  public void go(final HasWidgets container) {
  	userService.getCurrentUser( new AsyncCallback<User>(){
  		public void onSuccess( User user ){
  			currentUser = user;
  	    bind();
  	    container.clear();
  	    container.add(display.asWidget());
  		}
  		
  		public void onFailure( Throwable caught ){
  			
  		}
  	});
  	

  }
  
  private Course getCourseInfoFromUi() {

	  Course c = new Course();
	  c.setCourseName(display.getCourseName());
	  c.setCourseDesc(display.getCourseDescription());
	  c.setInstructor(currentUser);
	  return c;
  }
  

}
