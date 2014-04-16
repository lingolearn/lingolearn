package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.view.AddAssignmentView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class AddAssignmentPresenter implements Presenter {  

  private final HandlerManager eventBus;
  private final AddAssignmentView display;
  private final CourseServiceAsync courseService;
  
  public AddAssignmentPresenter(CourseServiceAsync courseService, HandlerManager eventBus, 
		  AddAssignmentView display) {
      this.courseService = courseService;
	  this.eventBus = eventBus;
      this.display = display;
  }
  
  public void bind() {
      
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
  }
  

}
