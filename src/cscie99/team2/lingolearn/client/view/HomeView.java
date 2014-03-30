package cscie99.team2.lingolearn.client.view;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.dom.client.Element;

import cscie99.team2.lingolearn.shared.Course;

public class HomeView extends Composite {
  
  interface Binder extends UiBinder<Widget, HomeView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField VerticalPanel coursesAsStudent;
  @UiField VerticalPanel coursesAsInstructor;
  @UiField Button sampleCardButton;
  @UiField Element userName;
  
  public HomeView() {
	  initWidget(binder.createAndBindUi(this));
  }
  
  public HasClickHandlers getSampleCardButton() {
	  return sampleCardButton;
  }

  public HasClickHandlers addCourseUserIsInstructing(Course course) {
	  return this.addCourseLink(this.coursesAsInstructor, course);
  }
  
  public HasClickHandlers addCourseUserIsEnrolledIn(Course course) {
	  return this.addCourseLink(this.coursesAsStudent, course);
  }
  
  private HasClickHandlers addCourseLink(VerticalPanel panel, Course course) {
	  Anchor a = new Anchor();
	  a.setHref("app.html?courseId=" + course.getCourseId() + "#course");
	  a.setText(course.getName());
	  panel.add(a);
	  return a;
  }
  
  public void setUserName(String name) {
	  this.userName.setInnerHTML(name);
  }
  
  public Widget asWidget() {
    return this;
  }
}
